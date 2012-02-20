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

package gov.hhs.fha.nhinc.patientdiscovery.passthru.proxy;
import org.hl7.v3.PRPAIN201305UV02;
import org.hl7.v3.PRPAIN201306UV02;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetSystemType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author mflynn02
 */
public class PassthruPatientDiscoveryProxyNoOpImpl implements PassthruPatientDiscoveryProxy {
   private Log log = null;

    public PassthruPatientDiscoveryProxyNoOpImpl()
    {
        log = createLogger();
    }
    protected Log createLogger()
    {
        return LogFactory.getLog(getClass());
    }
    public PRPAIN201306UV02 PRPAIN201305UV(PRPAIN201305UV02 body, AssertionType assertion, NhinTargetSystemType target) {
        log.error("NhincPatientDiscoveryNoOpImpl - Not implemented yet.");
        return new PRPAIN201306UV02();
    }

}
