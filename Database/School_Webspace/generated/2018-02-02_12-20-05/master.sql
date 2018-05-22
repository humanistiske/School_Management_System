SET ECHO ON
SET VERIFY ON
SET FEEDBACK OFF
SET DEFINE ON
CLEAR SCREEN
set serveroutput on

COLUMN date_time NEW_VAL filename noprint;
SELECT to_char(systimestamp,'yyyy-mm-dd_hh24-mi-ssxff') date_time FROM DUAL;
spool "School_Webspace_&filename..log"

-- Password file execution
@passworddefinition.sql

PROMPT Dropping Role ROLE_School_Webspace ...
DROP ROLE ROLE_School_Webspace ;
PROMPT Creating Role ROLE_School_Webspace ...
CREATE ROLE ROLE_School_Webspace ;

-- PROMPT Drop school_webspace user
-- drop user school_webspace cascade;
   
PROMPT Create user school_webspace
CREATE USER school_webspace identified by &&school_webspace_password DEFAULT TABLESPACE USERS TEMPORARY TABLESPACE TEMP;
GRANT CREATE SESSION, RESOURCE, CREATE VIEW, CREATE MATERIALIZED VIEW, CREATE SYNONYM TO school_webspace;

-- PROMPT Drop Emulation user
-- drop user Emulation cascade;
   
PROMPT Create user Emulation
CREATE USER Emulation identified by &&Emulation_password DEFAULT TABLESPACE USERS TEMPORARY TABLESPACE TEMP;
GRANT CREATE SESSION, RESOURCE, CREATE VIEW, CREATE MATERIALIZED VIEW, CREATE SYNONYM TO Emulation;

set define on
prompt connecting to Emulation
connect Emulation/&&Emulation_password;
set define off

set define on
prompt connecting to school_webspace
connect school_webspace/&&school_webspace_password;
set define off

-- DROP SEQUENCE class_classroomid_SEQ;


PROMPT Creating Sequence class_classroomid_SEQ ...
CREATE SEQUENCE  class_classroomid_SEQ  
  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1  NOCYCLE ;

-- DROP SEQUENCE classroom_classroomid_SEQ;


PROMPT Creating Sequence classroom_classroomid_SEQ ...
CREATE SEQUENCE  classroom_classroomid_SEQ  
  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1  NOCYCLE ;

-- DROP SEQUENCE exam_examid_SEQ;


PROMPT Creating Sequence exam_examid_SEQ ...
CREATE SEQUENCE  exam_examid_SEQ  
  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1  NOCYCLE ;

-- DROP SEQUENCE examstatus_exam_status_id_SEQ;


PROMPT Creating Sequence examstatus_exam_status_id_SEQ ...
CREATE SEQUENCE  examstatus_exam_status_id_SEQ  
  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1  NOCYCLE ;

-- DROP SEQUENCE newseventsdb_newseventid_SEQ;


PROMPT Creating Sequence newseventsdb_newseventid_SEQ ...
CREATE SEQUENCE  newseventsdb_newseventid_SEQ  
  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1  NOCYCLE ;

-- DROP SEQUENCE salarystatus_salarystatusid_SE;


PROMPT Creating Sequence salarystatus_salarystatusid_SE ...
CREATE SEQUENCE  salarystatus_salarystatusid_SE  
  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1  NOCYCLE ;

-- DROP SEQUENCE studentsubjectreport_subject_r;


PROMPT Creating Sequence studentsubjectreport_subject_r ...
CREATE SEQUENCE  studentsubjectreport_subject_r  
  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1  NOCYCLE ;

-- DROP SEQUENCE subject_subjectid_SEQ;


PROMPT Creating Sequence subject_subjectid_SEQ ...
CREATE SEQUENCE  subject_subjectid_SEQ  
  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1  NOCYCLE ;

-- DROP SEQUENCE userdb_userid_SEQ;


PROMPT Creating Sequence userdb_userid_SEQ ...
CREATE SEQUENCE  userdb_userid_SEQ  
  MINVALUE 1 MAXVALUE 999999999999999999999999 INCREMENT BY 1  NOCYCLE ;

-- DROP TABLE class CASCADE CONSTRAINTS;


PROMPT Creating Table class ...
CREATE TABLE class (
  classid VARCHAR2(3 CHAR) NOT NULL,
  class_capacity NUMBER(10,0) NOT NULL,
  class_size NUMBER(10,0) NOT NULL,
  classroomid NUMBER(10,0) NOT NULL
);


PROMPT Creating Primary Key Constraint PRIMARY on table class ... 
ALTER TABLE class
ADD CONSTRAINT PRIMARY PRIMARY KEY
(
  classid
)
ENABLE
;

GRANT ALL ON class TO ROLE_School_Webspace;
-- DROP TABLE classattendancedb CASCADE CONSTRAINTS;


PROMPT Creating Table classattendancedb ...
CREATE TABLE classattendancedb (
  userid NUMBER(10,0) NOT NULL,
  classid VARCHAR2(3 CHAR) NOT NULL,
  subjectid NUMBER(10,0) NOT NULL,
  date_ DATE NOT NULL,
  attendance_status VARCHAR2(10 CHAR) NOT NULL
);


COMMENT ON COLUMN classattendancedb.date_ IS 'ORIGINAL NAME:date'
;

PROMPT Creating Primary Key Constraint PRIMARY_23 on table classattendancedb ... 
ALTER TABLE classattendancedb
ADD CONSTRAINT PRIMARY_23 PRIMARY KEY
(
  classid,
  subjectid,
  userid,
  date_
)
ENABLE
;

