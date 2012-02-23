/*
 * This code is subject to the HIEOS License, Version 1.0
 *
 * Copyright(c) 2008-2009 Vangent, Inc.  All rights reserved.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * AdtRecordBean.java
 *
 * Created on September 29, 2005, 11:21 AM
 *
 */
package com.vangent.hieos.adt.db;

import com.vangent.hieos.xutil.exception.XdsInternalException;

import java.sql.SQLException;
import java.util.Collection;

/**
 *
 * @author mccaffrey
 */
public class AdtRecordBean {

    private AdtRecord record = null;
    private int uuidCharsToUse = 15;
    /**
     *
     */
    public static final String PATIENT_ID_DOMAIN_SEPARATOR = "^^^";
    /**
     *
     */
    public static final String FAKE_PATIENT_ID_PREFIX = "";

    /** Creates a new instance of AdtRecordBean
     */
    public AdtRecordBean() {
        record = new AdtRecord();
    }

    /**
     *
     * @return
     */
    public String getPatientId() {
        return record.getPatientId();
    }

    /**
     *
     * @param patientId
     */
    public void setPatientId(String patientId) {
        record.setPatientId(patientId);
    }

    /**
     *
     * @return
     */
    public String getPatientUUID() {
        return record.getUuid();
    }

    /**
     *
     * @param uuid
     */
    public void setPatientUUID(String uuid) {
        record.setUuid(uuid);
    }

    /**
     *
     * @param domain
     */
    public void setPatientIdAutoGenerated(String domain) {
        record.setPatientId(this.FAKE_PATIENT_ID_PREFIX + AdtRecordBean.firstNChars(AdtRecordBean.stripUuid(this.getId()), uuidCharsToUse) + AdtRecordBean.PATIENT_ID_DOMAIN_SEPARATOR + domain);
    }

    /**
     *
     * @return
     */
    public String getTimestamp() {
        return record.getTimestamp();
    }

    /**
     *
     * @param timestamp
     */
    public void setTimestamp(String timestamp) {
        record.setTimestamp(timestamp);
    }

    /**
     *
     * @param n
     */
    public void setUuidChars(int n) {
        uuidCharsToUse = n;
    }

    /**
     *
     * @return
     */
    public String getPatientAdminSex() {
        return record.getPatientAdminSex();
    }

    /**
     *
     * @param patientAdminSex
     */
    public void setPatientAdminSex(String patientAdminSex) {
        record.setPatientAdminSex(patientAdminSex);
    }

    /**
     *
     * @return
     */
    public String getPatientAccountNumber() {
        return record.getPatientAccountNumber();
    }

    /**
     *
     * @param patientAccountNumber
     */
    public void setPatientAccountNumber(String patientAccountNumber) {
        record.setPatientAccountNumber(patientAccountNumber);
    }

    /**
     *
     * @return
     */
    public String getPatientBedId() {
        return record.getPatientBedId();
    }

    /**
     *
     * @param patientBedId
     */
    public void setPatientBedId(String patientBedId) {
        record.setPatientBedId(patientBedId);
    }

    /**
     *
     * @return
     */
    public String getPatientBirthDateTime() {
        return record.getPatientBirthDateTime();
    }

    /**
     *
     * @param patientBirthDateTime
     */
    public void setPatientBirthDateTime(String patientBirthDateTime) {
        record.setPatientBirthDateTime(patientBirthDateTime);
    }

    /**
     *
     * @return
     */
    public Collection getPatientRace() {
        return record.getPatientRace();
    }

    /**
     *
     * @param patientRace
     */
    public void setPatientRace(Collection patientRace) {
        record.setPatientRace(patientRace);
    }

    /**
     *
     * @param race
     */
    public void setAddRace(String race) {
        this.setAddRace(new Hl7Race(this.getId(), race));
    }

    /**
     *
     * @param race
     */
    public void setAddRace(Hl7Race race) {
        record.getPatientRace().add(race);
    }

    /**
     *
     * @return
     */
    public Collection getPatientNames() {
        return record.getPatientNames();
    }

    /**
     *
     * @param patientNames
     */
    public void setPatientNames(Collection patientNames) {
        record.setPatientNames(patientNames);
    }

    /**
     *
     * @param name
     */
    public void setAddName(Hl7Name name) {
        record.getPatientNames().add(name);
    }

    /**
     *
     * @param familyName
     * @param givenName
     * @param secondAndFurtherName
     * @param suffix
     * @param prefix
     * @param degree
     */
    public void setAddName(String familyName, String givenName, String secondAndFurtherName,
            String suffix, String prefix, String degree) {

        Hl7Name name = new Hl7Name(this.getId(), familyName, givenName, secondAndFurtherName,
                suffix, prefix, degree);
        this.setAddName(name);
    }

    /**
     *
     * @return
     */
    public Collection getPatientAddresses() {
        return record.getPatientAddresses();
    }

    /**
     *
     * @param patientAddress
     */
    public void setPatientAddresses(Collection patientAddress) {
        record.setPatientAddresses(patientAddress);
    }

    /**
     *
     * @param address
     */
    public void setAddAddress(Hl7Address address) {
        record.getPatientAddresses().add(address);
    }

    /**
     *
     * @param streetAddress
     * @param otherDesignation
     * @param city
     * @param stateOrProvince
     * @param zipCode
     * @param country
     * @param countyOrParish
     */
    public void setAddAddress(String streetAddress, String otherDesignation, String city, String stateOrProvince,
            String zipCode, String country, String countyOrParish) {
        Hl7Address address = new Hl7Address(this.getId(), streetAddress, otherDesignation, city, stateOrProvince,
                zipCode, country, countyOrParish);
        this.setAddAddress(address);
    }

    /**
     * 
     * @throws java.sql.SQLException
     * @throws com.vangent.hieos.xutil.exception.XdsInternalException
     */
    public void saveToDatabase() throws SQLException, XdsInternalException {
        record.saveToDatabase();
    }

    /**
     *
     * @return
     */
    public String getId() {
        return record.getUuid();
    }

    /**
     *
     * @return
     */
    public AdtRecord getRecord() {
        return record;
    }

    /**
     *
     * @param record
     */
    public void setRecord(AdtRecord record) {
        this.record = record;
    }

    /**
     *
     * @param uuid
     * @return
     */
    static public String stripUuid(String uuid) {
        String withoutColons = uuid.substring(uuid.lastIndexOf(':') + 1);
        StringBuffer withoutHyphens = new StringBuffer();
        for (int i = 0; i < withoutColons.length(); i++) {
            if (withoutColons.charAt(i) != '-') {
                withoutHyphens.append(withoutColons.charAt(i));
            }
        }
        return withoutHyphens.toString();
    }

    /**
     *
     * @param str
     * @param n
     * @return
     */
    static public String firstNChars(String str, int n) {
        return str.substring(0, n);
    }
}
