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
-- Structura de tabel pentru tabelul `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `uid` int(11) NOT NULL COMMENT 'id unic pt fiecare client',
  `firstname` varchar(40) NOT NULL,
  `lastname` varchar(40) NOT NULL,
  `username` varchar(20) NOT NULL,
  `email` varchar(40) NOT NULL,
  `password` varchar(30) NOT NULL,
  `location` varchar(40) NOT NULL,
  `phone` varchar(40) NOT NULL,
  `photo_name` varchar(60) NOT NULL COMMENT 'numele pozei',
  `history` varchar(150) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Salvarea datelor din tabel `users`
--

INSERT INTO `users` (`uid`, `firstname`, `lastname`, `username`, `email`, `password`, `location`, `phone`, `photo_name`, `history`) VALUES
(1, '', '', 'h', 'h@h.com', 'h', 'bucuresti', '0720202202', 'images/h.txt', NULL),
(2, '', '', 'paulon', 'pua@lon.com', 'paulon', 'Pitesti', '0723456789', 'images/paulon.txt', NULL),
(3, '', '', 'vlad', 'vl@ad.com', 'vlad', 'Sibiu', '04397654', 'images/vlad.txt', NULL),
(4, '', '', 'adi', 'a@di.com', 'adi', 'Brasov', '0722456738', 'images/adi.txt', NULL),
(5, '', '', 'flori', 'fl@ri.com', 'flori', 'Severin', '0734562189', 'images/flori.txt', NULL),
(12, '', '', 'serban', 'ser@ban.com', 'serban', 'Craiova', '0737585840', 'images/serban.txt', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`uid`), ADD UNIQUE KEY `username` (`username`), ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `uid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id unic pt fiecare client',AUTO_INCREMENT=13;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