GRANT ALL ON classattendancedb TO ROLE_School_Webspace;
-- DROP TABLE classroom CASCADE CONSTRAINTS;


PROMPT Creating Table classroom ...
CREATE TABLE classroom (
  classroomid NUMBER(10,0) NOT NULL,
  capacity NUMBER(10,0) NOT NULL
);


PROMPT Creating Primary Key Constraint PRIMARY_2 on table classroom ... 
ALTER TABLE classroom
ADD CONSTRAINT PRIMARY_2 PRIMARY KEY
(
  classroomid
)
ENABLE
;

GRANT ALL ON classroom TO ROLE_School_Webspace;
-- DROP TABLE classsubjectdist CASCADE CONSTRAINTS;


PROMPT Creating Table classsubjectdist ...
CREATE TABLE classsubjectdist (
  classid VARCHAR2(3 CHAR) NOT NULL,
  subjectid NUMBER(10,0) NOT NULL
);


PROMPT Creating Primary Key Constraint PRIMARY_3 on table classsubjectdist ... 
ALTER TABLE classsubjectdist
ADD CONSTRAINT PRIMARY_3 PRIMARY KEY
(
  classid,
  subjectid
)
ENABLE
;

GRANT ALL ON classsubjectdist TO ROLE_School_Webspace;
-- DROP TABLE classtimetable CASCADE CONSTRAINTS;


PROMPT Creating Table classtimetable ...
CREATE TABLE classtimetable (
  classid VARCHAR2(3 CHAR) NOT NULL,
  subjectid NUMBER(10,0) NOT NULL,
  start_time INTERVAL DAY TO SECOND NOT NULL,
  end_time INTERVAL DAY TO SECOND NOT NULL
);


PROMPT Creating Primary Key Constraint PRIMARY_4 on table classtimetable ... 
ALTER TABLE classtimetable
ADD CONSTRAINT PRIMARY_4 PRIMARY KEY
(
  classid,
  subjectid
)
ENABLE
;

GRANT ALL ON classtimetable TO ROLE_School_Webspace;
-- DROP TABLE exam CASCADE CONSTRAINTS;


PROMPT Creating Table exam ...
CREATE TABLE exam (
  examid NUMBER(10,0) NOT NULL,
  classid VARCHAR2(3 CHAR) NOT NULL,
  subjectid NUMBER(10,0) NOT NULL,
  exam_location NUMBER(10,0) NOT NULL,
  date_time DATE NOT NULL,
  duration FLOAT NOT NULL,
  marks NUMBER(10,0) NOT NULL,
  exam_status_id NUMBER(10,0) NOT NULL
);


PROMPT Creating Primary Key Constraint PRIMARY_5 on table exam ... 
ALTER TABLE exam
ADD CONSTRAINT PRIMARY_5 PRIMARY KEY
(
  examid
)
ENABLE
;

GRANT ALL ON exam TO ROLE_School_Webspace;
-- DROP TABLE examenrolldb CASCADE CONSTRAINTS;


PROMPT Creating Table examenrolldb ...
CREATE TABLE examenrolldb (
  userid NUMBER(10,0) NOT NULL,
  examid NUMBER(10,0) NOT NULL,
  exam_status_id NUMBER(10,0) NOT NULL
);


PROMPT Creating Primary Key Constraint PRIMARY_6 on table examenrolldb ... 
ALTER TABLE examenrolldb
ADD CONSTRAINT PRIMARY_6 PRIMARY KEY
(
  examid,
  userid
)
ENABLE
;

GRANT ALL ON examenrolldb TO ROLE_School_Webspace;
-- DROP TABLE examhallticketdb CASCADE CONSTRAINTS;


PROMPT Creating Table examhallticketdb ...
CREATE TABLE examhallticketdb (
  userid NUMBER(10,0) NOT NULL,
  examid NUMBER(10,0) NOT NULL,
  exam_hallticket BLOB NOT NULL
);


PROMPT Creating Primary Key Constraint PRIMARY_7 on table examhallticketdb ... 
ALTER TABLE examhallticketdb
ADD CONSTRAINT PRIMARY_7 PRIMARY KEY
(
  examid,
  userid
)
ENABLE
;

GRANT ALL ON examhallticketdb TO ROLE_School_Webspace;
-- DROP TABLE examresultdb CASCADE CONSTRAINTS;


PROMPT Creating Table examresultdb ...
CREATE TABLE examresultdb (
  userid NUMBER(10,0) NOT NULL,
  examid NUMBER(10,0) NOT NULL,
  student_marks NUMBER(10,0) NOT NULL,
  grade VARCHAR2(1 CHAR) NOT NULL,
  answer_sheet BLOB NOT NULL
);


PROMPT Creating Primary Key Constraint PRIMARY_8 on table examresultdb ... 
ALTER TABLE examresultdb
ADD CONSTRAINT PRIMARY_8 PRIMARY KEY
(
  examid,
  userid
)
ENABLE
;

GRANT ALL ON examresultdb TO ROLE_School_Webspace;
-- DROP TABLE examstatus CASCADE CONSTRAINTS;


PROMPT Creating Table examstatus ...
CREATE TABLE examstatus (
  exam_status_id NUMBER(10,0) NOT NULL,
  exam_status_desc VARCHAR2(45 CHAR) NOT NULL
);


