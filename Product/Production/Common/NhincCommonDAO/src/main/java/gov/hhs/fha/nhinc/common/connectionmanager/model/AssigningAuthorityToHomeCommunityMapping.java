/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.hhs.fha.nhinc.common.connectionmanager.model;

/**
 *
 * @author svalluripalli
 */
public class AssigningAuthorityToHomeCommunityMapping {
    private Long id;
    private String assigningAuthorityId = "";
    private String homeCommunityId = "";

    public Long getId(){
        return id;
    }
    
    public void setId(Long id){
        this.id = id;
    }
    
    public String getAssigningAuthorityId() {
        return assigningAuthorityId;
    }

    public void setAssigningAuthorityId(String assigningAuthorityId) {
        this.assigningAuthorityId = assigningAuthorityId;
    }

    public String getHomeCommunityId() {
        return homeCommunityId;
    }

    public void setHomeCommunityId(String homeCommunityId) {
        this.homeCommunityId = homeCommunityId;
    }
    
}
