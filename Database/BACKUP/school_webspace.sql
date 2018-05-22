-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 28, 2018 at 06:42 PM
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
-- Creation: Jan 27, 2018 at 07:59 AM
--

DROP TABLE IF EXISTS `class`;
CREATE TABLE IF NOT EXISTS `class` (
  `classid` varchar(3) NOT NULL,
  `classcapacity` int(11) NOT NULL,
  `classsize` int(11) NOT NULL,
  `classroomid` int(3) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`classid`),
  UNIQUE KEY `classid` (`classid`),
  UNIQUE KEY `classroomid` (`classroomid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- RELATIONS FOR TABLE `class`:
--   `classroomid`
--       `classroom` -> `classroomid`
--

--
-- Dumping data for table `class`
--

INSERT INTO `class` (`classid`, `classcapacity`, `classsize`, `classroomid`) VALUES
('1A', 100, 40, 2);

-- --------------------------------------------------------

--
-- Table structure for table `classroom`
--
-- Creation: Jan 27, 2018 at 07:49 AM
--

DROP TABLE IF EXISTS `classroom`;
CREATE TABLE IF NOT EXISTS `classroom` (
  `classroomid` int(3) NOT NULL AUTO_INCREMENT,
  `capacity` int(11) NOT NULL,
  PRIMARY KEY (`classroomid`),
  UNIQUE KEY `classroomid` (`classroomid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- RELATIONS FOR TABLE `classroom`:
--

--
-- Dumping data for table `classroom`
--

INSERT INTO `classroom` (`classroomid`, `capacity`) VALUES
(2, 100);

-- --------------------------------------------------------

--
-- Table structure for table `subject`
--
-- Creation: Jan 27, 2018 at 09:11 AM
--

DROP TABLE IF EXISTS `subject`;
CREATE TABLE IF NOT EXISTS `subject` (
  `subjectid` int(3) NOT NULL AUTO_INCREMENT,
  `subjectname` varchar(50) NOT NULL,
  PRIMARY KEY (`subjectid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- RELATIONS FOR TABLE `subject`:
--

-- --------------------------------------------------------

--
-- Table structure for table `userdb`
--
-- Creation: Jan 27, 2018 at 01:24 PM
--

DROP TABLE IF EXISTS `userdb`;
CREATE TABLE IF NOT EXISTS `userdb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usertypeid` varchar(3) NOT NULL,
  `fname` varchar(45) NOT NULL,
  `lname` varchar(45) NOT NULL,
  `address` varchar(140) NOT NULL,
  `phone` int(15) NOT NULL,
  `altphone` int(15) NOT NULL,
  `emailid` varchar(45) NOT NULL,
  `photo` longblob NOT NULL,
  `idproof` longblob NOT NULL,
  PRIMARY KEY (`usertypeid`,`id`) USING BTREE,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- RELATIONS FOR TABLE `userdb`:
--   `usertypeid`
--       `usertype` -> `usertypeid`
--

--
-- Dumping data for table `userdb`
--

INSERT INTO `userdb` (`id`, `usertypeid`, `fname`, `lname`, `address`, `phone`, `altphone`, `emailid`, `photo`, `idproof`) VALUES
(1, 'AD', 'Ganesh', 'Chawkekar', 'Some address', 1234567890, 1234567890, 'fas', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `usertype`
--
-- Creation: Jan 27, 2018 at 12:35 PM
--

DROP TABLE IF EXISTS `usertype`;
CREATE TABLE IF NOT EXISTS `usertype` (
  `usertypeid` varchar(3) NOT NULL,
  `usertypename` varchar(50) NOT NULL,
  PRIMARY KEY (`usertypeid`),
  UNIQUE KEY `usertypeid` (`usertypeid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- RELATIONS FOR TABLE `usertype`:
--

--
-- Dumping data for table `usertype`
--

INSERT INTO `usertype` (`usertypeid`, `usertypename`) VALUES
('AC', 'Accountant'),
('AD', 'Admin'),
('S', 'Student'),
('SAD', 'Sub Admin'),
('T', 'Teacher');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `class`
--
ALTER TABLE `class`
  ADD CONSTRAINT `class_ibfk_1` FOREIGN KEY (`classroomid`) REFERENCES `classroom` (`classroomid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `userdb`
--
ALTER TABLE `userdb`
  ADD CONSTRAINT `userdb_ibfk_1` FOREIGN KEY (`usertypeid`) REFERENCES `usertype` (`usertypeid`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