PROMPT Creating Primary Key Constraint PRIMARY_9 on table examstatus ... 
ALTER TABLE examstatus
ADD CONSTRAINT PRIMARY_9 PRIMARY KEY
(
  exam_status_id
)
ENABLE
;

GRANT ALL ON examstatus TO ROLE_School_Webspace;
-- DROP TABLE logindb CASCADE CONSTRAINTS;


PROMPT Creating Table logindb ...
CREATE TABLE logindb (
  userid NUMBER(10,0) NOT NULL,
  usertypeid VARCHAR2(3 CHAR) NOT NULL,
  password VARCHAR2(20 CHAR) NOT NULL
);


PROMPT Creating Primary Key Constraint PRIMARY_10 on table logindb ... 
ALTER TABLE logindb
ADD CONSTRAINT PRIMARY_10 PRIMARY KEY
(
  userid
)
ENABLE
;

GRANT ALL ON logindb TO ROLE_School_Webspace;
-- DROP TABLE newseventsdb CASCADE CONSTRAINTS;


PROMPT Creating Table newseventsdb ...
CREATE TABLE newseventsdb (
  userid NUMBER(10,0) NOT NULL,
  newseventid NUMBER(10,0) NOT NULL,
  title VARCHAR2(130 CHAR) NOT NULL,
  post BLOB NOT NULL
);


PROMPT Creating Primary Key Constraint PRIMARY_11 on table newseventsdb ... 
ALTER TABLE newseventsdb
ADD CONSTRAINT PRIMARY_11 PRIMARY KEY
(
  newseventid
)
ENABLE
;

GRANT ALL ON newseventsdb TO ROLE_School_Webspace;
-- DROP TABLE salarystatus CASCADE CONSTRAINTS;


PROMPT Creating Table salarystatus ...
CREATE TABLE salarystatus (
  salarystatusid NUMBER(10,0) NOT NULL,
  salary_status_desc VARCHAR2(20 CHAR) NOT NULL
);


PROMPT Creating Primary Key Constraint PRIMARY_12 on table salarystatus ... 
ALTER TABLE salarystatus
ADD CONSTRAINT PRIMARY_12 PRIMARY KEY
(
  salarystatusid
)
ENABLE
;

GRANT ALL ON salarystatus TO ROLE_School_Webspace;
-- DROP TABLE staffaccountsdb CASCADE CONSTRAINTS;


PROMPT Creating Table staffaccountsdb ...
CREATE TABLE staffaccountsdb (
  userid NUMBER(10,0) NOT NULL,
  in_hand_salary NUMBER(10,0) NOT NULL,
  allowance NUMBER(10,0) NOT NULL,
  total_salary NUMBER(10,0) NOT NULL,
  last_salary_payment_date DATE NOT NULL,
  salarystatusid NUMBER(10,0) NOT NULL,
  date_of_joining DATE NOT NULL,
  contract_end_date DATE NOT NULL
);


PROMPT Creating Primary Key Constraint PRIMARY_13 on table staffaccountsdb ... 
ALTER TABLE staffaccountsdb
ADD CONSTRAINT PRIMARY_13 PRIMARY KEY
(
  userid
)
ENABLE
;

GRANT ALL ON staffaccountsdb TO ROLE_School_Webspace;
-- DROP TABLE staffinfodb CASCADE CONSTRAINTS;


PROMPT Creating Table staffinfodb ...
CREATE TABLE staffinfodb (
  userid NUMBER(10,0) NOT NULL,
  usertypeid VARCHAR2(3 CHAR) NOT NULL,
  designation VARCHAR2(45 CHAR) NOT NULL,
  highest_qualification VARCHAR2(45 CHAR) NOT NULL,
  qualification_documents BLOB NOT NULL
);


PROMPT Creating Primary Key Constraint PRIMARY_14 on table staffinfodb ... 
ALTER TABLE staffinfodb
ADD CONSTRAINT PRIMARY_14 PRIMARY KEY
(
  userid
)
ENABLE
;

GRANT ALL ON staffinfodb TO ROLE_School_Webspace;
-- DROP TABLE studentaccountsdb CASCADE CONSTRAINTS;


PROMPT Creating Table studentaccountsdb ...
CREATE TABLE studentaccountsdb (
  userid NUMBER(10,0) NOT NULL,
  classid VARCHAR2(3 CHAR) NOT NULL,
  paid_fees NUMBER(10,0) NOT NULL,
  outstanding_fees NUMBER(10,0) NOT NULL,
  last_payment_date DATE NOT NULL,
  due_date DATE
);


PROMPT Creating Primary Key Constraint PRIMARY_15 on table studentaccountsdb ... 
ALTER TABLE studentaccountsdb
ADD CONSTRAINT PRIMARY_15 PRIMARY KEY
(
  classid,
  userid
)
ENABLE
;

GRANT ALL ON studentaccountsdb TO ROLE_School_Webspace;
-- DROP TABLE studentinfodb CASCADE CONSTRAINTS;


PROMPT Creating Table studentinfodb ...
CREATE TABLE studentinfodb (
  userid NUMBER(10,0) NOT NULL,
  classid VARCHAR2(3 CHAR) NOT NULL,
  total_subjects NUMBER(10,0) NOT NULL
);


PROMPT Creating Primary Key Constraint PRIMARY_16 on table studentinfodb ... 
ALTER TABLE studentinfodb
ADD CONSTRAINT PRIMARY_16 PRIMARY KEY
(
  classid,
  userid
)
ENABLE
;

GRANT ALL ON studentinfodb TO ROLE_School_Webspace;
-- DROP TABLE studentsubjectdist CASCADE CONSTRAINTS;


