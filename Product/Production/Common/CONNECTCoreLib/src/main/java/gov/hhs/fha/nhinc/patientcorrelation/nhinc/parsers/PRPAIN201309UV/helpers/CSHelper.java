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

package gov.hhs.fha.nhinc.patientcorrelation.nhinc.parsers.PRPAIN201309UV.helpers;

import org.hl7.v3.CS;

/**
 *
 * @author rayj
 */
public class CSHelper {
    public static CS buildCS(String code) {
        CS csCode = new CS();
        csCode.setCode(code);
        return csCode;
    }

}
