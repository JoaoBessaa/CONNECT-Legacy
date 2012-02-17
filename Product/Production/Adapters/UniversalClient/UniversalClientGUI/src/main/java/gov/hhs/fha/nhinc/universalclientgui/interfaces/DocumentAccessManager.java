package gov.hhs.fha.nhinc.universalclientgui.interfaces;

import gov.hhs.fha.nhinc.adapter.commondatalayer.DoDConnectorPortType;
import gov.hhs.fha.nhinc.adapter.commondatalayer.DoDConnectorService;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.CeType;
import gov.hhs.fha.nhinc.common.nhinccommon.HomeCommunityType;
import gov.hhs.fha.nhinc.common.nhinccommon.PersonNameType;
import gov.hhs.fha.nhinc.common.nhinccommon.UserType;
import org.netbeans.xml.schema.docviewer.RetrievedDocumentDisplayObject;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.ws.BindingProvider;
import java.net.URL;
import javax.xml.namespace.QName;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.ResponseOptionType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.AdhocQueryType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.SlotType1;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ValueListType;
import org.hl7.v3.PatientDemographicsPRPAIN201307UV02RequestType;
import org.hl7.v3.PatientDemographicsPRPAMT201303UV02ResponseType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import universalclientgui.ApplicationBean1;
import universalclientgui.SessionBean1;
import universalclientgui.CryptAHLTA;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthnStatementType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthzDecisionStatementEvidenceAssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthzDecisionStatementEvidenceType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthzDecisionStatementType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthzDecisionStatementEvidenceConditionsType;

/**
 *
 * @author Duane DeCouteau
 */
public class DocumentAccessManager extends Thread {
    private static Log log = LogFactory.getLog(DocumentAccessManager.class);

    private String patientUnitNumber = "";
    private String dateOfBirth = "";
    private String patientGender = "";
    private String patientLastName = "";
    private String patientFirstName = "";
    private String patientMiddleInitial = "";
    private String role = "";
    private String roleCode = "";
    private String roleSystem = "";
    private String roleSystemName = "";
    private String roleSystemVersion = "";
    private String userName = "";
    private String providerId = "";
    private String providerName = "";
    private String providerFirstName = "";
    private String providerLastName = "";
    private String providerMiddleName = "";
    private String purposeOfUse = "";
    private String purposeOfUseCode = "";
    private String purposeOfUseCodeSystem = "";
    private String purposeOfUseCodeSystemName = "";
    private String purposeOfUseCodeSystemVersion = "";
    private String assigningAuthorityId = "";
    private String uniqueDocumentId = "";
    private String claimFormRef = "";
    private String claimFormRaw = "";
    private String signatureDate = "";
    private String expirationDate = "";
    private int numberOfYears;
    private static final String CREATION_TIME_FROM_SLOT_NAME = "$XDSDocumentEntryCreationTimeFrom";
    private static final String CREATION_TIME_TO_SLOT_NAME = "$XDSDocumentEntryCreationTimeTo";
    private static final String HL7_DATE_FORMAT = "yyyyMMddHHmmssZ";
    private static final String REGULAR_DATE_FORMAT = "MM/dd/yyyy";

    private ApplicationBean1 app;
    private SessionBean1 session;

    //patient provider ids from url instantiation
    private String encryptedPatientId;
    private String encryptedProviderId;

    //PAWS processing should use below
    public DocumentAccessManager(ApplicationBean1 app, SessionBean1 sess, String ePatientId,
                                 String eProviderId) {

        log.debug("DocumentAccessManager - Entering PAWS Enabled Instance");

        this.app = app;
        this.session = sess;

        this.encryptedPatientId = ePatientId;
        this.encryptedProviderId = eProviderId;


    }

