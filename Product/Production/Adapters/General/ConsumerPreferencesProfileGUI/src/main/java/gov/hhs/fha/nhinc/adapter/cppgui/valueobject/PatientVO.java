/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.hhs.fha.nhinc.adapter.cppgui.valueobject;

import gov.hhs.fha.nhinc.adapter.cppgui.valueobject.PatientPreferencesVO;

/**
 *
 * @author patlollav
 */
public class PatientVO {
    private String firstName;
    private String lastName;
    private String middleName;
    private String patientID;
    private String organizationID;
    private String assigningAuthorityID;
    private PatientPreferencesVO  patientPreferences;

    public PatientPreferencesVO getPatientPreferences() {
        return patientPreferences;
    }

    public void setPatientPreferences(PatientPreferencesVO patientPreferences) {
        this.patientPreferences = patientPreferences;
    }

    
    public String getAssigningAuthorityID() {
        return assigningAuthorityID;
    }

    public void setAssigningAuthorityID(String assigningAuthorityID) {
        this.assigningAuthorityID = assigningAuthorityID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(String organizationID) {
        this.organizationID = organizationID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

}
