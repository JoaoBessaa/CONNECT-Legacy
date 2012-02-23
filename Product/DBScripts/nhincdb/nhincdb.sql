-- create application user
CREATE USER nhincuser IDENTIFIED BY 'nhincpass';

-- begin aggregator creation
CREATE DATABASE aggregator;

CREATE TABLE aggregator.agg_transaction (
	TransactionId VARCHAR(32) NOT NULL COMMENT 'This will be a UUID',
	ServiceType VARCHAR(64) NOT NULL,
	TransactionStartTime DATETIME COMMENT 'Format of YYYYMMDDHHMMSS',
  PRIMARY KEY(TransactionId)
);

CREATE TABLE aggregator.agg_message_results (
	MessageId VARCHAR(32) NOT NULL COMMENT 'This will be a UUID.',
	TransactionId VARCHAR(32) NOT NULL COMMENT 'This will be a UUID. - Foreign Key to the agg_transaction table.',
	MessageKey VARCHAR(1000) NOT NULL COMMENT 'This is the key used to tie the response to the original request.',
	MessageOutTime DATETIME COMMENT 'This is the date/time when the outbound request was recorded.  Format of YYYYMMDDHHMMSS',
	ResponseReceivedTime DATETIME COMMENT 'This is the date/time when the response was recorded.  Format of YYYYMMDDHHMMSS',
	ResponseMessageType VARCHAR(100) COMMENT 'This is the name of the outer layer JAXB class for the response message.',
	ResponseMessage LONGTEXT COMMENT 'The response message in XML - Based on marshalling using JAXB',
  PRIMARY KEY (MessageId)
);

GRANT SELECT,INSERT,UPDATE,DELETE ON aggregator.* to nhincuser;
-- end aggregator creation

-- begin assigning authority
CREATE DATABASE assigningauthoritydb;

CREATE TABLE assigningauthoritydb.aa_to_home_community_mapping (
  id int(10) unsigned NOT NULL auto_increment,
  assigningauthorityid varchar(45) NOT NULL,
  homecommunityid varchar(45) NOT NULL,
  PRIMARY KEY  (id,assigningauthorityid)
);

GRANT SELECT,INSERT,UPDATE,DELETE ON assigningauthoritydb.* to nhincuser;
-- end assigning authority

-- begin auditrepo
CREATE DATABASE auditrepo;

CREATE TABLE auditrepo.auditrepository
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	audit_timestamp DATETIME,
	eventId BIGINT NOT NULL,
	userId VARCHAR(100),
	participationTypeCode SMALLINT,
	participationTypeCodeRole SMALLINT,
	participationIDTypeCode VARCHAR(100),
	receiverPatientId VARCHAR(100),
	senderPatientId VARCHAR(100),
	communityId VARCHAR(255),
	messageType VARCHAR(100) NOT NULL,
	message LONGBLOB,
	PRIMARY KEY (id),
	UNIQUE UQ_eventlog_id(id)
);

GRANT SELECT,INSERT,UPDATE,DELETE ON auditrepo.* to nhincuser;
-- end auditrepo

-- begin docrepository
CREATE DATABASE docrepository;

