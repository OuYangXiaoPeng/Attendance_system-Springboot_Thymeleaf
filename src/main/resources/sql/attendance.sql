-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: attendance
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendance` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `checkin_time` datetime NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `method` varchar(50) DEFAULT NULL,
  `status` varchar(20) DEFAULT '正常',
  PRIMARY KEY (`id`),
  KEY `attendance_ibfk_1` (`user_id`),
  CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
INSERT INTO `attendance` VALUES (1,1,'2025-09-20 16:51:10','教室','手机','正常'),(2,1,'2025-09-20 17:06:11','宿舍','刷脸','正常'),(4,6,'2025-09-26 16:00:51','教室','手机','正常'),(5,1,'2025-09-27 14:32:03','宿舍','刷脸','正常'),(6,1,'2025-09-27 14:41:15','教室','手机','正常'),(8,6,'2025-09-27 16:25:24','教室','手机','正常'),(9,6,'2025-09-27 16:25:39','宿舍','刷脸','正常'),(10,1,'2025-10-11 16:20:02','教室','刷脸','正常'),(11,1,'2025-10-11 16:20:06','教室','手机','正常'),(12,1,'2025-10-11 16:20:52','校外','手机','正常'),(13,1,'2025-10-11 16:22:45','校外','手机','正常'),(14,1,'2025-10-11 16:36:09','宿舍','刷脸','正常'),(15,1,'2025-10-11 16:43:48','教室','刷脸','正常'),(16,1,'2025-10-11 16:44:24','宿舍','手机','正常'),(17,1,'2025-10-11 16:50:20','校外','刷脸','正常'),(18,1,'2025-10-11 16:53:26','宿舍','手机','正常'),(19,1,'2025-10-11 16:55:44','宿舍','手机','正常'),(20,1,'2025-10-11 16:58:58','校外','手机','正常');
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leave_request`
--

DROP TABLE IF EXISTS `leave_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `leave_request` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `status` varchar(20) DEFAULT '待审批',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `leave_request_ibfk_1` (`user_id`),
  CONSTRAINT `leave_request_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leave_request`
--

LOCK TABLES `leave_request` WRITE;
/*!40000 ALTER TABLE `leave_request` DISABLE KEYS */;
INSERT INTO `leave_request` VALUES (1,1,'2025-09-21 10:07:00','2025-09-30 10:07:00','生病了','待审批','2025-09-21 10:07:59'),(3,6,'2025-09-19 16:26:00','2025-09-25 16:26:00','回家看望家人','已批准','2025-09-27 16:26:18'),(4,6,'2025-10-11 17:01:00','2025-10-25 17:01:00','家里人生病了，回家看望家人','已批准','2025-10-11 17:02:48'),(5,6,'2025-10-16 17:05:00','2025-10-22 17:05:00','生病了','已驳回','2025-10-11 17:05:24');
/*!40000 ALTER TABLE `leave_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `password` varchar(255) NOT NULL,
  `full_name` varchar(128) DEFAULT NULL,
  `role` enum('STUDENT','TEACHER','ADMIN') DEFAULT 'STUDENT',
  `phone` varchar(20) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'S202501','2501','张三','STUDENT','12312313','2025-09-20 07:39:38'),(2,'T202501','2501','赵美','TEACHER','12312313','2025-09-21 01:17:54'),(4,'S202503','2503','王五','STUDENT','12346','2025-09-21 03:56:12'),(5,'T202502','2502','王老师','TEACHER','123123','2025-09-21 04:01:25'),(6,'S202502','2502','赵六','STUDENT','1234655','2025-09-26 08:00:14');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-12  9:43:47
