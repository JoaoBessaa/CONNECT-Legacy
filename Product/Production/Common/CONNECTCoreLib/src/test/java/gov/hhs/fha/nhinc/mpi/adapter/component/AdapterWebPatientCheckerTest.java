/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.hhs.fha.nhinc.mpi.adapter.component;

import gov.hhs.fha.nhinc.mpilib.*;
import gov.hhs.fha.nhinc.mpi.adapter.component.hl7parsers.HL7Parser201306;
import org.hl7.v3.II;
import org.hl7.v3.PRPAIN201305UV02;
import org.hl7.v3.PRPAIN201306UV02;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Jon Hoppesch
 */
public class AdapterWebPatientCheckerTest {
    private static Log log = LogFactory.getLog(AdapterWebPatientCheckerTest.class);

    public AdapterWebPatientCheckerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of FindPatient method, of class PatientChecker.
     */
    @Test
    public void SinglePatientExists() {
        log.info("SinglePatientExists");
        II subjectId = new II();
        subjectId.setRoot("2.16.840.1.113883.3.200");
        subjectId.setExtension("1234");

        PRPAIN201305UV02 query = TestHelper.build201305("Joe", "Smith", "M", "March 1, 1956", subjectId);

        Identifier patId = new Identifier();
        patId.setId("1234");
        patId.setOrganizationId("2.16.840.1.113883.3.200");
        Patient patient = TestHelper.createMpiPatient("Joe", "Smith", "M", "March 1, 1956", patId);
        Patients patients = new Patients();
        patients.add(patient);

        PRPAIN201306UV02 expResult = HL7Parser201306.BuildMessageFromMpiPatient(patients, query);
        PatientChecker checker = new PatientChecker();
        PRPAIN201306UV02 result = checker.FindPatient(query);

        // Verify the messages match
        TestHelper.AssertPatientIdsAreSame(expResult, result);
        TestHelper.AssertPatientGendersAreSame(expResult, result);
        TestHelper.AssertPatientBdaysAreSame(expResult, result);
        TestHelper.AssertPatientNamesAreSame(expResult, result);
    }

    @Test
    public void PatientDoesNotExist() {
        log.info("PatientDoesNotExist");
        II subjectId = new II();
        subjectId.setRoot("2.16.840.1.113883.3.200");
        subjectId.setExtension("1239");
        PRPAIN201305UV02 query = TestHelper.build201305("Joe", "Anderson", "M", "March 1, 1956", subjectId);
        PatientChecker checker = new PatientChecker();
        PRPAIN201306UV02 result = checker.FindPatient(query);
        assertNotNull(result);
    }
}