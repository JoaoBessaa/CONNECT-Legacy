package gov.hhs.fha.nhinc.policyengine.adapterpep.proxy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import gov.hhs.fha.nhinc.properties.PropertyAccessor;

/**
 * An object factory that uses the Spring Framework to create service
 * implementation objects. The configuration file is referenced in the
 * common properties file location to assist in installation and configuration
 * simplicity.
 *
 * The Spring configuration file is defined by the constant "SPRING_CONFIG_FILE".
 * This file is loaded into an application context in the static initializer
 * and then the objects defined in the config file are available to the framework
 * for creation. This configuration file can be located anywhere in the
 * classpath.
 *
 * To retrieve an object that is created by the framework, the
 * "getBean(String beanId)" method is called on the application context passing
 * in the beanId that is specified in the config file. Considering the default
 * correlation definition in the config file for this component:
 * <bean id="adapterpep" class="gov.hhs.fha.nhinc.policyengine.adapterpep.proxy.AdapterPEPProxyNoOpImpl"/>
 * the bean id is "patientcorrelation" and an object of this type can be retrieved from
 * the application context by calling the getBean method like:
 * context.getBean("patientcorrelation");. This returns an object that can be casted to
 * the appropriate interface and then used in the application code. See the
 * getPatientCorrelation() method in this class.
 *
 */
public class AdapterPEPProxyObjectFactory
{
    private static final String SPRING_CONFIG_FILE = "AdapterPEPConfig.xml";
    private static final String BEAN_NAME_ADAPTER_PEP = "adapterpep";

    private static ApplicationContext context = null;

    static
    {
        String configFile = PropertyAccessor.getPropertyFileLocation();

        context = new FileSystemXmlApplicationContext(configFile + SPRING_CONFIG_FILE);
    }

    /**
     * Retrieve an adapter PEP implementation using the IOC framework.
     * This method retrieves the object from the framework that has an
     * identifier of "adapterpep."
     *
     * @return AdapterPEPProxy instance
     */
    public AdapterPEPProxy getAdapterPEPProxy()
    {
        AdapterPEPProxy adapterPEPProxy = null;
        if(context != null)
        {
            adapterPEPProxy = (AdapterPEPProxy)context.getBean(BEAN_NAME_ADAPTER_PEP);
        }
        return adapterPEPProxy;
    }
}