PROMPT Creating Table studentsubjectdist ...
CREATE TABLE studentsubjectdist (
  userid NUMBER(10,0) NOT NULL,
  subjectid NUMBER(10,0) NOT NULL,
  classid VARCHAR2(3 CHAR) NOT NULL,
  subject_report NUMBER(10,0) NOT NULL
);


PROMPT Creating Primary Key Constraint PRIMARY_17 on table studentsubjectdist ... 
ALTER TABLE studentsubjectdist
ADD CONSTRAINT PRIMARY_17 PRIMARY KEY
(
  classid,
  subjectid,
  userid
)
ENABLE
;

GRANT ALL ON studentsubjectdist TO ROLE_School_Webspace;
-- DROP TABLE studentsubjectreport CASCADE CONSTRAINTS;


PROMPT Creating Table studentsubjectreport ...
CREATE TABLE studentsubjectreport (
  subject_report NUMBER(10,0) NOT NULL,
  subject_report_desc VARCHAR2(20 CHAR) NOT NULL
);


PROMPT Creating Primary Key Constraint PRIMARY_18 on table studentsubjectreport ... 
ALTER TABLE studentsubjectreport
ADD CONSTRAINT PRIMARY_18 PRIMARY KEY
(
  subject_report
)
ENABLE
;

GRANT ALL ON studentsubjectreport TO ROLE_School_Webspace;
-- DROP TABLE studymaterial CASCADE CONSTRAINTS;


PROMPT Creating Table studymaterial ...
CREATE TABLE studymaterial (
  userid NUMBER(10,0) NOT NULL,
  filename VARCHAR2(100 CHAR) NOT NULL,
  file_ BLOB NOT NULL,
  size_ FLOAT NOT NULL,
  upload_date_time DATE NOT NULL,
  total_downloads NUMBER(10,0) NOT NULL
);


COMMENT ON COLUMN studymaterial.file_ IS 'ORIGINAL NAME:file'
;

COMMENT ON COLUMN studymaterial.size_ IS 'ORIGINAL NAME:size'
;

PROMPT Creating Primary Key Constraint PRIMARY_19 on table studymaterial ... 
ALTER TABLE studymaterial
ADD CONSTRAINT PRIMARY_19 PRIMARY KEY
(
  userid,
  filename,
  upload_date_time
)
ENABLE
;

GRANT ALL ON studymaterial TO ROLE_School_Webspace;
-- DROP TABLE subject CASCADE CONSTRAINTS;


PROMPT Creating Table subject ...
CREATE TABLE subject (
  subjectid NUMBER(10,0) NOT NULL,
  subject_name VARCHAR2(50 CHAR) NOT NULL
);


PROMPT Creating Primary Key Constraint PRIMARY_20 on table subject ... 
ALTER TABLE subject
ADD CONSTRAINT PRIMARY_20 PRIMARY KEY
(
  subjectid
)
ENABLE
;

GRANT ALL ON subject TO ROLE_School_Webspace;
-- DROP TABLE teachersubjectdist CASCADE CONSTRAINTS;


PROMPT Creating Table teachersubjectdist ...
CREATE TABLE teachersubjectdist (
  userid NUMBER(10,0) NOT NULL,
  classid VARCHAR2(3 CHAR) NOT NULL,
  subjectid NUMBER(10,0) NOT NULL
);


PROMPT Creating Primary Key Constraint PRIMARY_21 on table teachersubjectdist ... 
ALTER TABLE teachersubjectdist
ADD CONSTRAINT PRIMARY_21 PRIMARY KEY
(
  classid,
  subjectid
)
ENABLE
;

GRANT ALL ON teachersubjectdist TO ROLE_School_Webspace;
-- DROP TABLE userdb CASCADE CONSTRAINTS;


PROMPT Creating Table userdb ...
CREATE TABLE userdb (
  userid NUMBER(10,0) NOT NULL,
  fname VARCHAR2(45 CHAR) NOT NULL,
  lname VARCHAR2(45 CHAR) NOT NULL,
  address VARCHAR2(140 CHAR) NOT NULL,
  phone NUMBER(10,0) NOT NULL,
  alt_phone NUMBER(10,0) NOT NULL,
  email_id VARCHAR2(45 CHAR) NOT NULL,
  photo BLOB NOT NULL,
  id_proof BLOB NOT NULL
);


PROMPT Creating Primary Key Constraint PRIMARY_22 on table userdb ... 
ALTER TABLE userdb
ADD CONSTRAINT PRIMARY_22 PRIMARY KEY
(
  userid
)
ENABLE
;

GRANT ALL ON userdb TO ROLE_School_Webspace;
-- DROP TABLE usertype CASCADE CONSTRAINTS;


PROMPT Creating Table usertype ...
CREATE TABLE usertype (
  usertypeid VARCHAR2(3 CHAR) NOT NULL,
  usertype_name VARCHAR2(50 CHAR) NOT NULL
);


PROMPT Creating Primary Key Constraint PRIMARY_1 on table usertype ... 
ALTER TABLE usertype
ADD CONSTRAINT PRIMARY_1 PRIMARY KEY
(
  usertypeid
)
ENABLE
;