    public void run() {

        session.setStillProcessing(true);
        //set initial new status
        updateInitialProcessingStatus("Acquiring Local Patient and Provider Information.");
        //get patient info
        decodePatientId(encryptedPatientId);
        patientInfoLookup();
        session.setPawsPatientLookupComplete(true);
        //get provider info
        decodeProviderId(encryptedProviderId);
        providerInfoLookup();
        session.setPawsProviderLookupComplete(true);

        updateInitialProcessingStatus("Searching All Organizations");

        AdhocQueryRequest query = createAdhocQueryRequest();
        AssertionType assertion = createAssertion();

        //set session assertion info this is new to support paws action
        session.setAssertion(assertion);

        try { // Call Web Service Operation
            gov.hhs.fha.nhinc.universalclient.ws.DocViewerRequestServicesService service = new gov.hhs.fha.nhinc.universalclient.ws.DocViewerRequestServicesService();
            gov.hhs.fha.nhinc.universalclient.ws.DocViewerRequestServicesPortType port = service.getDocViewerRequestServicesPort();
			gov.hhs.fha.nhinc.webserviceproxy.WebServiceProxyHelper.getInstance().initializePort((javax.xml.ws.BindingProvider) port, app.getDocViewerRequestService());
            org.netbeans.xml.schema.docviewer.DocViewerRequestType docRequest = new org.netbeans.xml.schema.docviewer.DocViewerRequestType();
            docRequest.setAdhocQueryRequest(query);
            docRequest.setAssertion(assertion);
            docRequest.setHomeCommunityId(app.getHomeCommunityId());
            docRequest.setPatientId(patientUnitNumber);
            docRequest.setUserId(providerId);

            org.netbeans.xml.schema.docviewer.DocViewerResponseType result = port.requestAllNHINDocuments(docRequest);
            System.out.println("Result = "+result);
        } catch (Exception ex) {
            log.error("DocumentAccessManager:run ERROR "+ex.getMessage());
            ex.printStackTrace();
        }

        session.setStillProcessing(false);
        session.setProcessingLBLString("Processing complete.  All available documents are listed below.");
       log.debug("DocumentAccessManager - Exiting Instance");
    }


    /**
     *  build getPatientInfo Request
     */
    org.hl7.v3.PatientDemographicsPRPAIN201307UV02RequestType buildPawsRequest(String pId)
    {
        //II
        org.hl7.v3.II ii = new org.hl7.v3.II();
        ii.setExtension(pId);

        //PatientIdentfier
        org.hl7.v3.PRPAMT201307UVPatientIdentifier id = new org.hl7.v3.PRPAMT201307UVPatientIdentifier();
        id.getValue().add(ii);

        //Parameter List
        org.hl7.v3.PRPAMT201307UVParameterList paramList = new org.hl7.v3.PRPAMT201307UVParameterList();
        paramList.getPatientIdentifier().add(id);

        //Query By Parameter
        org.hl7.v3.PRPAMT201307UVQueryByParameter qparams = new org.hl7.v3.PRPAMT201307UVQueryByParameter();
        qparams.setParameterList(paramList);

        //objectfactory
        org.hl7.v3.ObjectFactory obf = new org.hl7.v3.ObjectFactory();

        //control act process
        org.hl7.v3.PRPAIN201307UV02QUQIMT021001UV01ControlActProcess controlActProcess = new org.hl7.v3.PRPAIN201307UV02QUQIMT021001UV01ControlActProcess();
        controlActProcess.setQueryByParameter(obf.createPRPAIN201307UV02QUQIMT021001UV01ControlActProcessQueryByParameter(qparams));

        //message
        org.hl7.v3.PRPAIN201307UV02MCCIMT000100UV01Message patientMessage = new org.hl7.v3.PRPAIN201307UV02MCCIMT000100UV01Message();
        patientMessage.setControlActProcess(controlActProcess);

        //request and query
        PatientDemographicsPRPAIN201307UV02RequestType request = new PatientDemographicsPRPAIN201307UV02RequestType();
        request.setQuery(patientMessage);

        return request;

    }

