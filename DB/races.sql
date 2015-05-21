-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 21 Mai 2015 la 01:29
-- Versiune server: 5.6.24
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
-- Structura de tabel pentru tabelul `races`
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Salvarea datelor din tabel `races`
--

INSERT INTO `races` (`rid`, `uid`, `departure_city`, `arrival_city`, `departure_time`, `arrival_time`, `departure_location`, `arrival_location`) VALUES
(8, 5, 'Severin', 'Bucuresti', '2015-04-13 13:00:00', '2015-04-13 16:00:00', 'str. Lalelelor, nr 45', 'str. Ratustelor, nr 32'),
(9, 12, 'Craiova', 'Bucuresti', '2025-05-21 17:33:00', '2015-05-21 20:18:00', 'str. Lacramioarelor, nr 14', 'str. Plopilor-fara-sot, nr 34');

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
  MODIFY `rid` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=10;
--
-- Restrictii pentru tabele sterse
--

--
-- Restrictii pentru tabele `races`
--
ALTER TABLE `races`
ADD CONSTRAINT `races_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `users` (`uid`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