GRANT ALL ON usertype TO ROLE_School_Webspace;
PROMPT Creating Index classattendancedb_ibfk_3 on classattendancedb ...
CREATE INDEX classattendancedb_ibfk_3 ON classattendancedb
(
  subjectid
) 
;
PROMPT Creating Index fk_attendance_classStudentSubj on classattendancedb ...
CREATE INDEX fk_attendance_classStudentSubj ON classattendancedb
(
  userid,
  classid,
  subjectid
) 
;
PROMPT Creating Index classsubjectdist_ibfk_1 on classsubjectdist ...
CREATE INDEX classsubjectdist_ibfk_1 ON classsubjectdist
(
  subjectid
) 
;
PROMPT Creating Index exam_ibfk_2 on exam ...
CREATE INDEX exam_ibfk_2 ON exam
(
  subjectid
) 
;
PROMPT Creating Index exam_ibfk_3 on exam ...
CREATE INDEX exam_ibfk_3 ON exam
(
  exam_location
) 
;
PROMPT Creating Index fk_examResultClassSubjectID on exam ...
CREATE INDEX fk_examResultClassSubjectID ON exam
(
  classid,
  subjectid
) 
;
PROMPT Creating Index exam_ibfk_4 on exam ...
CREATE INDEX exam_ibfk_4 ON exam
(
  exam_status_id
) 
;
PROMPT Creating Index examenrolldb_ibfk_1 on examenrolldb ...
CREATE INDEX examenrolldb_ibfk_1 ON examenrolldb
(
  userid
) 
;
PROMPT Creating Index examenrolldb_ibfk_3 on examenrolldb ...
CREATE INDEX examenrolldb_ibfk_3 ON examenrolldb
(
  exam_status_id
) 
;
PROMPT Creating Index logindb_ibfk_2 on logindb ...
CREATE INDEX logindb_ibfk_2 ON logindb
(
  usertypeid
) 
;
PROMPT Creating Index newseventsdb_ibfk_1 on newseventsdb ...
CREATE INDEX newseventsdb_ibfk_1 ON newseventsdb
(
  userid
) 
;
PROMPT Creating Index staffaccountsdb_ibfk_2 on staffaccountsdb ...
CREATE INDEX staffaccountsdb_ibfk_2 ON staffaccountsdb
(
  salarystatusid
) 
;
PROMPT Creating Index staffinfodb_ibfk_2 on staffinfodb ...
CREATE INDEX staffinfodb_ibfk_2 ON staffinfodb
(
  usertypeid
) 
;
PROMPT Creating Index studentinfodb_ibfk_1 on studentinfodb ...
CREATE INDEX studentinfodb_ibfk_1 ON studentinfodb
(
  userid
) 
;
PROMPT Creating Index studentinfodb_ibfk_2 on studentinfodb ...
CREATE INDEX studentinfodb_ibfk_2 ON studentinfodb
(
  classid
) 
;
PROMPT Creating Index studentsubjectdist_ibfk_3 on studentsubjectdist ...
CREATE INDEX studentsubjectdist_ibfk_3 ON studentsubjectdist
(
  subject_report
) 
;
PROMPT Creating Index studentsubjectdist_ibfk_1 on studentsubjectdist ...
CREATE INDEX studentsubjectdist_ibfk_1 ON studentsubjectdist
(
  userid,
  classid
) 
;
PROMPT Creating Index studentsubjectdist_ibfk_2 on studentsubjectdist ...
CREATE INDEX studentsubjectdist_ibfk_2 ON studentsubjectdist
(
  subjectid
) 
;
PROMPT Creating Index teachersubjectdist_ibfk_2 on teachersubjectdist ...
CREATE INDEX teachersubjectdist_ibfk_2 ON teachersubjectdist
(
  subjectid
) 
;
PROMPT Creating Index fk_teacherClassSubjectID on teachersubjectdist ...
CREATE INDEX fk_teacherClassSubjectID ON teachersubjectdist
(
  classid,
  subjectid
) 
;
PROMPT Creating Index teachersubjectdist_ibfk_3 on teachersubjectdist ...
CREATE INDEX teachersubjectdist_ibfk_3 ON teachersubjectdist
(
  userid
) 
;
set define on
prompt connecting to school_webspace
connect school_webspace/&&school_webspace_password;
set define off