    private AdhocQueryRequest createAdhocQueryRequest() {
            AdhocQueryRequest query = new AdhocQueryRequest();
            query.setFederated(false);
            query.setStartIndex(BigInteger.valueOf(0));
            query.setMaxResults(BigInteger.valueOf(-1));
            ResponseOptionType resp = new ResponseOptionType();
            resp.setReturnComposedObjects(true);
            resp.setReturnType("LeafClass");
            query.setResponseOption(resp);

            AdhocQueryType queryType = new AdhocQueryType();
            queryType.setId("urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d");


            SlotType1 patientslot = new SlotType1();
            patientslot.setName("$XDSDocumentEntryPatientId");
            ValueListType patientvalList = new ValueListType();
            StringBuffer sb = new StringBuffer();
            sb.append(patientUnitNumber);
            sb.append("^^^&");
            sb.append(app.getHomeCommunityId());
            sb.append("&ISO");
            patientvalList.getValue().add(sb.toString());
            patientslot.setValueList(patientvalList);
            queryType.getSlot().add(patientslot);

            SlotType1 entryslot = new SlotType1();
            entryslot.setName("$XDSDocumentEntryStatus");
            ValueListType entryvalList = new ValueListType();
            entryvalList.getValue().add("('urn:oasis:names:tc:ebxml-regrep:StatusType:Approved')");
            entryslot.setValueList(entryvalList);
            queryType.getSlot().add(entryslot);

            //Set Creation From Date
            SlotType1 creationStartTimeSlot = new SlotType1();
            creationStartTimeSlot.setName(CREATION_TIME_FROM_SLOT_NAME);

            ValueListType creationStartTimeValueList = new ValueListType();
            Date creationFromDate = getStartDate();
            creationStartTimeValueList.getValue().add(formatDate(creationFromDate, HL7_DATE_FORMAT));

            creationStartTimeSlot.setValueList(creationStartTimeValueList);
            queryType.getSlot().add(creationStartTimeSlot);

            // Set Creation To Date
            SlotType1 creationEndTimeSlot = new SlotType1();
            creationEndTimeSlot.setName(CREATION_TIME_TO_SLOT_NAME);

            ValueListType creationEndTimeSlotValueList = new ValueListType();
            Date creationToDate = new Date();
            creationEndTimeSlotValueList.getValue().add(formatDate(creationToDate, HL7_DATE_FORMAT));

            creationEndTimeSlot.setValueList(creationEndTimeSlotValueList);
            queryType.getSlot().add(creationEndTimeSlot);


            query.setAdhocQuery(queryType);

            return query;
    }

