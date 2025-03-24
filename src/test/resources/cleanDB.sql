-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: boulderbook_test
-- ------------------------------------------------------
-- Server version	8.0.41

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
-- Table structure for table `climb`
--

DROP TABLE IF EXISTS `climb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `climb` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `user_id` int NOT NULL,
                         `gym_id` int NOT NULL,
                         `date` date NOT NULL,
                         `climb_type` enum('Slab','Overhang','Dyno','Vertical','Roof') NOT NULL,
                         `grade` varchar(10) NOT NULL,
                         `attempts` int NOT NULL,
                         `success` tinyint(1) NOT NULL,
                         `notes` text,
                         PRIMARY KEY (`id`),
                         KEY `user_id` (`user_id`),
                         KEY `gym_id` (`gym_id`),
                         CONSTRAINT `climb_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
                         CONSTRAINT `climb_ibfk_2` FOREIGN KEY (`gym_id`) REFERENCES `gym` (`id`) ON DELETE CASCADE,
                         CONSTRAINT `climb_chk_1` CHECK ((`attempts` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `climb`
--

LOCK TABLES `climb` WRITE;
/*!40000 ALTER TABLE `climb` DISABLE KEYS */;
INSERT INTO `climb` VALUES
                        (1,1,1,'2025-02-10','Overhang','V5',3,1,'Felt strong!'),
                        (2,2,2,'2025-02-11','Slab','V4',2,0,'Slipped on the last move.'),
                        (3,1,1,'2025-03-03','Dyno','V1',4,1,'Good climb');
/*!40000 ALTER TABLE `climb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gym`
--

DROP TABLE IF EXISTS `gym`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gym` (
                       `id` int NOT NULL AUTO_INCREMENT,
                       `name` varchar(100) NOT NULL,
                       `location` varchar(255) NOT NULL,
                       `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                       `latitude` varchar(50),
                       `longitude` varchar(50),
                       PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gym`
--

LOCK TABLES `gym` WRITE;
/*!40000 ALTER TABLE `gym` DISABLE KEYS */;
INSERT INTO `gym` (id, name, location, created_at, latitude, longitude) VALUES
                                                                            (1, 'East Side Boulders', '123 Boulder St, Madison, WI', '2025-02-18 04:55:49', '43.0747', '-89.3837'),
                                                                            (2, 'Downtown Boulders', '456 Climber Ave, Madison, WI', '2025-02-18 04:55:49', '43.0749', '-89.3839');
/*!40000 ALTER TABLE `gym` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `email` varchar(100) NOT NULL,
                         `username` varchar(100) DEFAULT NULL,
                         `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                         `cognito_sub` varchar(50) DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `email_or_username` (`email`),
                         UNIQUE KEY `cognito_sub` (`cognito_sub`),
                         UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES
                        (1, 'climber123@example.com', 'climber123', '2025-02-18 04:55:49', 'test-cognito-sub'),
                        (2, 'boulderking@example.com', 'boulderking', '2025-02-18 04:55:49', 'another-cognito-sub');
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

-- Dump completed on 2025-03-09 23:45:20