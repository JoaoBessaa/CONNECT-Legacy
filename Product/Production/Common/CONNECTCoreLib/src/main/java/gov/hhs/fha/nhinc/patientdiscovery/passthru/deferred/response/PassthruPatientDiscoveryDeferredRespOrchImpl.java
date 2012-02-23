/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 * Copyright 2010(Year date of delivery) United States Government, as represented by the Secretary of Health and Human Services.  All rights reserved.
 *  
 */
package gov.hhs.fha.nhinc.patientdiscovery.passthru.deferred.response;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetCommunitiesType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetCommunityType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetSystemType;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import gov.hhs.fha.nhinc.patientdiscovery.PatientDiscoveryAuditLogger;
import gov.hhs.fha.nhinc.patientdiscovery.nhin.deferred.response.proxy.NhinPatientDiscoveryDeferredRespProxy;
import gov.hhs.fha.nhinc.patientdiscovery.nhin.deferred.response.proxy.NhinPatientDiscoveryDeferredRespProxyObjectFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hl7.v3.MCCIIN000002UV01;
import org.hl7.v3.PRPAIN201306UV02;
import org.hl7.v3.RespondingGatewayPRPAIN201306UV02RequestType;

/**
 *
 * @author Neil Webb
 */
public class PassthruPatientDiscoveryDeferredRespOrchImpl {

    private static Log log = LogFactory.getLog(PassthruPatientDiscoveryDeferredRespOrchImpl.class);

    /**
     *
     * @param request
     * @param assertion
     * @param targetSystem
     * @return Patient Discovery Response Acknowledgement
     */
    public MCCIIN000002UV01 proxyProcessPatientDiscoveryAsyncResp(PRPAIN201306UV02 request, AssertionType assertion, NhinTargetSystemType targetSystem) {
        log.debug("Begin - proxyProcessPatientDiscoveryAsyncResp");

        MCCIIN000002UV01 response = null;
        // Audit the Patient Discovery Request Message sent on the Nhin Interface
        PatientDiscoveryAuditLogger auditLog = new PatientDiscoveryAuditLogger();
        auditLog.auditNhinDeferred201306(request, assertion, NhincConstants.AUDIT_LOG_OUTBOUND_DIRECTION);

        NhinPatientDiscoveryDeferredRespProxyObjectFactory patientDiscoveryFactory = new NhinPatientDiscoveryDeferredRespProxyObjectFactory();
        NhinPatientDiscoveryDeferredRespProxy proxy = patientDiscoveryFactory.getNhinPatientDiscoveryAsyncRespProxy();

        RespondingGatewayPRPAIN201306UV02RequestType nhinResponse = new RespondingGatewayPRPAIN201306UV02RequestType();
        nhinResponse.setPRPAIN201306UV02(request);
        nhinResponse.setAssertion(assertion);
        NhinTargetCommunitiesType targets = new NhinTargetCommunitiesType();
        NhinTargetCommunityType target = new NhinTargetCommunityType();
        target.setHomeCommunity(targetSystem.getHomeCommunity());
        targets.getNhinTargetCommunity().add(target);
        nhinResponse.setNhinTargetCommunities(targets);

        response = proxy.respondingGatewayPRPAIN201306UV02(request, assertion, targetSystem);

        // Audit the Patient Discovery Response Message received on the Nhin Interface
        auditLog.auditAck(response, assertion, NhincConstants.AUDIT_LOG_INBOUND_DIRECTION, NhincConstants.AUDIT_LOG_NHIN_INTERFACE);

        log.debug("End - proxyProcessPatientDiscoveryAsyncResp");

        return response;
    }
}