    public AssertionType createAssertion() {
        AssertionType assertion = new AssertionType();
        assertion.setDateOfBirth(dateOfBirth);
        SamlAuthnStatementType samlAuthnStatement = new SamlAuthnStatementType();
        SamlAuthzDecisionStatementType samlAuthzDecisionStatement = new SamlAuthzDecisionStatementType();
        SamlAuthzDecisionStatementEvidenceType samlAuthzDecisionStatementEvidence = new SamlAuthzDecisionStatementEvidenceType();
        SamlAuthzDecisionStatementEvidenceAssertionType samlAuthzDecisionStatementAssertion = new SamlAuthzDecisionStatementEvidenceAssertionType();
        SamlAuthzDecisionStatementEvidenceConditionsType samlAuthzDecisionStatementEvidenceConditions = new SamlAuthzDecisionStatementEvidenceConditionsType();
        samlAuthzDecisionStatementEvidenceConditions.setNotOnOrAfter(expirationDate);
        samlAuthzDecisionStatementEvidenceConditions.setNotBefore(signatureDate);
        samlAuthzDecisionStatementAssertion.setConditions(samlAuthzDecisionStatementEvidenceConditions);
        samlAuthzDecisionStatementAssertion.setAccessConsentPolicy(claimFormRef);
        samlAuthzDecisionStatementEvidence.setAssertion(samlAuthzDecisionStatementAssertion);
        samlAuthzDecisionStatement.setEvidence(samlAuthzDecisionStatementEvidence);
        assertion.setSamlAuthzDecisionStatement(samlAuthzDecisionStatement);
        assertion.setSamlAuthnStatement(samlAuthnStatement);

        PersonNameType pName = new PersonNameType();
        pName.setFamilyName(patientLastName);
        pName.setGivenName(patientFirstName);
        pName.setSecondNameOrInitials(patientMiddleInitial);
        assertion.setPersonName(pName);

        HomeCommunityType hc = new HomeCommunityType();
        hc.setDescription(app.getHomeCommunityDesc());
        hc.setHomeCommunityId(app.getHomeCommunityId());
        hc.setName(app.getHomeCommunityName());


        UserType muser = new UserType();
        CeType roleType = new CeType();
        roleType.setCode(roleCode);
        roleType.setCodeSystem(roleSystem);
        roleType.setCodeSystemName(roleSystemName);
        roleType.setCodeSystemVersion(roleSystemVersion);
        roleType.setDisplayName(role);
        roleType.setOriginalText(role);
        muser.setOrg(hc);
        muser.setRoleCoded(roleType);
        //VA DoD Requirement
        String uPlusDoD = userName + "*DoD";
        muser.setUserName(uPlusDoD);
        PersonNameType uName = new PersonNameType();
        CeType nType = new CeType();
        nType.setCode("P");
        nType.setCodeSystem("30");
        nType.setCodeSystemName("nameType");
        nType.setCodeSystemVersion("1.0");
        nType.setDisplayName("P");
        nType.setOriginalText("P");

        uName.setNameType(nType);
        uName.setFamilyName(providerLastName);
        uName.setGivenName(providerFirstName);
        uName.setSecondNameOrInitials(providerMiddleName);
        muser.setPersonName(uName);
        assertion.setUserInfo(muser);

        assertion.setHomeCommunity(hc);
        CeType pouType = new CeType();
        pouType.setCode(purposeOfUseCode);
        pouType.setCodeSystem(purposeOfUseCodeSystem);
        pouType.setCodeSystemName(purposeOfUseCodeSystemName);
        pouType.setCodeSystemVersion(purposeOfUseCodeSystemVersion);
        pouType.setDisplayName(purposeOfUse);
        pouType.setOriginalText(purposeOfUse);
        assertion.setPurposeOfDisclosureCoded(pouType);

        return assertion;
    }

    private String formatDate(String dateString, String inputFormat, String outputFormat) {
        SimpleDateFormat inputFormatter = new SimpleDateFormat(inputFormat);
        SimpleDateFormat outputFormatter = new SimpleDateFormat(outputFormat);

        Date date = null;

        try {
            date = inputFormatter.parse(dateString);
        } catch (Exception ex) {
            log.error("DocumentAccessManager.formatDate "+ex.getMessage());
        }

        return outputFormatter.format(date);
    }

