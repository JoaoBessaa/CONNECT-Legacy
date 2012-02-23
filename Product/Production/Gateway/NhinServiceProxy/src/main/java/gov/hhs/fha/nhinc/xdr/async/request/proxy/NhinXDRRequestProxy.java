package gov.hhs.fha.nhinc.xdr.async.request.proxy;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetSystemType;
import ihe.iti.xdr._2007.AcknowledgementType;
import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequestType;

/**
 *
 * @author Neil Webb
 */
public interface NhinXDRRequestProxy
{
    public AcknowledgementType provideAndRegisterDocumentSetBRequest(ProvideAndRegisterDocumentSetRequestType request, AssertionType assertion, NhinTargetSystemType targetSystem);
}
