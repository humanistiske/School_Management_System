-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 01, 2018 at 12:46 PM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `school_webspace`
--
CREATE DATABASE IF NOT EXISTS `school_webspace` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `school_webspace`;

-- --------------------------------------------------------

--
-- Table structure for table `class`
--

DROP TABLE IF EXISTS `class`;
CREATE TABLE `class` (
  `classid` varchar(3) NOT NULL,
  `class_capacity` int(11) NOT NULL,
  `class_size` int(11) NOT NULL,
  `classroomid` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `class`
--

INSERT INTO `class` (`classid`, `class_capacity`, `class_size`, `classroomid`) VALUES
('1A', 100, 40, 2);

-- --------------------------------------------------------

--
-- Table structure for table `classattendancedb`
--

DROP TABLE IF EXISTS `classattendancedb`;
CREATE TABLE `classattendancedb` (
  `userid` int(11) NOT NULL,
  `classid` varchar(3) NOT NULL,
  `subjectid` int(3) NOT NULL,
  `date` date NOT NULL,
  `attendance_status` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `classattendancedb`
--

INSERT INTO `classattendancedb` (`userid`, `classid`, `subjectid`, `date`, `attendance_status`) VALUES
(1, '1A', 1, '2018-01-21', 'PRESENT'),
(1, '1A', 1, '2018-01-22', 'PRESENT'),
(4, '1A', 1, '2018-01-01', 'PRESENT'),
(1, '1A', 2, '2018-01-31', 'PRESENT');

-- --------------------------------------------------------

--
-- Table structure for table `classroom`
--

DROP TABLE IF EXISTS `classroom`;
CREATE TABLE `classroom` (
  `classroomid` int(3) NOT NULL,
  `capacity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `classroom`
--

INSERT INTO `classroom` (`classroomid`, `capacity`) VALUES
(2, 100);

-- --------------------------------------------------------

--
-- Table structure for table `classsubjectdist`
--

DROP TABLE IF EXISTS `classsubjectdist`;
CREATE TABLE `classsubjectdist` (
  `classid` varchar(3) NOT NULL,
  `subjectid` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `classsubjectdist`
--

INSERT INTO `classsubjectdist` (`classid`, `subjectid`) VALUES
('1A', 1),
('1A', 2);

-- --------------------------------------------------------

--
-- Table structure for table `classtimetable`
--

DROP TABLE IF EXISTS `classtimetable`;
CREATE TABLE `classtimetable` (
  `classid` varchar(3) NOT NULL,
  `subjectid` int(3) NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `exam`
--

DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam` (
  `examid` int(11) NOT NULL,
  `classid` varchar(3) NOT NULL,
  `subjectid` int(3) NOT NULL,
  `exam_location` int(3) NOT NULL,
  `date_time` datetime NOT NULL,
  `duration` float NOT NULL,
  `marks` int(4) NOT NULL,
  `exam_status_id` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `exam`
--

INSERT INTO `exam` (`examid`, `classid`, `subjectid`, `exam_location`, `date_time`, `duration`, `marks`, `exam_status_id`) VALUES
(3, '1A', 1, 2, '0000-00-00 00:00:00', 2.5, 100, 2);

-- --------------------------------------------------------

--
-- Table structure for table `examenrolldb`
--

DROP TABLE IF EXISTS `examenrolldb`;
CREATE TABLE `examenrolldb` (
  `userid` int(11) NOT NULL,
  `examid` int(11) NOT NULL,
  `exam_status_id` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `examhallticketdb`
--

DROP TABLE IF EXISTS `examhallticketdb`;
CREATE TABLE `examhallticketdb` (
  `userid` int(11) NOT NULL,
  `examid` int(11) NOT NULL,
  `exam_hallticket` longblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `examresultdb`
--

DROP TABLE IF EXISTS `examresultdb`;
CREATE TABLE `examresultdb` (
  `userid` int(11) NOT NULL,
  `examid` int(11) NOT NULL,
  `student_marks` int(4) NOT NULL,
  `grade` varchar(1) NOT NULL,
  `answer_sheet` longblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `examstatus`
--

DROP TABLE IF EXISTS `examstatus`;
CREATE TABLE `examstatus` (
  `exam_status_id` int(1) NOT NULL,
  `exam_status_desc` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `examstatus`
--

INSERT INTO `examstatus` (`exam_status_id`, `exam_status_desc`) VALUES
(1, 'ACTIVE'),
(2, 'INACTIVE'),
(3, 'PASSED'),
(4, 'AWAITING RESULT'),
(5, 'FAILED');

-- --------------------------------------------------------

--
-- Table structure for table `logindb`
--

DROP TABLE IF EXISTS `logindb`;
CREATE TABLE `logindb` (
  `userid` int(11) NOT NULL,
  `usertypeid` varchar(3) NOT NULL,
  `password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `newseventsdb`
--

DROP TABLE IF EXISTS `newseventsdb`;
CREATE TABLE `newseventsdb` (
  `userid` int(11) NOT NULL,
  `newseventid` int(11) NOT NULL,
  `title` varchar(130) NOT NULL,
  `post` longblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `salarystatus`
--

DROP TABLE IF EXISTS `salarystatus`;
CREATE TABLE `salarystatus` (
  `salarystatusid` int(1) NOT NULL,
  `salary_status_desc` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `staffaccountsdb`
--

DROP TABLE IF EXISTS `staffaccountsdb`;
CREATE TABLE `staffaccountsdb` (
  `userid` int(11) NOT NULL,
  `in_hand_salary` int(10) NOT NULL,
  `allowance` int(10) NOT NULL,
  `total_salary` int(11) NOT NULL,
  `last_salary_payment_date` date NOT NULL,
  `salarystatusid` int(1) NOT NULL,
  `date_of_joining` date NOT NULL,
  `contract_end_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `staffinfodb`
--

DROP TABLE IF EXISTS `staffinfodb`;
CREATE TABLE `staffinfodb` (
  `userid` int(11) NOT NULL,
  `usertypeid` varchar(3) NOT NULL,
  `designation` varchar(45) NOT NULL,
  `highest_qualification` varchar(45) NOT NULL,
  `qualification_documents` longblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `staffinfodb`
--

INSERT INTO `staffinfodb` (`userid`, `usertypeid`, `designation`, `highest_qualification`, `qualification_documents`) VALUES
(2, 'T', 'Teacher', 'Diploma', '');

-- --------------------------------------------------------

--
-- Table structure for table `studentaccountsdb`
--

DROP TABLE IF EXISTS `studentaccountsdb`;
CREATE TABLE `studentaccountsdb` (
  `userid` int(11) NOT NULL,
  `classid` varchar(3) NOT NULL,
  `paid_fees` int(10) NOT NULL,
  `outstanding_fees` int(10) NOT NULL,
  `last_payment_date` date NOT NULL,
  `due_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `studentaccountsdb`
--

INSERT INTO `studentaccountsdb` (`userid`, `classid`, `paid_fees`, `outstanding_fees`, `last_payment_date`, `due_date`) VALUES
(1, '1A', 0, 0, '2018-01-31', '2018-01-31'),
(4, '1A', 0, 0, '0000-00-00', '0000-00-00');

-- --------------------------------------------------------

--
-- Table structure for table `studentinfodb`
--

DROP TABLE IF EXISTS `studentinfodb`;
CREATE TABLE `studentinfodb` (
  `userid` int(11) NOT NULL,
  `classid` varchar(3) NOT NULL,
  `total_subjects` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `studentinfodb`
--

INSERT INTO `studentinfodb` (`userid`, `classid`, `total_subjects`) VALUES
(1, '1A', 1),
(4, '1A', 0);

-- --------------------------------------------------------

--
-- Table structure for table `studentsubjectdist`
--

DROP TABLE IF EXISTS `studentsubjectdist`;
CREATE TABLE `studentsubjectdist` (
  `userid` int(11) NOT NULL,
  `subjectid` int(3) NOT NULL,
  `classid` varchar(3) NOT NULL,
  `subject_report` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `studentsubjectdist`
--

INSERT INTO `studentsubjectdist` (`userid`, `subjectid`, `classid`, `subject_report`) VALUES
(1, 1, '1A', 1),
(4, 1, '1A', 1),
(1, 2, '1A', 1);

-- --------------------------------------------------------

--
-- Table structure for table `studentsubjectreport`
--

DROP TABLE IF EXISTS `studentsubjectreport`;
CREATE TABLE `studentsubjectreport` (
  `subject_report` int(1) NOT NULL,
  `subject_report_desc` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `studentsubjectreport`
--

INSERT INTO `studentsubjectreport` (`subject_report`, `subject_report_desc`) VALUES
(1, 'Pursuing'),
(2, 'Passed'),
(3, 'Failed'),
(4, 'Exempted');

-- --------------------------------------------------------

--
-- Table structure for table `studymaterial`
--

DROP TABLE IF EXISTS `studymaterial`;
CREATE TABLE `studymaterial` (
  `userid` int(11) NOT NULL,
  `filename` varchar(100) NOT NULL,
  `file` longblob NOT NULL,
  `size` double NOT NULL,
  `upload_date_time` datetime NOT NULL,
  `total_downloads` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject` (
  `subjectid` int(3) NOT NULL,
  `subject_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `subject`
--

INSERT INTO `subject` (`subjectid`, `subject_name`) VALUES
(1, 'History'),
(2, 'History');

-- --------------------------------------------------------

--
-- Table structure for table `teachersubjectdist`
--

DROP TABLE IF EXISTS `teachersubjectdist`;
CREATE TABLE `teachersubjectdist` (
  `userid` int(11) NOT NULL,
  `classid` varchar(3) NOT NULL,
  `subjectid` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `teachersubjectdist`
--

INSERT INTO `teachersubjectdist` (`userid`, `classid`, `subjectid`) VALUES
(2, '1A', 1),
(2, '1A', 2);

-- --------------------------------------------------------

--
-- Table structure for table `userdb`
--

DROP TABLE IF EXISTS `userdb`;
CREATE TABLE `userdb` (
  `userid` int(11) NOT NULL,
  `fname` varchar(45) NOT NULL,
  `lname` varchar(45) NOT NULL,
  `address` varchar(140) NOT NULL,
  `phone` int(15) NOT NULL,
  `alt_phone` int(15) NOT NULL,
  `email_id` varchar(45) NOT NULL,
  `photo` longblob NOT NULL,
  `id_proof` longblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `userdb`
--

INSERT INTO `userdb` (`userid`, `fname`, `lname`, `address`, `phone`, `alt_phone`, `email_id`, `photo`, `id_proof`) VALUES
(1, 'Ganesh', 'Chawkekar', 'fdas', 1234567890, 1234567890, 'fdsa', '', ''),
(2, 'Ganesh', 'Chawkekar', 'fdas', 1234567890, 1234567890, 'fdsa', '', ''),
(4, 'Mahesh', 'fdsa', '32', 1234567890, 12345678, '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `usertype`
--

DROP TABLE IF EXISTS `usertype`;
CREATE TABLE `usertype` (
  `usertypeid` varchar(3) NOT NULL,
  `usertype_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `usertype`
--

INSERT INTO `usertype` (`usertypeid`, `usertype_name`) VALUES
('AC', 'Accountant'),
('AD', 'Admin'),
('S', 'Student'),
('SAD', 'Sub Admin'),
('T', 'Teacher');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `class`
--
ALTER TABLE `class`
  ADD PRIMARY KEY (`classid`),
  ADD UNIQUE KEY `classid` (`classid`),
  ADD UNIQUE KEY `classroomid` (`classroomid`);

--
-- Indexes for table `classattendancedb`
--
ALTER TABLE `classattendancedb`
  ADD PRIMARY KEY (`classid`,`subjectid`,`userid`,`date`),
  ADD KEY `classattendancedb_ibfk_3` (`subjectid`),
  ADD KEY `fk_attendance_classStudentSubjectID` (`userid`,`classid`,`subjectid`);

--
-- Indexes for table `classroom`
--
ALTER TABLE `classroom`
  ADD PRIMARY KEY (`classroomid`),
  ADD UNIQUE KEY `classroomid` (`classroomid`);

--
-- Indexes for table `classsubjectdist`
--
ALTER TABLE `classsubjectdist`
  ADD PRIMARY KEY (`classid`,`subjectid`),
  ADD KEY `classsubjectdist_ibfk_1` (`subjectid`);

--
-- Indexes for table `classtimetable`
--
ALTER TABLE `classtimetable`
  ADD PRIMARY KEY (`classid`,`subjectid`);

--
-- Indexes for table `exam`
--
ALTER TABLE `exam`
  ADD PRIMARY KEY (`examid`),
  ADD KEY `exam_ibfk_2` (`subjectid`),
  ADD KEY `exam_ibfk_3` (`exam_location`),
  ADD KEY `fk_examResultClassSubjectID` (`classid`,`subjectid`),
  ADD KEY `exam_ibfk_4` (`exam_status_id`);

--
-- Indexes for table `examenrolldb`
--
ALTER TABLE `examenrolldb`
  ADD PRIMARY KEY (`examid`,`userid`),
  ADD KEY `examenrolldb_ibfk_1` (`userid`),
  ADD KEY `examenrolldb_ibfk_3` (`exam_status_id`);

--
-- Indexes for table `examhallticketdb`
--
ALTER TABLE `examhallticketdb`
  ADD PRIMARY KEY (`examid`,`userid`);

--
-- Indexes for table `examresultdb`
--
ALTER TABLE `examresultdb`
  ADD PRIMARY KEY (`examid`,`userid`);

--
-- Indexes for table `examstatus`
--
ALTER TABLE `examstatus`
  ADD PRIMARY KEY (`exam_status_id`);

--
-- Indexes for table `logindb`
--
ALTER TABLE `logindb`
  ADD PRIMARY KEY (`userid`),
  ADD UNIQUE KEY `userid` (`userid`),
  ADD KEY `logindb_ibfk_2` (`usertypeid`);

--
-- Indexes for table `newseventsdb`
--
ALTER TABLE `newseventsdb`
  ADD PRIMARY KEY (`newseventid`),
  ADD KEY `newseventsdb_ibfk_1` (`userid`);

--
-- Indexes for table `salarystatus`
--
ALTER TABLE `salarystatus`
  ADD PRIMARY KEY (`salarystatusid`);

--
-- Indexes for table `staffaccountsdb`
--
ALTER TABLE `staffaccountsdb`
  ADD PRIMARY KEY (`userid`),
  ADD UNIQUE KEY `userid` (`userid`),
  ADD KEY `staffaccountsdb_ibfk_2` (`salarystatusid`);

--
-- Indexes for table `staffinfodb`
--
ALTER TABLE `staffinfodb`
  ADD PRIMARY KEY (`userid`),
  ADD KEY `staffinfodb_ibfk_2` (`usertypeid`);

--
-- Indexes for table `studentaccountsdb`
--
ALTER TABLE `studentaccountsdb`
  ADD PRIMARY KEY (`classid`,`userid`);

--
-- Indexes for table `studentinfodb`
--
ALTER TABLE `studentinfodb`
  ADD PRIMARY KEY (`classid`,`userid`),
  ADD KEY `studentinfodb_ibfk_1` (`userid`),
  ADD KEY `studentinfodb_ibfk_2` (`classid`);

--
-- Indexes for table `studentsubjectdist`
--
ALTER TABLE `studentsubjectdist`
  ADD PRIMARY KEY (`classid`,`subjectid`,`userid`) USING BTREE,
  ADD KEY `studentsubjectdist_ibfk_3` (`subject_report`),
  ADD KEY `studentsubjectdist_ibfk_1` (`userid`,`classid`),
  ADD KEY `studentsubjectdist_ibfk_2` (`subjectid`);

--
-- Indexes for table `studentsubjectreport`
--
ALTER TABLE `studentsubjectreport`
  ADD PRIMARY KEY (`subject_report`);

--
-- Indexes for table `studymaterial`
--
ALTER TABLE `studymaterial`
  ADD PRIMARY KEY (`userid`,`filename`,`upload_date_time`);

--
-- Indexes for table `subject`
--
ALTER TABLE `subject`
  ADD PRIMARY KEY (`subjectid`);

--
-- Indexes for table `teachersubjectdist`
--
ALTER TABLE `teachersubjectdist`
  ADD PRIMARY KEY (`classid`,`subjectid`),
  ADD KEY `teachersubjectdist_ibfk_2` (`subjectid`),
  ADD KEY `fk_teacherClassSubjectID` (`classid`,`subjectid`),
  ADD KEY `teachersubjectdist_ibfk_3` (`userid`);

--
-- Indexes for table `userdb`
--
ALTER TABLE `userdb`
  ADD PRIMARY KEY (`userid`),
  ADD UNIQUE KEY `id` (`userid`);

--
-- Indexes for table `usertype`
--
ALTER TABLE `usertype`
  ADD PRIMARY KEY (`usertypeid`),
  ADD UNIQUE KEY `usertypeid` (`usertypeid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `class`
--
ALTER TABLE `class`
  MODIFY `classroomid` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `classroom`
--
ALTER TABLE `classroom`
  MODIFY `classroomid` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `exam`
--
ALTER TABLE `exam`
  MODIFY `examid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `examstatus`
--
ALTER TABLE `examstatus`
  MODIFY `exam_status_id` int(1) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `newseventsdb`
--
ALTER TABLE `newseventsdb`
  MODIFY `newseventid` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `salarystatus`
--
ALTER TABLE `salarystatus`
  MODIFY `salarystatusid` int(1) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `studentsubjectreport`
--
ALTER TABLE `studentsubjectreport`
  MODIFY `subject_report` int(1) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `subject`
--
ALTER TABLE `subject`
  MODIFY `subjectid` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `userdb`
--
ALTER TABLE `userdb`
  MODIFY `userid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `class`
--
ALTER TABLE `class`
  ADD CONSTRAINT `class_ibfk_1` FOREIGN KEY (`classroomid`) REFERENCES `classroom` (`classroomid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `classattendancedb`
--
ALTER TABLE `classattendancedb`
  ADD CONSTRAINT `fk_attendance_classStudentSubjectID` FOREIGN KEY (`userid`,`classid`,`subjectid`) REFERENCES `studentsubjectdist` (`userid`, `classid`, `subjectid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `classsubjectdist`
--
ALTER TABLE `classsubjectdist`
  ADD CONSTRAINT `classsubjectdist_ibfk_1` FOREIGN KEY (`subjectid`) REFERENCES `subject` (`subjectid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `classsubjectdist_ibfk_2` FOREIGN KEY (`classid`) REFERENCES `class` (`classid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `classtimetable`
--
ALTER TABLE `classtimetable`
  ADD CONSTRAINT `classtimetable_ibfk_1` FOREIGN KEY (`classid`,`subjectid`) REFERENCES `classsubjectdist` (`classid`, `subjectid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `exam`
--
ALTER TABLE `exam`
  ADD CONSTRAINT `exam_ibfk_3` FOREIGN KEY (`exam_location`) REFERENCES `classroom` (`classroomid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `exam_ibfk_4` FOREIGN KEY (`exam_status_id`) REFERENCES `examstatus` (`exam_status_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_examClassSubjectID` FOREIGN KEY (`classid`,`subjectid`) REFERENCES `classsubjectdist` (`classid`, `subjectid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `examenrolldb`
--
ALTER TABLE `examenrolldb`
  ADD CONSTRAINT `examenrolldb_ibfk_2` FOREIGN KEY (`examid`) REFERENCES `exam` (`examid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `examenrolldb_ibfk_3` FOREIGN KEY (`exam_status_id`) REFERENCES `examstatus` (`exam_status_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `examhallticketdb`
--
ALTER TABLE `examhallticketdb`
  ADD CONSTRAINT `examhallticketdb_ibfk_1` FOREIGN KEY (`examid`,`userid`) REFERENCES `examenrolldb` (`examid`, `userid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `examresultdb`
--
ALTER TABLE `examresultdb`
  ADD CONSTRAINT ` examenrolldb_ibfk_1` FOREIGN KEY (`examid`,`userid`) REFERENCES `examenrolldb` (`examid`, `userid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `logindb`
--
ALTER TABLE `logindb`
  ADD CONSTRAINT `logindb_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `userdb` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `logindb_ibfk_2` FOREIGN KEY (`usertypeid`) REFERENCES `usertype` (`usertypeid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `newseventsdb`
--
ALTER TABLE `newseventsdb`
  ADD CONSTRAINT `newseventsdb_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `staffinfodb` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `staffaccountsdb`
--
ALTER TABLE `staffaccountsdb`
  ADD CONSTRAINT `staffaccountsdb_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `staffinfodb` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `staffaccountsdb_ibfk_2` FOREIGN KEY (`salarystatusid`) REFERENCES `salarystatus` (`salarystatusid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `staffinfodb`
--
ALTER TABLE `staffinfodb`
  ADD CONSTRAINT `staffinfodb_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `userdb` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `staffinfodb_ibfk_2` FOREIGN KEY (`usertypeid`) REFERENCES `usertype` (`usertypeid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `studentaccountsdb`
--
ALTER TABLE `studentaccountsdb`
  ADD CONSTRAINT `studentaccountsdb_ibfk_1` FOREIGN KEY (`classid`,`userid`) REFERENCES `studentinfodb` (`classid`, `userid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `studentinfodb`
--
ALTER TABLE `studentinfodb`
  ADD CONSTRAINT `studentinfodb_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `userdb` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `studentinfodb_ibfk_2` FOREIGN KEY (`classid`) REFERENCES `class` (`classid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `studentsubjectdist`
--
ALTER TABLE `studentsubjectdist`
  ADD CONSTRAINT `studentsubjectdist_ibfk_1` FOREIGN KEY (`userid`,`classid`) REFERENCES `studentinfodb` (`userid`, `classid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `studentsubjectdist_ibfk_2` FOREIGN KEY (`subjectid`) REFERENCES `subject` (`subjectid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `studentsubjectdist_ibfk_3` FOREIGN KEY (`subject_report`) REFERENCES `studentsubjectreport` (`subject_report`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `studymaterial`
--
ALTER TABLE `studymaterial`
  ADD CONSTRAINT `studymaterial_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `userdb` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `teachersubjectdist`
--
ALTER TABLE `teachersubjectdist`
  ADD CONSTRAINT `fk_teacherClassSubjectID` FOREIGN KEY (`classid`,`subjectid`) REFERENCES `classsubjectdist` (`classid`, `subjectid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `teachersubjectdist_ibfk_3` FOREIGN KEY (`userid`) REFERENCES `staffinfodb` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
