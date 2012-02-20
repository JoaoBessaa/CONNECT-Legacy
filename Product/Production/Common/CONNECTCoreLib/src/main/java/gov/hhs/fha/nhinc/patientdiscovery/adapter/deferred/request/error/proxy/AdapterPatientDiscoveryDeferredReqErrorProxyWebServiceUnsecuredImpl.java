/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 * Copyright 2010(Year date of delivery) United States Government, as represented by the Secretary of Health and Human Services.  All rights reserved.
 *  
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.hhs.fha.nhinc.patientdiscovery.adapter.deferred.request.error.proxy;

import gov.hhs.fha.nhinc.adapterpatientdiscoveryasyncreqerror.AdapterPatientDiscoveryAsyncReqErrorPortType;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import gov.hhs.fha.nhinc.nhinclib.NullChecker;
import gov.hhs.fha.nhinc.transform.subdisc.HL7AckTransforms;
import gov.hhs.fha.nhinc.webserviceproxy.WebServiceProxyHelper;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hl7.v3.AsyncAdapterPatientDiscoveryErrorRequestType;
import org.hl7.v3.MCCIIN000002UV01;
import org.hl7.v3.PRPAIN201305UV02;
import org.hl7.v3.PRPAIN201306UV02;

/**
 *
 * @author jhoppesc
 */
public class AdapterPatientDiscoveryDeferredReqErrorProxyWebServiceUnsecuredImpl implements AdapterPatientDiscoveryDeferredReqErrorProxy {
    private Log log = null;
    private static Service cachedService = null;
    private static final String NAMESPACE_URI = "urn:gov:hhs:fha:nhinc:adapterpatientdiscoveryasyncreqerror";
    private static final String SERVICE_LOCAL_PART = "AdapterPatientDiscoveryAsyncReqError";
    private static final String PORT_LOCAL_PART = "AdapterPatientDiscoveryAsyncReqErrorPortSoap";
    private static final String WSDL_FILE = "AdapterPatientDiscoveryAsyncReqError.wsdl";
    private static final String WS_ADDRESSING_ACTION = "urn:gov:hhs:fha:nhinc:adapterpatientdiscoveryasyncreqerror:ProcessPatientDiscoveryAsyncReqErrorRequest";
    private WebServiceProxyHelper oProxyHelper = null;

    public AdapterPatientDiscoveryDeferredReqErrorProxyWebServiceUnsecuredImpl() {
        log = createLogger();
        oProxyHelper = createWebServiceProxyHelper();
    }

    protected Log createLogger() {
        return LogFactory.getLog(getClass());
    }

    protected WebServiceProxyHelper createWebServiceProxyHelper() {
        return new WebServiceProxyHelper();
    }

    /**
     * This method retrieves and initializes the port.
     *
     * @param url The URL for the web service.
     * @return The port object for the web service.
     */
    protected AdapterPatientDiscoveryAsyncReqErrorPortType getPort(String url, String serviceAction, String wsAddressingAction, AssertionType assertion) {
        AdapterPatientDiscoveryAsyncReqErrorPortType port = null;
        Service service = getService();
        if (service != null) {
            log.debug("Obtained service - creating port.");

            port = service.getPort(new QName(NAMESPACE_URI, PORT_LOCAL_PART), AdapterPatientDiscoveryAsyncReqErrorPortType.class);
            oProxyHelper.initializeUnsecurePort((javax.xml.ws.BindingProvider) port, url, wsAddressingAction, assertion);
        } else {
            log.error("Unable to obtain serivce - no port created.");
        }
        return port;
    }

    /**
     * Retrieve the service class for this web service.
     *
     * @return The service class for this web service.
     */
    protected Service getService() {
        if (cachedService == null) {
            try {
                cachedService = oProxyHelper.createService(WSDL_FILE, NAMESPACE_URI, SERVICE_LOCAL_PART);
            } catch (Throwable t) {
                log.error("Error creating service: " + t.getMessage(), t);
            }
        }
        return cachedService;
    }

    public MCCIIN000002UV01 processPatientDiscoveryAsyncReqError(PRPAIN201305UV02 request, PRPAIN201306UV02 response, AssertionType assertion, String errMsg) {
        log.debug("Begin processPatientDiscoveryAsyncReqError");
        MCCIIN000002UV01 ack = null;

        try
        {
            String url = oProxyHelper.getUrlLocalHomeCommunity(NhincConstants.PATIENT_DISCOVERY_ADAPTER_ASYNC_REQ_ERROR_SERVICE_NAME);
            AdapterPatientDiscoveryAsyncReqErrorPortType port = getPort(url, NhincConstants.PATIENT_DISCOVERY_ACTION, WS_ADDRESSING_ACTION, assertion);

            if(request == null)
            {
                log.error("Request was null");
            }
            else if (response == null) {
                log.error("Response was null");
            }
            else if (assertion == null)
            {
                log.error("assertion was null");
            }
            else if (NullChecker.isNullish(errMsg))
            {
                log.error("targets was null");
            }
            else if(port == null)
            {
                log.error("port was null");
            }
            else
            {
                AsyncAdapterPatientDiscoveryErrorRequestType msg = new AsyncAdapterPatientDiscoveryErrorRequestType();
                msg.setAssertion(assertion);
                msg.setPRPAIN201305UV02(request);
                msg.setPRPAIN201306UV02(response);
                msg.setErrorMsg(errMsg);

                ack = (MCCIIN000002UV01)oProxyHelper.invokePort(port, AdapterPatientDiscoveryAsyncReqErrorPortType.class, "processPatientDiscoveryAsyncReqError", msg);
            }
        }
        catch (Exception ex)
        {
            log.error("Error calling processPatientDiscoveryAsyncReqError: " + ex.getMessage(), ex);
            ack = HL7AckTransforms.createAckFrom201305(request, errMsg);
        }

        log.debug("End processPatientDiscoveryAsyncReqError");
        return ack;
    }

}
