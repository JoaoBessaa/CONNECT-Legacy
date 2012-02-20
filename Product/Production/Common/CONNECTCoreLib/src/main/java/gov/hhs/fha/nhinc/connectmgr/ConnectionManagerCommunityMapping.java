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
package gov.hhs.fha.nhinc.connectmgr;

import gov.hhs.fha.nhinc.common.connectionmanager.dao.AssigningAuthorityHomeCommunityMappingDAO;
import gov.hhs.fha.nhinc.common.nhinccommon.HomeCommunityType;
import gov.hhs.fha.nhinc.nhinclib.NullChecker;

/**
 *
 * @author jhoppesc
 */
public class ConnectionManagerCommunityMapping {

    /**
     *
     * @param requestType
     * @return GetHomeCommunityByAssigningAuthorityResponseType
     */
    public static HomeCommunityType getHomeCommunityByAssigningAuthority(String assigningAuthId) {
        HomeCommunityType hc = new HomeCommunityType();

        // Verify assigning authority id is valid
        if (NullChecker.isNotNullish(assigningAuthId)) {
            AssigningAuthorityHomeCommunityMappingDAO mappingDao = new AssigningAuthorityHomeCommunityMappingDAO();
            hc.setHomeCommunityId(mappingDao.getHomeCommunityId(assigningAuthId));
        }
        else {
            hc = null;
        }

        return hc;
    }
}
