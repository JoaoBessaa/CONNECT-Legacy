/*
 * Helper class that calls Secured PatientCorrelation endpoints. Will replace
 * PatientCorrelationHelper once the BPEL to EJB conversion is complete. 
 */
package gov.hhs.fha.nhinc.patientcorrelationfacade.helper;

import gov.hhs.fha.nhinc.connectmgr.ConnectionManagerException;

import gov.hhs.fha.nhinc.properties.PropertyAccessException;
import gov.hhs.fha.nhinc.properties.PropertyAccessor;
import gov.hhs.fha.nhinc.connectmgr.ConnectionManagerCache;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.nhinccomponentpatientcorrelation.*;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import gov.hhs.fha.nhinc.saml.extraction.SamlTokenCreator;
import java.util.Map;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author dunnek
 */
public class PatientCorrelationHelperSecured {

    private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(PatientCorrelationHelperSecured.class);
    private static PatientCorrelationServiceSecured service = new gov.hhs.fha.nhinc.nhinccomponentpatientcorrelation.PatientCorrelationServiceSecured();
    private static final String PROPERTY_FILE_NAME = "gateway";
    private static final String PROPERTY_LOCAL_HOME_COMMUNITY = "localHomeCommunityId";

    private PatientCorrelationSecuredPortType getPort(String url) {
        if (service == null) {
            service = new gov.hhs.fha.nhinc.nhinccomponentpatientcorrelation.PatientCorrelationServiceSecured();
        }
        PatientCorrelationSecuredPortType port = service.getPatientCorrelationSecuredPort();

        gov.hhs.fha.nhinc.webserviceproxy.WebServiceProxyHelper.getInstance().initializePort((javax.xml.ws.BindingProvider) port, url);
        return port;
    }

    public org.hl7.v3.PRPAIN201310UV02 retrievePatientCorrelations(org.hl7.v3.PRPAIN201309UV02 correlationInput, AssertionType assertion) {
        org.hl7.v3.PRPAIN201310UV02 response = null;
        log.debug("in PatientCorrelationHelperSecured.retrievePatientCorrelations");
        try { // Call Web Service Operation
            String url = getPatientCorrelationEndpointAddress();
            PatientCorrelationSecuredPortType port = getPort(url);


            org.hl7.v3.RetrievePatientCorrelationsSecuredRequestType retrievePatientCorrelationsRequest = new org.hl7.v3.RetrievePatientCorrelationsSecuredRequestType();
            retrievePatientCorrelationsRequest.setPRPAIN201309UV02(correlationInput);

            SamlTokenCreator tokenCreator = new SamlTokenCreator();
            Map requestContext = tokenCreator.CreateRequestContext(assertion, url, NhincConstants.PAT_CORR_ACTION);


            ((BindingProvider) port).getRequestContext().putAll(requestContext);
            org.hl7.v3.RetrievePatientCorrelationsSecuredResponseType result = port.retrievePatientCorrelations(retrievePatientCorrelationsRequest);


            if (result != null) {
                response = result.getPRPAIN201310UV02();
            }
        } catch (Exception ex) {
            log.error("Exception encountered retrieving patient correlations: " + ex.getMessage(), ex);
        }
        return response;
    }

    public org.hl7.v3.MCCIIN000002UV01 addPatientCorrelation(org.hl7.v3.PRPAIN201301UV02 request, AssertionType assertion) {

        org.hl7.v3.MCCIIN000002UV01 response = null;

        log.debug("in PatientCorrelationHelperSecured.addPatientCorrelation");

        try { // Call Web Service Operation
            String url = getPatientCorrelationEndpointAddress();
            PatientCorrelationSecuredPortType port = getPort(url);

            org.hl7.v3.AddPatientCorrelationSecuredRequestType addPatientCorrelationRequest = new org.hl7.v3.AddPatientCorrelationSecuredRequestType();
            addPatientCorrelationRequest.setPRPAIN201301UV02(request);

            SamlTokenCreator tokenCreator = new SamlTokenCreator();
            Map requestContext = tokenCreator.CreateRequestContext(assertion, url, NhincConstants.PAT_CORR_ACTION);
            ((BindingProvider) port).getRequestContext().putAll(requestContext);
            // TODO process result here
            org.hl7.v3.AddPatientCorrelationSecuredResponseType result = port.addPatientCorrelation(addPatientCorrelationRequest);
            if (result != null) {
                response = result.getMCCIIN000002UV01();
            }
        } catch (Exception ex) {
            log.error("Exception encountered adding a patient correlation: " + ex.getMessage(), ex);
        }
        return response;
    }

    /**
     * Retrieve the endpoint address for the patient correlation service.
     *
     * @return Patient correlation service endpoint address.
     */
    private String getPatientCorrelationEndpointAddress() {
        String endpointAddress = null;
        try {
            // Lookup home community id
            String homeCommunity = getHomeCommunityId();
            // Get endpoint url
            endpointAddress = ConnectionManagerCache.getEndpointURLByServiceName(homeCommunity, NhincConstants.PATIENT_CORRELATION_SECURED_SERVICE_NAME);
            log.debug("Patient correlation endpoint address: " + endpointAddress);
        } catch (PropertyAccessException pae) {
            log.error("Exception encountered retrieving the local home community: " + pae.getMessage(), pae);
        } catch (ConnectionManagerException cme) {
            log.error("Exception encountered retrieving the patient correlation connection endpoint address: " + cme.getMessage(), cme);
        }
        return endpointAddress;
    }

    /**
     * Retrieve the local home community id
     *
     * @return Local home community id
     * @throws gov.hhs.fha.nhinc.properties.PropertyAccessException
     */
    private String getHomeCommunityId() throws PropertyAccessException {
        return PropertyAccessor.getProperty(PROPERTY_FILE_NAME, PROPERTY_LOCAL_HOME_COMMUNITY);
    }
}
