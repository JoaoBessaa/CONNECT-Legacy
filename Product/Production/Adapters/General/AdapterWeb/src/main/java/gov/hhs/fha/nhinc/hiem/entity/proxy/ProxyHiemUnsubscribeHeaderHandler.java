package gov.hhs.fha.nhinc.hiem.entity.proxy;

import java.util.Collections;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import gov.hhs.fha.nhinc.hiem.dte.SoapUtil;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;

/**
 *
 * @author rayj
 */
public class ProxyHiemUnsubscribeHeaderHandler implements SOAPHandler<SOAPMessageContext>
{

    private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(ProxyHiemUnsubscribeHeaderHandler.class);

    @SuppressWarnings("unchecked")
    public Set<QName> getHeaders()
    {
        return Collections.EMPTY_SET;
    }

    public boolean handleMessage(SOAPMessageContext context)
    {
        new SoapUtil().extractReferenceParameters(context, NhincConstants.HTTP_REQUEST_ATTRIBUTE_SOAPMESSAGE);
        return true;
    }

    public boolean handleFault(SOAPMessageContext context)
    {
        return true;
    }

    public void close(MessageContext context)
    {
    }
}
