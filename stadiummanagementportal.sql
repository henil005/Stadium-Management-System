-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 24, 2024 at 07:25 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `stadiummanagementportal`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_event` (IN `p_id` INT)   BEGIN
    -- Delete the event record
    DELETE FROM Events
    WHERE id = p_id;

  
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_ticket` (IN `p_id` INT)   BEGIN
    -- Delete the ticket record
    DELETE FROM Tickets
    WHERE id = p_id;

    
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_user` (IN `p_id` INT)   BEGIN
    -- Delete the user record
    DELETE FROM Users
    WHERE id = p_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `update_event` (IN `p_id` INT, IN `p_name` VARCHAR(255), IN `p_date` DATETIME, IN `p_type` VARCHAR(255))   BEGIN
    -- Update the event information
    UPDATE Events
    SET name = p_name,
        date = p_date,
        type = p_type
    WHERE id = p_id;

    -- Check if any row was updated
   
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `update_ticket` (IN `p_id` INT, IN `p_event_id` INT, IN `p_seat_number` VARCHAR(50), IN `p_user_id` INT, IN `p_status` VARCHAR(50))   BEGIN
    -- Update the ticket record
    UPDATE Tickets
    SET event_id = p_event_id,
        seat_number = p_seat_number,
        user_id = p_user_id,
        status = p_status
    WHERE id = p_id;

   
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `update_user` (IN `p_id` INT, IN `p_username` VARCHAR(255), IN `p_password` VARCHAR(255), IN `p_email` VARCHAR(255), IN `p_membership_level` VARCHAR(50))   BEGIN
    -- Update the user information
    UPDATE Users
    SET username = p_username,
        password = p_password,
        email = p_email,
        membership_level = p_membership_level
    WHERE id = p_id;

END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `events`
--

CREATE TABLE `events` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `date` datetime NOT NULL,
  `type` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `events`
--

INSERT INTO `events` (`id`, `name`, `date`, `type`) VALUES
(1, 'xyz', '2025-09-19 09:50:50', 'abc'),
(2, 'ipl match-1', '2024-09-20 07:30:00', 'rcb vs csk'),
(3, 'ipl match-2', '2024-08-24 05:45:13', 'rr vs gt'),
(4, 'ipl match-3', '2024-08-25 05:45:13', 'srh vs kkr');

--
-- Triggers `events`
--
DELIMITER $$
CREATE TRIGGER `delete_ticket` AFTER DELETE ON `events` FOR EACH ROW BEGIN
	DELETE from tickets where event_id=old.id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `login_logs`
--

CREATE TABLE `login_logs` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `username` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `login_time` timestamp NOT NULL DEFAULT current_timestamp(),
  `status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `login_logs`
--

INSERT INTO `login_logs` (`id`, `username`, `password_hash`, `login_time`, `status`) VALUES
(2, 'het', 'hrt2476', '2024-08-23 23:23:57', ''),
(3, 'het', 'het2476', '2024-08-24 03:48:00', 'after login'),
(4, 'dhruv', 'dhruv258', '2024-08-24 03:48:00', 'after login'),
(5, 'keval', 'keval123', '2024-08-24 03:49:01', 'after login');

-- --------------------------------------------------------

--
-- Table structure for table `tickets`
--

CREATE TABLE `tickets` (
  `id` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  `seat_number` varchar(50) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tickets`
--

INSERT INTO `tickets` (`id`, `event_id`, `seat_number`, `user_id`, `status`) VALUES
(3, 1, 'A1', 1, 'Available'),
(4, 1, 'A4', 3, 'Available'),
(5, 1, 'A1', 1, 'Available'),
(7, 2, 'd2', 2, 'vip');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `membership_level` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `email`, `membership_level`) VALUES
(3, 'het', 'het2476', 'hetthummar2474@gmail.com', 'vip'),
(4, 'dhruv', 'dhruv258', 'dhruvthummar2584@gmail.com', 'vip'),
(5, 'keval', 'keval123', 'keval123@gmail.com', 'primeum');

--
-- Triggers `users`
--
DELIMITER $$
CREATE TRIGGER `after_delete` AFTER DELETE ON `users` FOR EACH ROW BEGIN
    
        INSERT INTO login_logs (username, password_hash, login_time,status)
        VALUES (old.username, old.password, NOW(),'after update');
   
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `after_login` AFTER INSERT ON `users` FOR EACH ROW BEGIN
    
        INSERT INTO login_logs (username, password_hash, login_time,status)
        VALUES (NEW.username, NEW.password, NOW(),'after login');
   
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `after_update` AFTER UPDATE ON `users` FOR EACH ROW BEGIN
    
        INSERT INTO login_logs (username, password_hash, login_time,status)
        VALUES (NEW.username, NEW.password, NOW(),'after update');
   
END
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `login_logs`
--
ALTER TABLE `login_logs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tickets`
--
ALTER TABLE `tickets`
  ADD PRIMARY KEY (`id`),
  ADD KEY `event_id` (`event_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `events`
--
ALTER TABLE `events`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `login_logs`
--
ALTER TABLE `login_logs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tickets`
--
ALTER TABLE `tickets`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tickets`
--
ALTER TABLE `tickets`
  ADD CONSTRAINT `tickets_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `events` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
