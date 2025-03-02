-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: [Timestamp]
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

-- Database: student_management

-- --------------------------------------------------------

--
-- Table structure for table students
--

CREATE TABLE students (
  rollno varchar(50) NOT NULL,
  name varchar(50) NOT NULL,
  email varchar(50) NOT NULL,
  contactnumber varchar(15) NOT NULL,
  homecity varchar(50) NOT NULL,
  PRIMARY KEY (rollno)
);

--
-- Dumping data for table students
--

INSERT INTO students (rollno, name, email, contactnumber, homecity) VALUES
('292', 'Devansh Yadav', 'devansh6@gmail.com', '9372942499', 'Pune'),
('304', 'Mrunal Bhamare', 'mrunal15@gmail.com', '8805718521', 'Pune');

-- Add any additional records for students here

COMMIT;
