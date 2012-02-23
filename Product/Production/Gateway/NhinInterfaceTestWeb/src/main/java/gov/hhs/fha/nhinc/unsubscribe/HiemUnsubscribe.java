package gov.hhs.fha.nhinc.unsubscribe;

import javax.jws.WebService;
import javax.xml.ws.BindingType;
import org.oasis_open.docs.wsn.bw_2.ResourceUnknownFault;
import org.oasis_open.docs.wsn.bw_2.UnableToDestroySubscriptionFault;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.jws.HandlerChain;

/**
 *
 * @author Neil Webb
 */
@WebService(serviceName = "SubscriptionManagerService", portName = "SubscriptionManagerPort", endpointInterface = "org.oasis_open.docs.wsn.bw_2.SubscriptionManager", targetNamespace = "http://docs.oasis-open.org/wsn/bw-2", wsdlLocation = "WEB-INF/wsdl/HiemNotify/NhinSubscription.wsdl")
@BindingType(value = "http://www.w3.org/2003/05/soap/bindings/HTTP/")
@HandlerChain(file = "HiemUnsubscribeSoapHeaderHandler.xml")
public class HiemUnsubscribe
{
    @Resource
    private WebServiceContext context;

    public org.oasis_open.docs.wsn.b_2.UnsubscribeResponse unsubscribe(org.oasis_open.docs.wsn.b_2.Unsubscribe unsubscribeRequest) throws ResourceUnknownFault, UnableToDestroySubscriptionFault
    {
        return new HiemUnsubscribeImpl().unsubscribe(unsubscribeRequest, context);
    }

}
