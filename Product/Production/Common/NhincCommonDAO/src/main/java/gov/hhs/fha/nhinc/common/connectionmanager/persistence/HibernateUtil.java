/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.hhs.fha.nhinc.common.connectionmanager.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.File;

/**
 * This class will be used as a Utility Class to access the Data Object using Hibernate SessionFactory
 * @author svalluripalli
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    private static Log log = LogFactory.getLog(HibernateUtil.class);
    
    private static final String HIBERNATE_ASSIGNING_AUTHORITY = "assignauthority.hibernate.cfg.xml";
    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            sessionFactory = new Configuration().configure(getConfigFile()).buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    /**
     * Method returns an instance of Hibernate SessionFactory
     * @return SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
     private static File getConfigFile(){
        File result = null;

        try
        {
            result = HibernateAccessor.getHibernateFile(HIBERNATE_ASSIGNING_AUTHORITY);
        }
        catch (Exception ex)
        {
            log.error("Unable to load " + HIBERNATE_ASSIGNING_AUTHORITY + " " + ex.getMessage(), ex );
        }


        return result;


    }
}
