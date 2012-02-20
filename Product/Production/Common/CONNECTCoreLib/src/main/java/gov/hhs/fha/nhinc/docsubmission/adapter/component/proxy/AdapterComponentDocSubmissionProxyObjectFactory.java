/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 * Copyright 2010(Year date of delivery) United States Government, as represented by the Secretary of Health and Human Services.  All rights reserved.
 *  
 */
package gov.hhs.fha.nhinc.docsubmission.adapter.component.proxy;

import gov.hhs.fha.nhinc.proxy.ComponentProxyObjectFactory;

/**
 * @author svalluripalli
 */
public class AdapterComponentDocSubmissionProxyObjectFactory extends ComponentProxyObjectFactory
{
    private static final String CONFIG_FILE_NAME = "DocumentSubmissionProxyConfig.xml";
    private static final String BEAN_NAME = "adaptercomponentdocsubmission";

    protected String getConfigFileName() {
        return CONFIG_FILE_NAME;
    }

    public AdapterComponentDocSubmissionProxy getAdapterComponentDocSubmissionProxy() {
        return getBean(BEAN_NAME, AdapterComponentDocSubmissionProxy.class);
    }

}
