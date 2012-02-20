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

package gov.hhs.fha.nhinc.docquery.nhin.deferred.request.proxy;

import gov.hhs.fha.nhinc.proxy.ComponentProxyObjectFactory;

/**
 *
 * @author jhoppesc
 */
public class NhinDocQueryDeferredRequestProxyObjectFactory extends ComponentProxyObjectFactory {
    private static final String CONFIG_FILE_NAME = "DocumentQueryDeferredProxyConfig.xml";
    private static final String BEAN_NAME = "nhindocquerydeferredrequest";

    protected String getConfigFileName()
    {
        return CONFIG_FILE_NAME;
    }

    public NhinDocQueryDeferredRequestProxy getNhinDocQueryDeferredRequestProxy()
    {
        return getBean(BEAN_NAME, NhinDocQueryDeferredRequestProxy.class);
   }

}