    private String formatDate(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    private Date getStartDate() {
        Date res = new Date();
        try {
            Date d = new Date();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(d);
            gc.add(Calendar.YEAR, -app.getNumberOfYears());
            res = gc.getTime();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }

    private void decodePatientId(String dPatientId) {
       CryptAHLTA cipherOBJ = new CryptAHLTA();

       patientUnitNumber = cipherOBJ.decrypt(dPatientId);
       session.setPatientId(patientUnitNumber);
       log.debug("DocumentAccessManager - Decrypted patientId = " + patientUnitNumber);
    }

    private void decodeProviderId(String dProviderId) {
       CryptAHLTA cipherOBJ = new CryptAHLTA();

       providerId = cipherOBJ.decrypt(dProviderId);
       session.setProviderId(providerId);
       log.debug("DocumentAccessManager - Decrypted providerId = " + providerId);
    }

    private void providerInfoLookup() {
        //perform paws web service call for provider info here

        try {
            //retreieve patient info
            log.debug("DocumentAccessManager - Instantiating DOD Connector Service (" + app.getCALServiceWSDL() + ")...");

            DoDConnectorService calservice = new DoDConnectorService(new URL(app.getCALServiceWSDL()), new QName(app.getCALServiceQNAME(), app.getCALService()));

            log.debug("DocumentAccessManager - Retrieving the port from the following service: " + calservice.toString());

            DoDConnectorPortType port = calservice.getCommonDataLayerPort();

            //build provider info request
            org.hl7.v3.PatientDemographicsPRPAIN201307UV02RequestType providerReqType = buildPawsRequest(providerId);


            log.debug("DocumentAccessManager - Invoking getProviderInfo Operation: " + calservice.toString());
            PatientDemographicsPRPAMT201303UV02ResponseType providerInfoResponse = port.getProviderInfo(providerReqType);

            if (providerInfoResponse != null)
            {
                log.debug("DocumentAccessManager - getProviderInfo response returned from PAWS");

                //parse names
                javax.xml.bind.JAXBElement<org.hl7.v3.EnExplicitFamily> eef = (javax.xml.bind.JAXBElement<org.hl7.v3.EnExplicitFamily>) providerInfoResponse.getSubject().getPatientPerson().getValue().getName().get(0).getContent().get(0);
                providerLastName = eef.getValue().getContent();

                javax.xml.bind.JAXBElement<org.hl7.v3.EnExplicitGiven> eeg = (javax.xml.bind.JAXBElement<org.hl7.v3.EnExplicitGiven>) providerInfoResponse.getSubject().getPatientPerson().getValue().getName().get(0).getContent().get(1);
                providerFirstName = eeg.getValue().getContent();

                javax.xml.bind.JAXBElement<org.hl7.v3.EnExplicitDelimiter> eed = (javax.xml.bind.JAXBElement<org.hl7.v3.EnExplicitDelimiter>) providerInfoResponse.getSubject().getPatientPerson().getValue().getName().get(0).getContent().get(2);
                providerMiddleName = eed.getValue().getContent();

                javax.xml.bind.JAXBElement<org.hl7.v3.EnExplicitSuffix> ees = (javax.xml.bind.JAXBElement<org.hl7.v3.EnExplicitSuffix>) providerInfoResponse.getSubject().getPatientPerson().getValue().getName().get(0).getContent().get(3);
                providerName = ees.getValue().getContent();

                log.debug("DocumentAccessManager - SessionBean Provider Last Name = " + providerLastName);
                log.debug("DocumentAccessManager - SessionBean Provider First Name = " + providerFirstName);
                log.debug("DocumentAccessManager - SessionBean Provider Middle Name = " + providerMiddleName);
                log.debug("DocumentAccessManager - SessionBean Provider Full Name = " + providerName);

            }

            if (providerName != null)
                session.setProviderName(providerName);
            else
                session.setProviderName("");

            if (providerFirstName != null)
                session.setProviderFirstName(providerFirstName);
            else
                session.setProviderFirstName("");

            if (providerLastName != null)
                 session.setProviderLastName(providerLastName);
            else
                session.setProviderLastName("");

            if (providerMiddleName != null)
                 session.setProviderMiddleNameOrInitial(providerMiddleName);
            else
                session.setProviderMiddleNameOrInitial("");
        }
        catch (Exception ep) {
            log.error("DocumentAccessManager:providerLookup "+ep);
        }
    }

    private void patientInfoLookup() {
        //perform paws web service patient info lookup here

        try {
            //retreieve patient info
            log.debug("DocumentAccessManager - Instantiating DOD Connector Service (" + app.getCALServiceWSDL() + ")...");

            DoDConnectorService calservice = new DoDConnectorService(new URL(app.getCALServiceWSDL()), new QName(app.getCALServiceQNAME(), app.getCALService()));

            log.debug("DocumentAccessManager - Retrieving the port from the following service: " + calservice.toString());

            DoDConnectorPortType port = calservice.getCommonDataLayerPort();

            //build patient info request
            org.hl7.v3.PatientDemographicsPRPAIN201307UV02RequestType patientReqType = buildPawsRequest(patientUnitNumber);

            log.debug("DocumentAccessManager - Invoking getPatientInfo Operation: " + calservice.toString());
            PatientDemographicsPRPAMT201303UV02ResponseType patientInfoResponse = port.getPatienInfo(patientReqType);

            if (patientInfoResponse != null)
            {
                log.debug("DocumentAccessManager - getPatientInfo response returned from PAWS");

                dateOfBirth = patientInfoResponse.getSubject().getPatientPerson().getValue().getBirthTime().getValue();

                javax.xml.bind.JAXBElement<org.hl7.v3.EnExplicitGiven> eeg = (javax.xml.bind.JAXBElement<org.hl7.v3.EnExplicitGiven>) patientInfoResponse.getSubject().getPatientPerson().getValue().getName().get(0).getContent().get(1);
                patientFirstName = eeg.getValue().getContent();

                javax.xml.bind.JAXBElement<org.hl7.v3.EnExplicitFamily> eef = (javax.xml.bind.JAXBElement<org.hl7.v3.EnExplicitFamily>) patientInfoResponse.getSubject().getPatientPerson().getValue().getName().get(0).getContent().get(0);
                patientLastName = eef.getValue().getContent();

                patientGender = patientInfoResponse.getSubject().getPatientPerson().getValue().getAdministrativeGenderCode().getCode();

                log.debug("DocumentAccessManager - SessionBean Patient Date of Birth = " + dateOfBirth);
                log.debug("DocumentAccessManager - SessionBean Patient First Name = " + patientFirstName);
                log.debug("DocumentAccessManager - SessionBean Patient Last Name = " + patientLastName);
                log.debug("DocumentAccessManager - SessionBean Patient Gender = " + patientGender);

            }

            if (dateOfBirth != null)
                session.setPatientDOB(dateOfBirth);
            else
                session.setPatientDOB("");

            if (patientFirstName != null)
                session.setPatientFirstName(patientFirstName);
            else
                session.setPatientFirstName("");

            if (patientLastName != null)
                session.setPatientLastName(patientLastName);
            else
                session.setPatientLastName("");

            session.setPatientMiddleInitial("");

            session.setPatientName(patientLastName + "," + patientFirstName);

            if (patientGender != null)
                session.setPatientGender(patientGender);
            else
                session.setPatientGender("");
        }
        catch (Exception ep) {
            log.error("DocumentAccessManager:patientInfoLookup "+ep);
        }
    }

    private void updateInitialProcessingStatus(String status) {

            RetrievedDocumentDisplayObject[] docs = new RetrievedDocumentDisplayObject[1];
            RetrievedDocumentDisplayObject docInfo = new RetrievedDocumentDisplayObject();
            docInfo.setDocumentTitle("Summary of Episode");
            docInfo.setDocumentType("C32");
            docInfo.setCreationDate(formatDate(new Date(), REGULAR_DATE_FORMAT));
            docInfo.setOrganizationName(status);
            docInfo.setUniqueDocumentId("999999");
            docInfo.setRepositoryId("999999");
            docInfo.setOrgId(app.getHomeCommunityId());
            docInfo.setSelected(false);
            docInfo.setAvailableInLocalStore(false);
            docInfo.setDocumentStatus("Pending");
            docInfo.setPatientId(patientUnitNumber);
            docInfo.setRequestingUser(userName);
            docInfo.setOrigDocumentId("999999");
            docInfo.setOrigHomeCommunityId(app.getHomeCommunityId());
            docInfo.setOrigRespositoryId("999999");

            docs[0] = docInfo;
            session.setAvailableDocuments(docs);

    }


}
