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
package gov.hhs.fha.nhinc.patientdiscovery.adapter.deferred.request.queue.proxy;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import org.hl7.v3.MCCIIN000002UV01;
import org.hl7.v3.PRPAIN201305UV02;

/**
 * The No Op implementation of the Adapter Patient Discovery Async Req Queue Proxy.
 * 
 * @author JHOPPESC
 */
public class AdapterPatientDiscoveryAsyncReqQueueProxyNoOpImpl implements AdapterPatientDiscoveryAsyncReqQueueProxy
{

    public MCCIIN000002UV01 addPatientDiscoveryAsyncReq(PRPAIN201305UV02 request, AssertionType assertion)
    {
        return new MCCIIN000002UV01();
    }
}
