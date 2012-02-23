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
package gov.hhs.fha.nhinc.mpilib;

/**
 *
 * @author rayj
 */
public interface IMPI {
    Patient AddUpdate(Patient value);
    Patients Search(Patient searchParams, boolean AllowSearchByDemographics);
    Patients Search(Patient searchParams);
}
