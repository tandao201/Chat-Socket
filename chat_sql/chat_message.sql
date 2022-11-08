CREATE DATABASE  IF NOT EXISTS `chat` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `chat`;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: chat
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `message_id` int NOT NULL AUTO_INCREMENT,
  `message` varchar(255) NOT NULL,
  `createAt` varchar(25) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `roomId` int DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,'test message',NULL,1,NULL,'tandao'),(18,'tan tin nhan test','05/11/2022-10:40',4,700,'tan'),(19,'khanh tin nhắn test','05/11/2022-10:40',3,700,'khanh'),(20,'khánh test 2','05/11/2022-11:05',3,700,'khanh'),(21,'tân test 2','05/11/2022-11:05',4,700,'tan'),(22,'alo','06/11/2022-12:21',4,700,'tan'),(23,'alo','06/11/2022-12:34',4,700,'tan'),(24,'chào bạn mạnh nhé','06/11/2022-12:38',4,600,'tan'),(25,'chào bạn tân nhé','06/11/2022-12:38',2,600,'manh'),(26,'alo','06/11/2022-12:40',3,700,'khanh'),(27,'alo','06/11/2022-12:40',2,500,'manh'),(28,'alo','06/11/2022-12:40',3,700,'khanh'),(29,'alo','06/11/2022-12:43',3,700,'khanh'),(30,'tao bảo','06/11/2022-12:51',3,700,'khanh'),(31,'bảo gì','06/11/2022-12:51',4,700,'tan'),(32,'alo','06/11/2022-03:41',3,700,'khanh'),(33,'alo','06/11/2022-10:53',3,300,'khanh'),(34,'tin nhắn khánh test','06/11/2022-10:55',3,700,'khanh'),(35,'tin nhắn tân test','06/11/2022-10:55',4,700,'tan'),(38,'tk Mạnh lười vailoz','07/11/2022-10:14',3,700,'khanh'),(39,'địt mẹ toàn đi chơi xong lấy lí do vcc','07/11/2022-10:14',4,700,'tan'),(40,'tao là Tuấn','07/11/2022-10:16',5,1200,'tuan'),(41,'alo','07/11/2022-10:17',5,1200,'tuan'),(42,'alo','07/11/2022-10:27',4,700,'tan'),(43,'Mạnh toàn lừa ae','07/11/2022-10:31',3,700,'khanh'),(44,'đúng đúng','07/11/2022-10:31',4,700,'tan'),(45,'??????','07/11/2022-10:32',3,700,'khanh');
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-08  9:33:43
