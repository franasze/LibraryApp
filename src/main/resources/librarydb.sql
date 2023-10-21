-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 21, 2023 at 01:48 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `librarydb`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbooks`
--

CREATE TABLE `tbooks` (
  `id` int(10) NOT NULL,
  `author` varchar(20) NOT NULL,
  `title` varchar(20) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `ISBN` int(13) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbooks`
--

INSERT INTO `tbooks` (`id`, `author`, `title`, `status`, `ISBN`) VALUES
(1, 'Boleslaw Prus', 'Lalka', 1, 1),
(2, 'Adam Mickiewicz', 'Pan Tadeusz', 1, 2),
(3, 'Juliusz Slowacki', 'Kordian', 1, 3);

-- --------------------------------------------------------

--
-- Table structure for table `tborrowed`
--

CREATE TABLE `tborrowed` (
  `id` int(10) NOT NULL,
  `login` varchar(20) NOT NULL,
  `isbn` int(13) NOT NULL,
  `return_date_of_book` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tborrowed`
--

INSERT INTO `tborrowed` (`id`, `login`, `isbn`, `return_date_of_book`) VALUES

-- --------------------------------------------------------

--
-- Table structure for table `tuser`
--

CREATE TABLE `tuser` (
  `id` int(10) NOT NULL,
  `login` varchar(40) NOT NULL,
  `password` varchar(35) NOT NULL,
  `role` varchar(20) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tuser`
--

INSERT INTO `tuser` (`id`, `login`, `password`, `role`, `first_name`, `last_name`) VALUES
(1, 'admin', 'admin', 'ADMIN', 'Arek', 'Kera'),
(2, 'mike12', 'mike123', 'USER', 'Mike', 'Nowak');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbooks`
--
ALTER TABLE `tbooks`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tborrowed`
--
ALTER TABLE `tborrowed`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tuser`
--
ALTER TABLE `tuser`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbooks`
--
ALTER TABLE `tbooks`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `tborrowed`
--
ALTER TABLE `tborrowed`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `tuser`
--
ALTER TABLE `tuser`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
