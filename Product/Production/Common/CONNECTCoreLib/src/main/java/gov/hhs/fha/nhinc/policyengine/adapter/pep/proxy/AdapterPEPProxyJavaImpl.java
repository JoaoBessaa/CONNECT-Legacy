/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 * Copyright 2010(Year date of delivery) United States Government, as represented by the Secretary of Health and Human Services.  All rights reserved.
 *  
 */
package gov.hhs.fha.nhinc.policyengine.adapter.pep.proxy;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommonadapter.CheckPolicyRequestType;
import gov.hhs.fha.nhinc.common.nhinccommonadapter.CheckPolicyResponseType;
import gov.hhs.fha.nhinc.policyengine.adapter.pep.AdapterPEPImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is the concrete implementation for the Java based call to the
 * AdapterPEP.
 */
public class AdapterPEPProxyJavaImpl implements AdapterPEPProxy
{
    private Log log = null;

    public AdapterPEPProxyJavaImpl()
    {
        log = createLogger();
    }

    protected Log createLogger()
    {
        return LogFactory.getLog(getClass());
    }

    /**
     * Given a request to check the access policy, this service will interface
     * with the PDP to determine if access is to be granted or denied.
     * @param checkPolicyRequest The xacml request to check defined policy
     * @return The xacml response which contains the access decision
     */
    public CheckPolicyResponseType checkPolicy(CheckPolicyRequestType checkPolicyRequest, AssertionType assertion)
    {
        log.debug("Begin AdapterPEPProxyJavaImpl.checkPolicy");
        CheckPolicyResponseType checkPolicyResponse = new CheckPolicyResponseType();

        AdapterPEPImpl pepImpl = new AdapterPEPImpl();

        try
        {
            checkPolicyResponse = pepImpl.checkPolicy(checkPolicyRequest, assertion);
        }
        catch (Exception ex)
        {
            String message = "Error occurred calling AdapterPEPProxyJavaImpl.checkPolicy.  Error: " +
                                   ex.getMessage();
            log.error(message, ex);
            throw new RuntimeException(message, ex);
        }

        log.debug("End AdapterPEPProxyJavaImpl.checkPolicy");
        return checkPolicyResponse;
    }
}