PROMPT Creating Foreign Key Constraint class_ibfk_1 on table classroom...
ALTER TABLE class
ADD CONSTRAINT class_ibfk_1 FOREIGN KEY
(
  classroomid
)
REFERENCES classroom
(
  classroomid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint classsubjectdist_ibfk_1 on table subject...
ALTER TABLE classsubjectdist
ADD CONSTRAINT classsubjectdist_ibfk_1 FOREIGN KEY
(
  subjectid
)
REFERENCES subject
(
  subjectid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint classsubjectdist_ibfk_2 on table class...
ALTER TABLE classsubjectdist
ADD CONSTRAINT classsubjectdist_ibfk_2 FOREIGN KEY
(
  classid
)
REFERENCES class
(
  classid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint classtimetable_ibfk_1 on table classsubjectdist...
ALTER TABLE classtimetable
ADD CONSTRAINT classtimetable_ibfk_1 FOREIGN KEY
(
  classid,
  subjectid
)
REFERENCES classsubjectdist
(
  classid,
  subjectid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint exam_ibfk_3 on table classroom...
ALTER TABLE exam
ADD CONSTRAINT exam_ibfk_3 FOREIGN KEY
(
  exam_location
)
REFERENCES classroom
(
  classroomid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint exam_ibfk_4 on table examstatus...
ALTER TABLE exam
ADD CONSTRAINT exam_ibfk_4 FOREIGN KEY
(
  exam_status_id
)
REFERENCES examstatus
(
  exam_status_id
)
ENABLE
;

PROMPT Creating Foreign Key Constraint fk_examClassSubjectID on table classsubjectdist...
ALTER TABLE exam
ADD CONSTRAINT fk_examClassSubjectID FOREIGN KEY
(
  classid,
  subjectid
)
REFERENCES classsubjectdist
(
  classid,
  subjectid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint examenrolldb_ibfk_2 on table exam...
ALTER TABLE examenrolldb
ADD CONSTRAINT examenrolldb_ibfk_2 FOREIGN KEY
(
  examid
)
REFERENCES exam
(
  examid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint examenrolldb_ibfk_3 on table examstatus...
ALTER TABLE examenrolldb
ADD CONSTRAINT examenrolldb_ibfk_3 FOREIGN KEY
(
  exam_status_id
)
REFERENCES examstatus
(
  exam_status_id
)
ENABLE
;

PROMPT Creating Foreign Key Constraint examhallticketdb_ibfk_1 on table examenrolldb...
ALTER TABLE examhallticketdb
ADD CONSTRAINT examhallticketdb_ibfk_1 FOREIGN KEY
(
  examid,
  userid
)
REFERENCES examenrolldb
(
  examid,
  userid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint examenrolldb_ibfk_1 on table examenrolldb...
ALTER TABLE examresultdb
ADD CONSTRAINT examenrolldb_ibfk_1 FOREIGN KEY
(
  examid,
  userid
)
REFERENCES examenrolldb
(
  examid,
  userid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint logindb_ibfk_1 on table userdb...
ALTER TABLE logindb
ADD CONSTRAINT logindb_ibfk_1 FOREIGN KEY
(
  userid
)
REFERENCES userdb
(
  userid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint logindb_ibfk_2 on table usertype...
ALTER TABLE logindb
ADD CONSTRAINT logindb_ibfk_2 FOREIGN KEY
(
  usertypeid
)
REFERENCES usertype
(
  usertypeid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint newseventsdb_ibfk_1 on table staffinfodb...
ALTER TABLE newseventsdb
ADD CONSTRAINT newseventsdb_ibfk_1 FOREIGN KEY
(
  userid
)
REFERENCES staffinfodb
(
  userid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint staffaccountsdb_ibfk_1 on table staffinfodb...
ALTER TABLE staffaccountsdb
ADD CONSTRAINT staffaccountsdb_ibfk_1 FOREIGN KEY
(
  userid
)
REFERENCES staffinfodb
(
  userid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint staffaccountsdb_ibfk_2 on table salarystatus...
ALTER TABLE staffaccountsdb
ADD CONSTRAINT staffaccountsdb_ibfk_2 FOREIGN KEY
(
  salarystatusid
)
REFERENCES salarystatus
(
  salarystatusid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint staffinfodb_ibfk_1 on table userdb...
ALTER TABLE staffinfodb
ADD CONSTRAINT staffinfodb_ibfk_1 FOREIGN KEY
(
  userid
)
REFERENCES userdb
(
  userid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint staffinfodb_ibfk_2 on table usertype...
ALTER TABLE staffinfodb
ADD CONSTRAINT staffinfodb_ibfk_2 FOREIGN KEY
(
  usertypeid
)
REFERENCES usertype
(
  usertypeid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint studentaccountsdb_ibfk_1 on table studentinfodb...
ALTER TABLE studentaccountsdb
ADD CONSTRAINT studentaccountsdb_ibfk_1 FOREIGN KEY
(
  classid,
  userid
)
REFERENCES studentinfodb
(
  classid,
  userid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint studentinfodb_ibfk_1 on table userdb...
ALTER TABLE studentinfodb
ADD CONSTRAINT studentinfodb_ibfk_1 FOREIGN KEY
(
  userid
)
REFERENCES userdb
(
  userid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint studentinfodb_ibfk_2 on table class...
ALTER TABLE studentinfodb
ADD CONSTRAINT studentinfodb_ibfk_2 FOREIGN KEY
(
  classid
)
REFERENCES class
(
  classid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint studentsubjectdist_ibfk_1 on table studentinfodb...
ALTER TABLE studentsubjectdist
ADD CONSTRAINT studentsubjectdist_ibfk_1 FOREIGN KEY
(
  userid,
  classid
)
REFERENCES studentinfodb
(
  userid,
  classid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint studentsubjectdist_ibfk_2 on table subject...
ALTER TABLE studentsubjectdist
ADD CONSTRAINT studentsubjectdist_ibfk_2 FOREIGN KEY
(
  subjectid
)
REFERENCES subject
(
  subjectid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint studentsubjectdist_ibfk_3 on table studentsubjectreport...
ALTER TABLE studentsubjectdist
ADD CONSTRAINT studentsubjectdist_ibfk_3 FOREIGN KEY
(
  subject_report
)
REFERENCES studentsubjectreport
(
  subject_report
)
ENABLE
;

PROMPT Creating Foreign Key Constraint studymaterial_ibfk_1 on table userdb...
ALTER TABLE studymaterial
ADD CONSTRAINT studymaterial_ibfk_1 FOREIGN KEY
(
  userid
)
REFERENCES userdb
(
  userid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint fk_teacherClassSubjectID on table classsubjectdist...
ALTER TABLE teachersubjectdist
ADD CONSTRAINT fk_teacherClassSubjectID FOREIGN KEY
(
  classid,
  subjectid
)
REFERENCES classsubjectdist
(
  classid,
  subjectid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint teachersubjectdist_ibfk_3 on table staffinfodb...
ALTER TABLE teachersubjectdist
ADD CONSTRAINT teachersubjectdist_ibfk_3 FOREIGN KEY
(
  userid
)
REFERENCES staffinfodb
(
  userid
)
ENABLE
;

PROMPT Creating Foreign Key Constraint fk_attendance_classStudentSubj on table studentsubjectdist...
ALTER TABLE classattendancedb
ADD CONSTRAINT fk_attendance_classStudentSubj FOREIGN KEY
(
  userid,
  classid,
  subjectid
)
REFERENCES studentsubjectdist
(
  userid,
  classid,
  subjectid
)
ENABLE
;

CREATE OR REPLACE TRIGGER class_classroomid_TRG BEFORE INSERT ON class
FOR EACH ROW
DECLARE 
v_newVal NUMBER(12) := 0;
v_incval NUMBER(12) := 0;
BEGIN
  IF INSERTING AND :new.classroomid IS NULL THEN
    SELECT  class_classroomid_SEQ.NEXTVAL INTO v_newVal FROM DUAL;
    -- If this is the first time this table have been inserted into (sequence == 1)
    IF v_newVal = 1 THEN 
      --get the max indentity value from the table
      SELECT NVL(max(classroomid),0) INTO v_newVal FROM class;
      v_newVal := v_newVal + 1;
      --set the sequence to that value
      LOOP
           EXIT WHEN v_incval>=v_newVal;
           SELECT class_classroomid_SEQ.nextval INTO v_incval FROM dual;
      END LOOP;
    END IF;
    --used to emulate LAST_INSERT_ID()
    --mysql_utilities.identity := v_newVal; 
   -- assign the value from the sequence to emulate the identity column
   :new.classroomid := v_newVal;
  END IF;
END;

/

CREATE OR REPLACE TRIGGER classroom_classroomid_TRG BEFORE INSERT ON classroom
FOR EACH ROW
DECLARE 
v_newVal NUMBER(12) := 0;
v_incval NUMBER(12) := 0;
BEGIN
  IF INSERTING AND :new.classroomid IS NULL THEN
    SELECT  classroom_classroomid_SEQ.NEXTVAL INTO v_newVal FROM DUAL;
    -- If this is the first time this table have been inserted into (sequence == 1)
    IF v_newVal = 1 THEN 
      --get the max indentity value from the table
      SELECT NVL(max(classroomid),0) INTO v_newVal FROM classroom;
      v_newVal := v_newVal + 1;
      --set the sequence to that value
      LOOP
           EXIT WHEN v_incval>=v_newVal;
           SELECT classroom_classroomid_SEQ.nextval INTO v_incval FROM dual;
      END LOOP;
    END IF;
    --used to emulate LAST_INSERT_ID()
    --mysql_utilities.identity := v_newVal; 
   -- assign the value from the sequence to emulate the identity column
   :new.classroomid := v_newVal;
  END IF;
END;

/

CREATE OR REPLACE TRIGGER exam_examid_TRG BEFORE INSERT ON exam
FOR EACH ROW
DECLARE 
v_newVal NUMBER(12) := 0;
v_incval NUMBER(12) := 0;
BEGIN
  IF INSERTING AND :new.examid IS NULL THEN
    SELECT  exam_examid_SEQ.NEXTVAL INTO v_newVal FROM DUAL;
    -- If this is the first time this table have been inserted into (sequence == 1)
    IF v_newVal = 1 THEN 
      --get the max indentity value from the table
      SELECT NVL(max(examid),0) INTO v_newVal FROM exam;
      v_newVal := v_newVal + 1;
      --set the sequence to that value
      LOOP
           EXIT WHEN v_incval>=v_newVal;
           SELECT exam_examid_SEQ.nextval INTO v_incval FROM dual;
      END LOOP;
    END IF;
    --used to emulate LAST_INSERT_ID()
    --mysql_utilities.identity := v_newVal; 
   -- assign the value from the sequence to emulate the identity column
   :new.examid := v_newVal;
  END IF;
END;

/

CREATE OR REPLACE TRIGGER examstatus_exam_status_id_TRG BEFORE INSERT ON examstatus
FOR EACH ROW
DECLARE 
v_newVal NUMBER(12) := 0;
v_incval NUMBER(12) := 0;
BEGIN
  IF INSERTING AND :new.exam_status_id IS NULL THEN
    SELECT  examstatus_exam_status_id_SEQ.NEXTVAL INTO v_newVal FROM DUAL;
    -- If this is the first time this table have been inserted into (sequence == 1)
    IF v_newVal = 1 THEN 
      --get the max indentity value from the table
      SELECT NVL(max(exam_status_id),0) INTO v_newVal FROM examstatus;
      v_newVal := v_newVal + 1;
      --set the sequence to that value
      LOOP
           EXIT WHEN v_incval>=v_newVal;
           SELECT examstatus_exam_status_id_SEQ.nextval INTO v_incval FROM dual;
      END LOOP;
    END IF;
    --used to emulate LAST_INSERT_ID()
    --mysql_utilities.identity := v_newVal; 
   -- assign the value from the sequence to emulate the identity column
   :new.exam_status_id := v_newVal;
  END IF;
END;

/

CREATE OR REPLACE TRIGGER newseventsdb_newseventid_TRG BEFORE INSERT ON newseventsdb
FOR EACH ROW
DECLARE 
v_newVal NUMBER(12) := 0;
v_incval NUMBER(12) := 0;
BEGIN
  IF INSERTING AND :new.newseventid IS NULL THEN
    SELECT  newseventsdb_newseventid_SEQ.NEXTVAL INTO v_newVal FROM DUAL;
    -- If this is the first time this table have been inserted into (sequence == 1)
    IF v_newVal = 1 THEN 
      --get the max indentity value from the table
      SELECT NVL(max(newseventid),0) INTO v_newVal FROM newseventsdb;
      v_newVal := v_newVal + 1;
      --set the sequence to that value
      LOOP
           EXIT WHEN v_incval>=v_newVal;
           SELECT newseventsdb_newseventid_SEQ.nextval INTO v_incval FROM dual;
      END LOOP;
    END IF;
    --used to emulate LAST_INSERT_ID()
    --mysql_utilities.identity := v_newVal; 
   -- assign the value from the sequence to emulate the identity column
   :new.newseventid := v_newVal;
  END IF;
END;

/

CREATE OR REPLACE TRIGGER salarystatus_salarystatusid_TR BEFORE INSERT ON salarystatus
FOR EACH ROW
DECLARE 
v_newVal NUMBER(12) := 0;
v_incval NUMBER(12) := 0;
BEGIN
  IF INSERTING AND :new.salarystatusid IS NULL THEN
    SELECT  salarystatus_salarystatusid_SE.NEXTVAL INTO v_newVal FROM DUAL;
    -- If this is the first time this table have been inserted into (sequence == 1)
    IF v_newVal = 1 THEN 
      --get the max indentity value from the table
      SELECT NVL(max(salarystatusid),0) INTO v_newVal FROM salarystatus;
      v_newVal := v_newVal + 1;
      --set the sequence to that value
      LOOP
           EXIT WHEN v_incval>=v_newVal;
           SELECT salarystatus_salarystatusid_SE.nextval INTO v_incval FROM dual;
      END LOOP;
    END IF;
    --used to emulate LAST_INSERT_ID()
    --mysql_utilities.identity := v_newVal; 
   -- assign the value from the sequence to emulate the identity column
   :new.salarystatusid := v_newVal;
  END IF;
END;

/

CREATE OR REPLACE TRIGGER STUDENTSUBJECTREPORT_SUBJECT_1 BEFORE INSERT ON studentsubjectreport
FOR EACH ROW
DECLARE 
v_newVal NUMBER(12) := 0;
v_incval NUMBER(12) := 0;
BEGIN
  IF INSERTING AND :new.subject_report IS NULL THEN
    SELECT  studentsubjectreport_subject_r.NEXTVAL INTO v_newVal FROM DUAL;
    -- If this is the first time this table have been inserted into (sequence == 1)
    IF v_newVal = 1 THEN 
      --get the max indentity value from the table
      SELECT NVL(max(subject_report),0) INTO v_newVal FROM studentsubjectreport;
      v_newVal := v_newVal + 1;
      --set the sequence to that value
      LOOP
           EXIT WHEN v_incval>=v_newVal;
           SELECT studentsubjectreport_subject_r.nextval INTO v_incval FROM dual;
      END LOOP;
    END IF;
    --used to emulate LAST_INSERT_ID()
    --mysql_utilities.identity := v_newVal; 
   -- assign the value from the sequence to emulate the identity column
   :new.subject_report := v_newVal;
  END IF;
END;

/

CREATE OR REPLACE TRIGGER subject_subjectid_TRG BEFORE INSERT ON subject
FOR EACH ROW
DECLARE 
v_newVal NUMBER(12) := 0;
v_incval NUMBER(12) := 0;
BEGIN
  IF INSERTING AND :new.subjectid IS NULL THEN
    SELECT  subject_subjectid_SEQ.NEXTVAL INTO v_newVal FROM DUAL;
    -- If this is the first time this table have been inserted into (sequence == 1)
    IF v_newVal = 1 THEN 
      --get the max indentity value from the table
      SELECT NVL(max(subjectid),0) INTO v_newVal FROM subject;
      v_newVal := v_newVal + 1;
      --set the sequence to that value
      LOOP
           EXIT WHEN v_incval>=v_newVal;
           SELECT subject_subjectid_SEQ.nextval INTO v_incval FROM dual;
      END LOOP;
    END IF;
    --used to emulate LAST_INSERT_ID()
    --mysql_utilities.identity := v_newVal; 
   -- assign the value from the sequence to emulate the identity column
   :new.subjectid := v_newVal;
  END IF;
END;

/

CREATE OR REPLACE TRIGGER userdb_userid_TRG BEFORE INSERT ON userdb
FOR EACH ROW
DECLARE 
v_newVal NUMBER(12) := 0;
v_incval NUMBER(12) := 0;
BEGIN
  IF INSERTING AND :new.userid IS NULL THEN
    SELECT  userdb_userid_SEQ.NEXTVAL INTO v_newVal FROM DUAL;
    -- If this is the first time this table have been inserted into (sequence == 1)
    IF v_newVal = 1 THEN 
      --get the max indentity value from the table
      SELECT NVL(max(userid),0) INTO v_newVal FROM userdb;
      v_newVal := v_newVal + 1;
      --set the sequence to that value
      LOOP
           EXIT WHEN v_incval>=v_newVal;
           SELECT userdb_userid_SEQ.nextval INTO v_incval FROM dual;
      END LOOP;
    END IF;
    --used to emulate LAST_INSERT_ID()
    --mysql_utilities.identity := v_newVal; 
   -- assign the value from the sequence to emulate the identity column
   :new.userid := v_newVal;
  END IF;
END;

/

spool off;

COMMIT;