CREATE TABLE docrepository.document (
  documentid int(11) NOT NULL,
  DocumentUniqueId varchar(64) NOT NULL,
  DocumentTitle varchar(128) default NULL,
  authorPerson varchar(64) default NULL,
  authorInstitution varchar(64) default NULL,
  authorRole varchar(64) default NULL,
  authorSpecialty varchar(64) default NULL,
  AvailabilityStatus varchar(64) default NULL,
  ClassCode varchar(64) default NULL,
  ClassCodeScheme varchar(64) default NULL,
  ClassCodeDisplayName varchar(64) default NULL,
  ConfidentialityCode varchar(64) default NULL,
  ConfidentialityCodeScheme varchar(64) default NULL,
  ConfidentialityCodeDisplayName varchar(64) default NULL,
  CreationTime datetime default NULL COMMENT 'Date format expected: MM/dd/yyyy.HH:mm:ss',
  FormatCode varchar(64) default NULL,
  FormatCodeScheme varchar(64) default NULL,
  FormatCodeDisplayName varchar(64) default NULL,
  PatientId varchar(64) default NULL COMMENT 'Format of HL7 2.x CX',
  ServiceStartTime datetime default NULL COMMENT 'Format of YYYYMMDDHHMMSS',
  ServiceStopTime datetime default NULL COMMENT 'Format of YYYYMMDDHHMMSS',
  Status varchar(64) default NULL,
  Comments varchar(256) default NULL,
  Hash varchar(1028) default NULL COMMENT 'Might be better to derive',
  FacilityCode varchar(64) default NULL,
  FacilityCodeScheme varchar(64) default NULL,
  FacilityCodeDisplayName varchar(64) default NULL,
  IntendedRecipientPerson varchar(128) default NULL COMMENT 'Format of HL7 2.x XCN',
  IntendedRecipientOrganization varchar(128) default NULL COMMENT 'Format of HL7 2.x XON',
  LanguageCode varchar(64) default NULL,
  LegalAuthenticator varchar(128) default NULL COMMENT 'Format of HL7 2.x XCN',
  MimeType varchar(32) default NULL,
  ParentDocumentId varchar(64) default NULL,
  ParentDocumentRelationship varchar(64) default NULL,
  PracticeSetting varchar(64) default NULL,
  PracticeSettingScheme varchar(64) default NULL,
  PracticeSettingDisplayName varchar(64) default NULL,
  DocumentSize int(11) default NULL,
  SourcePatientId varchar(128) default NULL COMMENT 'Format of HL7 2.x CX',
  Pid3 varchar(128) default NULL,
  Pid5 varchar(128) default NULL,
  Pid7 varchar(128) default NULL,
  Pid8 varchar(128) default NULL,
  Pid11 varchar(128) default NULL,
  TypeCode varchar(64) default NULL,
  TypeCodeScheme varchar(64) default NULL,
  TypeCodeDisplayName varchar(64) default NULL,
  DocumentUri varchar(128) default NULL COMMENT 'May derive this value',
  RawData longblob,
  Persistent int(11) NOT NULL,
  PRIMARY KEY  (documentid)
);

CREATE TABLE docrepository.eventcode (
  eventcodeid int(11) NOT NULL,
  documentid int(11) NOT NULL COMMENT 'Foriegn key to document table',
  EventCode varchar(64) default NULL,
  EventCodeScheme varchar(64) default NULL,
  EventCodeDisplayName varchar(64) default NULL,
  PRIMARY KEY  (eventcodeid)
);

GRANT SELECT,INSERT,UPDATE,DELETE ON docrepository.* to nhincuser;
-- end docrepository

-- begin patientcorrelationdb
CREATE DATABASE patientcorrelationdb;

CREATE TABLE patientcorrelationdb.correlatedidentifiers (
  correlationId int(10) unsigned NOT NULL auto_increment,
  PatientAssigningAuthorityId varchar(45) NOT NULL,
  PatientId varchar(45) NOT NULL,
  CorrelatedPatientAssignAuthId varchar(45) NOT NULL,
  CorrelatedPatientId varchar(45) NOT NULL,
  CorrelationExpirationDate datetime,
  PRIMARY KEY  (correlationId)
);

GRANT SELECT,INSERT,UPDATE,DELETE ON patientcorrelationdb.* to nhincuser;
-- end patientcorrelationdb

-- begin subscription repository creation
CREATE DATABASE subscriptionrepository;

CREATE TABLE subscriptionrepository.subscription (
	id VARCHAR(128) NOT NULL COMMENT 'Database generated UUID',
	Subscriptionid VARCHAR(128) COMMENT 'Unique identifier for a CONNECT generated subscription',
	SubscribeXML LONGTEXT COMMENT 'Full subscribe message as an XML string',
	SubscriptionReferenceXML LONGTEXT COMMENT 'Full subscription reference as an XML string',
	RootTopic LONGTEXT COMMENT 'Root topic of the subscription record',
	ParentSubscriptionId VARCHAR(128) COMMENT 'Subscription id for a parent record provided for fast searching',
	ParentSubscriptionReferenceXML LONGTEXT COMMENT 'Full subscription reference for a parent record as an XML string',
	Consumer VARCHAR(128) COMMENT 'Notification consumer system',
	Producer VARCHAR(128) COMMENT 'Notification producer system',
	PatientId VARCHAR(128) COMMENT 'Local system patient identifier',
	PatientAssigningAuthority VARCHAR(128) COMMENT 'Assigning authority for the local patient identifier',
	Targets LONGTEXT COMMENT 'Full target system as an XML string',
	CreationDate DATETIME COMMENT 'Format of YYYYMMDDHHMMSS',
  PRIMARY KEY(id)
);

GRANT SELECT,INSERT,UPDATE,DELETE ON subscriptionrepository.* to nhincuser;
-- end subscription repository creation
