/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 * Copyright 2010(Year date of delivery) United States Government, as represented by the Secretary of Health and Human Services.  All rights reserved.
 *  
 */
//
// Non-Export Controlled Information
//
//####################################################################
//## The MIT License
//##
//## Copyright (c) 2010 Harris Corporation
//##
//## Permission is hereby granted, free of charge, to any person
//## obtaining a copy of this software and associated documentation
//## files (the "Software"), to deal in the Software without
//## restriction, including without limitation the rights to use,
//## copy, modify, merge, publish, distribute, sublicense, and/or sell
//## copies of the Software, and to permit persons to whom the
//## Software is furnished to do so, subject to the following conditions:
//##
//## The above copyright notice and this permission notice shall be
//## included in all copies or substantial portions of the Software.
//##
//## THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
//## EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
//## OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
//## NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
//## HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
//## WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
//## FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
//## OTHER DEALINGS IN THE SOFTWARE.
//##
//####################################################################
//********************************************************************
// FILE: ProducerProxyPropertiesFacade.java
//
// Copyright (C) 2010 Harris Corporation. All rights reserved.
//
// CLASSIFICATION: Unclassified
//
// DESCRIPTION: ProducerProxyPropertiesFacade.java 
//              
//
// LIMITATIONS: None
//
// SOFTWARE HISTORY:
//
//> Feb 24, 2010 PTR#  - R. Robinson
// Initial Coding.
//<
//********************************************************************
package gov.hhs.fha.nhinc.lift.proxy.properties.interfaces;

import gov.hhs.fha.nhinc.lift.common.util.LiftConnectionRequestToken;
import java.io.IOException;
import java.net.Socket;


public interface ProducerProxyPropertiesFacade {
	/**
	 * Should return if the request GUID is known
	 * @param request
	 * @return
	 */
	public boolean verifySecurityForRequest(LiftConnectionRequestToken request);

        /**
	 * A retrieval had completed furfilling the request
	 * @param request
	 * @return
	 */
	public void completeProcessingForRequest(LiftConnectionRequestToken request);

        /**
	 * A retrieval has been terminated
	 * @param request
	 * @return
	 */
	public void terminateProcessingForRequest(LiftConnectionRequestToken request);
	
	/**
	 * Should return a socket connected to the server that this request
	 * corresponds to.  Such servers might be a file server or image renderer
	 * server.
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	public Socket getSocketToServerForRequest(LiftConnectionRequestToken request) throws IOException;

        public void setTrustStore();
        
	public void setKeyStoreProperty();
}
