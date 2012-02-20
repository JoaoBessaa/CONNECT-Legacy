/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 * Copyright 2010(Year date of delivery) United States Government, as represented by the Secretary of Health and Human Services.  All rights reserved.
 *  
 */
package gov.hhs.fha.nhinc.policyengine.adapter.orchestrator;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommonadapter.CheckPolicyResponseType;
import gov.hhs.fha.nhinc.common.nhinccommonadapter.CheckPolicyRequestType;
import gov.hhs.fha.nhinc.policyengine.adapter.pep.proxy.AdapterPEPProxy;
import gov.hhs.fha.nhinc.policyengine.adapter.pep.proxy.AdapterPEPProxyObjectFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class is the Java implementation of the AdapterPolicyEngineOrchestrator.   It
 * is orchestrates a call to the policy engine by passing the information to the PEP.
 *
 * @author Les Westberg
 */
public class AdapterPolicyEngineOrchestratorImpl
{

    private static Log log = LogFactory.getLog(AdapterPolicyEngineOrchestratorImpl.class);

    /**
     * Given a request to check the access policy, this service will interface
     * with the Adapter PEP to determine if access is to be granted or denied.
     * @param checkPolicyRequest The request to check defined policy
     * @return The response which contains the access decision
     */
    public CheckPolicyResponseType checkPolicy(CheckPolicyRequestType checkPolicyRequest, AssertionType assertion)
    {
        CheckPolicyResponseType checkPolicyResp = new CheckPolicyResponseType();

        AdapterPEPProxyObjectFactory factory = new AdapterPEPProxyObjectFactory();
        AdapterPEPProxy adapterPEPProxy = factory.getAdapterPEPProxy();
        log.debug("AdapterPEP Proxy selected: " + adapterPEPProxy.getClass());

        try
        {
            checkPolicyResp = adapterPEPProxy.checkPolicy(checkPolicyRequest, assertion);
        }
        catch (Exception ex)
        {
            String message = "Error occurred calling AdapterPEPImpl.checkPolicy.  Error: " +
                ex.getMessage();
            log.error(message, ex);
            throw new RuntimeException(message, ex);
        }
        return checkPolicyResp;
    }
}
