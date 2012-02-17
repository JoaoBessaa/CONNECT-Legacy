/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.hhs.fha.nhinc.patientdiscovery.response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author dunnek
 */
public class ResponseFactory {
    public static final String RESPONSE_MODE_VERIFY = "verify";
    public static final String RESPONSE_MODE_TRUST = "trust";
    public static final String RESPONSE_MODE_PASSTHRU = "passthrough";
    private static final int VERIFY_MODE = 0;
    private static final int TRUST_MODE = 1;
    private static final int PASSTHRU_MODE = 2;
    private Log log = null;

    public ResponseFactory() {
        log = createLogger();
    }
    public ResponseMode getResponseMode()
    {
        ResponseMode result = null;

        int mode = getResponseModeType();

        switch (mode)
        {
            case (VERIFY_MODE):
            {
                result = new VerifyMode();
                break;
            }
            case TRUST_MODE:
            {
                result = new TrustMode();
                break;
            }
            case PASSTHRU_MODE:
            {
                result = new PassThruMode();
                break;
            }
            default:
            {
                result = new VerifyMode();
            }
        }

        return result;
    }
    protected Log createLogger()
    {
        return ((log != null) ? log : LogFactory.getLog(getClass()));
    }

    protected String getModeProperty()
    {
        String result = "";

        try
        {
            result = gov.hhs.fha.nhinc.properties.PropertyAccessor.getProperty("gateway", "patientDiscoveryResponseMode");
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage(), ex);
        }


        return result;
    }
    private int getResponseModeType()
    {
        int result = 0;

        try
        {
            String property = getModeProperty();
            if (property == null)
            {
                result = VERIFY_MODE;
            }
            else if(property.equalsIgnoreCase(RESPONSE_MODE_VERIFY))
            {
                result = VERIFY_MODE;
            }
            else if(property.equalsIgnoreCase(RESPONSE_MODE_TRUST))
            {
                result = TRUST_MODE;
            }
            else if(property.equalsIgnoreCase(RESPONSE_MODE_PASSTHRU))
            {
                result = PASSTHRU_MODE;
            }
            else
            {
                //unknown mode, use default response mode
                result = VERIFY_MODE;
            }


        }
        catch (Exception ex)
        {
            log.error(ex.getMessage(), ex);
            result = VERIFY_MODE;
        }

        return result;

    }

}
