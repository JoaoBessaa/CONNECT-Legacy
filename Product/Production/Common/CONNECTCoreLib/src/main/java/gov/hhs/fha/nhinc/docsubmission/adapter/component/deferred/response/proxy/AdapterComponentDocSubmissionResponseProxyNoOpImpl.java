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
package gov.hhs.fha.nhinc.docsubmission.adapter.component.deferred.response.proxy;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.healthit.nhin.XDRAcknowledgementType;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author jhoppesc
 */
public class AdapterComponentDocSubmissionResponseProxyNoOpImpl implements AdapterComponentDocSubmissionResponseProxy
{

    private Log log = null;

    /**
     * Default constructor.
     */
    public AdapterComponentDocSubmissionResponseProxyNoOpImpl()
    {
        log = createLogger();
    }

    /**
     * Creates the log object for logging.
     *
     * @return The log object.
     */
    protected Log createLogger()
    {
        return ((log != null) ? log : LogFactory.getLog(getClass()));
    }

    /**
     * Receive document deferred document submission response.
     *
     * @param body The doc submission response message.
     * @param assertion The assertion information.
     * @return The ACK
     */
    public XDRAcknowledgementType provideAndRegisterDocumentSetBResponse(RegistryResponseType body, AssertionType assertion)
    {

        log.debug("Entering AdapterComponentDocSubmissionResponseProxyNoOpImpl.provideAndRegisterDocumentSetBResponse");
        return new XDRAcknowledgementType();
    }
}
