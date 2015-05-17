-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 17, 2015 at 08:44 PM
-- Server version: 5.6.24
-- PHP Version: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `packs`
--

-- --------------------------------------------------------

--
-- Table structure for table `races`
--

CREATE TABLE IF NOT EXISTS `races` (
  `rid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `departure_city` varchar(30) NOT NULL,
  `arrival_city` varchar(30) NOT NULL,
  `departure_time` datetime NOT NULL,
  `arrival_time` datetime NOT NULL,
  `departure_location` varchar(100) NOT NULL,
  `arrival_location` varchar(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `races`
--

INSERT INTO `races` (`rid`, `uid`, `departure_city`, `arrival_city`, `departure_time`, `arrival_time`, `departure_location`, `arrival_location`) VALUES
(1, 1, 'pitesti', 'bucuresti', '2015-05-13 08:00:00', '2015-05-10 10:00:00', 'str Nordului, nr 29', 'gara de vest'),
(2, 2, 'timisoara', 'bucuresti', '2015-05-13 10:00:00', '2015-05-13 20:00:00', 'gara de nord', 'gara de sus'),
(3, 2, 'timisoara', 'bucuresti', '2015-05-14 00:00:00', '2015-05-12 10:00:00', 'str timisului de vest, McDonald', 'gara de nord'),
(4, 1, 'x', 'x', '2015-05-13 00:00:00', '2015-05-13 08:00:00', 'marte', 'venus');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `races`
--
ALTER TABLE `races`
  ADD PRIMARY KEY (`rid`), ADD KEY `uid` (`uid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `races`
--
ALTER TABLE `races`
  MODIFY `rid` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `races`
--
ALTER TABLE `races`
ADD CONSTRAINT `races_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `users` (`uid`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
