package gov.hhs.fha.nhinc.docquery.adapter.proxy;

import gov.hhs.fha.nhinc.adapterdocquerysecured.AdapterDocQuerySecured;
import gov.hhs.fha.nhinc.adapterdocquerysecured.AdapterDocQuerySecuredPortType;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommonadapter.RespondingGatewayCrossGatewayQueryRequestType;
import gov.hhs.fha.nhinc.connectmgr.ConnectionManagerCache;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import gov.hhs.fha.nhinc.saml.extraction.SamlTokenCreator;
import java.util.Map;
import java.util.StringTokenizer;
import javax.xml.ws.BindingProvider;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryError;

/**
 *
 *
 * @author Neil Webb
 */
public class AdapterDocQueryWebServiceProxy implements AdapterDocQueryProxy
{
    private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(AdapterDocQueryWebServiceProxy.class);
    private static AdapterDocQuerySecured service = new AdapterDocQuerySecured();

    public AdhocQueryResponse respondingGatewayCrossGatewayQuery(RespondingGatewayCrossGatewayQueryRequestType respondingGatewayCrossGatewayQueryRequest)
    {
        AdhocQueryResponse response = null;
        try
        {
            String url = ConnectionManagerCache.getLocalEndpointURLByServiceName(NhincConstants.ADAPTER_DOC_QUERY_SECURED_SERVICE_NAME);

            AdapterDocQuerySecuredPortType port = getPort(url);

            log.debug("Setting assertion");
            AssertionType assertIn = respondingGatewayCrossGatewayQueryRequest.getAssertion();
            SamlTokenCreator tokenCreator = new SamlTokenCreator();
            Map requestContext = tokenCreator.CreateRequestContext(assertIn, url, NhincConstants.DOC_RETRIEVE_ACTION);
            ((BindingProvider) port).getRequestContext().putAll(requestContext);
                        
			int retryCount = gov.hhs.fha.nhinc.webserviceproxy.WebServiceProxyHelper.getInstance().getRetryAttempts();
			int retryDelay = gov.hhs.fha.nhinc.webserviceproxy.WebServiceProxyHelper.getInstance().getRetryDelay();
        String exceptionText = gov.hhs.fha.nhinc.webserviceproxy.WebServiceProxyHelper.getInstance().getExceptionText();
        javax.xml.ws.WebServiceException catchExp = null;
        if (retryCount > 0 && retryDelay > 0 && exceptionText != null && !exceptionText.equalsIgnoreCase("")) {
            int i = 1;
            while (i <= retryCount) {
                try {
                    response = port.respondingGatewayCrossGatewayQuery(respondingGatewayCrossGatewayQueryRequest.getAdhocQueryRequest());
                    break;
                } catch (javax.xml.ws.WebServiceException e) {
                    catchExp = e;
                    int flag = 0;
                    StringTokenizer st = new StringTokenizer(exceptionText, ",");
                    while (st.hasMoreTokens()) {
                        if (e.getMessage().contains(st.nextToken())) {
                            flag = 1;
                        }
                    }
                    if (flag == 1) {
                        log.warn("Exception calling ... web service: " + e.getMessage());
                        System.out.println("retrying the connection for attempt [ " + i + " ] after [ " + retryDelay + " ] seconds");
                        log.info("retrying attempt [ " + i + " ] the connection after [ " + retryDelay + " ] seconds");
                        i++;
                        try {
                            Thread.sleep(retryDelay);
                        } catch (InterruptedException iEx) {
                            log.error("Thread Got Interrupted while waiting on AdapterDocQuerySecured call :" + iEx);
                        } catch (IllegalArgumentException iaEx) {
                            log.error("Thread Got Interrupted while waiting on AdapterDocQuerySecured call :" + iaEx);
                        }
                        retryDelay = retryDelay + retryDelay; //This is a requirement from Customer
                    } else {
                        log.error("Unable to call AdapterDocQuerySecured Webservice due to  : " + e);
                        throw e;
                    }
                }
            }

            if (i > retryCount) {
                log.error("Unable to call AdapterDocQuerySecured Webservice due to  : " + catchExp);
                throw catchExp;
            }

        } else {
            response = port.respondingGatewayCrossGatewayQuery(respondingGatewayCrossGatewayQueryRequest.getAdhocQueryRequest());
        }
        }
        catch (Exception ex)
        {
            log.error("Error sending NHIN Proxy message: " + ex.getMessage(), ex);
            response = new AdhocQueryResponse();
            response.setStatus("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure");

            RegistryError registryError = new RegistryError();
            registryError.setCodeContext("Processing NHIN Proxy document retrieve");
            registryError.setErrorCode("XDSRepositoryError");
            registryError.setSeverity("Error");
            response.getRegistryErrorList().getRegistryError().add(registryError);
        }

        return response;
    }
    
    private AdapterDocQuerySecuredPortType getPort(String url)
    {
        AdapterDocQuerySecuredPortType port = service.getAdapterDocQuerySecuredPortSoap11();
        gov.hhs.fha.nhinc.webserviceproxy.WebServiceProxyHelper.getInstance().initializePort((javax.xml.ws.BindingProvider) port, url);
        return port;
    }
}
