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

package gov.hhs.fha.nhinc.docsubmission.entity.deferred.response.proxy;

import gov.hhs.fha.nhinc.proxy.ComponentProxyObjectFactory;

/**
 * @author Jon Hoppesch
 */
public class EntityDocSubmissionDeferredResponseProxyObjectFactory extends ComponentProxyObjectFactory {

    private static final String CONFIG_FILE_NAME = "EntityXDRAsyncRespProxyConfig.xml";
    private static final String BEAN_NAME = "entityxdrasyncresp";

    protected String getConfigFileName() {
        return CONFIG_FILE_NAME;
    }

    public EntityDocSubmissionDeferredResponseProxy getEntityDocSubmissionDeferredRespProxy() {
        return getBean(BEAN_NAME, EntityDocSubmissionDeferredResponseProxy.class);
    }

}
