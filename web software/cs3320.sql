-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 07, 2019 at 11:27 PM
-- Server version: 10.4.8-MariaDB
-- PHP Version: 7.3.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cs3320`
--

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `userId` int(11) NOT NULL,
  `orderNumber` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `totalPrice` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `paymentinformation`
--

CREATE TABLE `paymentinformation` (
  `userId` int(11) NOT NULL,
  `cardType` char(15) NOT NULL,
  `cardNumber` int(16) NOT NULL,
  `expDate` int(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `productId` int(11) NOT NULL,
  `description` text NOT NULL,
  `unitPrice` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`productId`, `description`, `unitPrice`) VALUES
(1, 'Product 1', 1),
(2, 'Product 2', 2),
(1, 'Product 1', 1),
(3, 'Product 3', 3),
(4, 'Product 4', 4),
(5, 'Product 5', 5);

-- --------------------------------------------------------

--
-- Table structure for table `shippinginformation`
--

CREATE TABLE `shippinginformation` (
  `userId` int(11) NOT NULL,
  `address1` char(50) NOT NULL,
  `address2` char(50) NOT NULL,
  `city` char(50) NOT NULL,
  `state` char(15) NOT NULL,
  `zip` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `usercredentials`
--

CREATE TABLE `usercredentials` (
  `userId` int(11) NOT NULL,
  `username` char(50) NOT NULL,
  `pass` char(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `userinformation`
--

CREATE TABLE `userinformation` (
  `userId` int(11) NOT NULL,
  `fullname` char(50) NOT NULL,
  `address1` char(50) NOT NULL,
  `address2` char(50) NOT NULL,
  `city` char(50) NOT NULL,
  `state` char(15) NOT NULL,
  `zip` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
