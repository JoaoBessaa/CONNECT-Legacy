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

package gov.hhs.fha.nhinc.assemblymanager.builder.cda.modules;

import gov.hhs.fha.nhinc.assemblymanager.builder.DocumentBuilderException;
import java.util.List;
import org.hl7.v3.POCDMT000040Entry;

/**
 *
 * @author kim
 */
public interface CDAModule {

   public List<POCDMT000040Entry> build() throws DocumentBuilderException;

}
