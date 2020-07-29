-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: bestdeal
-- ------------------------------------------------------
-- Server version	8.0.17

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
-- Table structure for table `customerorders`
--

DROP TABLE IF EXISTS `customerorders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customerorders` (
  `orderId` int(11) NOT NULL,
  `username` varchar(45) NOT NULL,
  `ordername` int(11) NOT NULL,
  `orderquantity` int(11) DEFAULT NULL,
  `warranty` tinyint(4) DEFAULT '0',
  `address` varchar(45) DEFAULT NULL,
  `creditcard` varchar(45) DEFAULT NULL,
  `deliverydate` datetime DEFAULT CURRENT_TIMESTAMP,
  `zip` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`orderId`,`username`,`ordername`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customerorders`
--

LOCK TABLES `customerorders` WRITE;
/*!40000 ALTER TABLE `customerorders` DISABLE KEYS */;
INSERT INTO `customerorders` VALUES (1,'r@1',1,1,0,'3001 S king Dr','9638527417894563','2019-10-22 02:20:53','60120'),(2,'r@1',2,2,0,'3001 S king Dr','9638527417894563','2019-10-22 02:20:53','60120'),(3,'r@1',3,4,0,'3001 S king Dr','9638527417894563','2019-10-22 02:20:53','60120'),(4,'r@1',1,4,0,'3001 S king Dr','9638527417894563','2019-10-22 02:20:53','60120'),(5,'j@1',5,2,0,'South Halsted 75th','6385274178945639','2019-10-22 03:45:53','60620'),(6,'r@1',104,1,0,'3001 S king Dr','9876543217418523','2019-10-22 15:55:10','60120'),(7,'s@1',19,1,0,'35th street ','7418529637894563','2019-10-28 18:49:40','02101'),(7,'s@1',20,2,0,'35th street ','7418529637894563','2019-10-28 18:49:40','02101'),(8,'v@1',36,4,0,'North Ciecero Avenue','4567891237539511','2019-10-28 19:00:15','98101'),(9,'k@1',51,2,0,'32nd street','8521364789741852','2019-10-28 19:10:42','75001'),(10,'a@1',46,2,0,'37th Street','9637539517417526','2019-10-28 19:16:46','75001'),(11,'a@1',29,1,0,'37th Street','9637539517417526','2019-10-28 19:19:57','75001'),(12,'d@1',60,1,0,'33rd Street','7539517418529631','2019-10-28 14:29:06','60616'),(13,'h@1',44,2,0,'3001 S king Dr','1597538524567411','2019-10-28 17:02:18','60616');
/*!40000 ALTER TABLE `customerorders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_accessory`
--

DROP TABLE IF EXISTS `product_accessory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_accessory` (
  `p_id` int(11) NOT NULL,
  `a_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_accessory`
--

LOCK TABLES `product_accessory` WRITE;
/*!40000 ALTER TABLE `product_accessory` DISABLE KEYS */;
INSERT INTO `product_accessory` VALUES (1,101),(1,102),(1,103),(1,104),(1,105),(1,106),(2,101),(2,102),(2,103),(2,104),(2,105),(2,106),(3,101),(3,102),(3,103),(3,104),(3,105),(3,106),(4,101),(4,102),(4,103),(4,104),(4,105),(4,106),(5,101),(5,102),(5,103),(5,104),(5,105),(5,106),(6,101),(6,102),(6,103),(6,104),(6,105),(6,106),(7,101),(7,102),(7,103),(7,104),(7,105),(7,106),(8,101),(8,102),(8,103),(8,104),(8,105),(8,106),(9,101),(9,102),(9,103),(9,104),(9,105),(9,106),(10,101),(10,102),(10,103),(10,104),(10,105),(10,106),(11,107),(11,108),(11,109),(11,110),(11,111),(11,112),(12,107),(12,108),(12,109),(12,110),(12,111),(12,112),(13,107),(13,108),(13,109),(13,110),(13,111),(13,112),(14,107),(14,108),(14,109),(14,110),(14,111),(14,112),(15,107),(15,108),(15,109),(15,110),(15,111),(15,112),(16,107),(16,108),(16,109),(16,110),(16,111),(16,112),(17,107),(17,108),(17,109),(17,110),(17,111),(17,112),(18,107),(18,108),(18,109),(18,110),(18,111),(18,112),(19,113),(19,114),(19,115),(19,116),(19,117),(19,118),(20,113),(20,114),(20,115),(20,116),(20,117),(20,118),(21,113),(21,114),(21,115),(21,116),(21,117),(21,118),(22,113),(22,114),(22,115),(22,116),(22,117),(22,118),(23,113),(23,114),(23,115),(23,116),(23,117),(23,118),(24,113),(24,114),(24,115),(24,116),(24,117),(24,118),(25,113),(25,114),(25,115),(25,116),(25,117),(25,118),(26,113),(26,114),(26,115),(26,116),(26,117),(26,118),(27,113),(27,114),(27,115),(27,116),(27,117),(27,118),(28,113),(28,114),(28,115),(28,116),(28,117),(28,118),(29,119),(29,120),(29,121),(29,122),(30,119),(30,120),(30,121),(30,122),(31,119),(31,120),(31,121),(31,122),(32,119),(32,120),(32,121),(32,122),(33,119),(33,120),(33,121),(33,122),(34,119),(34,120),(34,121),(34,122),(35,119),(35,120),(35,121),(35,122),(36,124),(37,124),(38,124),(39,123),(40,125),(41,125),(42,125),(43,125),(44,126),(45,126),(46,127),(47,127),(48,127),(49,128),(50,128),(51,129),(51,130),(52,129),(52,130),(53,129),(53,130),(54,129),(54,130),(55,129),(55,130),(56,131),(57,132),(58,131),(59,132),(60,131),(61,132);
/*!40000 ALTER TABLE `product_accessory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productcatalog`
--

DROP TABLE IF EXISTS `productcatalog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productcatalog` (
  `productid` int(11) NOT NULL,
  `model` varchar(250) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `image` varchar(45) DEFAULT NULL,
  `manufacturer` varchar(45) DEFAULT NULL,
  `pcondition` varchar(45) DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`productid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productcatalog`
--

LOCK TABLES `productcatalog` WRITE;
/*!40000 ALTER TABLE `productcatalog` DISABLE KEYS */;
INSERT INTO `productcatalog` VALUES (1,'LG - 65\" Class - LED - UK6090PUA Series - 2160p - Smart - 4K UHD TV with HDR',529.99,'imagesNew/1.jpg','LG','New',15,25,'TV'),(2,'LG - 75\" Class - LED - UK6190 Series - 2160p - Smart - 4K UHD TV with HDR',899.99,'imagesNew/2.jpg','LG','New',15,25,'TV'),(3,'LG - 32\" Class - LED - 720p - Smart - HDTV with HDR',139.99,'imagesNew/3.jpg','LG','New',15,25,'TV'),(4,'Sony - 43\" Class - LED - X800G Series - 2160p - Smart - 4K UHD TV with HDR',599.99,'imagesNew/4.jpg','Sony','New',10,20,'TV'),(5,'Sony - 55\" Class - LED - X900F Series - 2160p - Smart - 4K Ultra HD TV with HDR',1149.99,'imagesNew/5.jpg','Sony','New',10,20,'TV'),(6,'Sony - 65\" Class - LED - X750F Series - 2160p - Smart - 4K UHD TV with HDR',899.99,'imagesNew/6.jpg','Sony','New',10,20,'TV'),(7,'Samsung - 32\" Class - LED - N5300 Series - 1080p - Smart - HDTV',249.99,'imagesNew/7.jpg','Samsung','New',20,15,'TV'),(8,'Samsung - 55\" Class - LED - 7 Series - 2160p - Smart - 4K UHD TV with HDR',499.99,'imagesNew/8.jpg','Samsung','New',20,15,'TV'),(9,'Samsung - 65\" Class - LED - 8 Series - 2160p - Smart - 4K UHD TV with HDR',999.99,'imagesNew/9.jpg','Samsung','New',20,15,'TV'),(10,'Samsung - 65\" Class - LED - Q70 Series - 2160p - Smart - 4K UHD TV with HDR',1399.99,'imagesNew/10.jpg','Samsung','New',20,15,'TV'),(11,'JBL - 2.1-Channel Soundbar System with 6-1/2\" Wireless Subwoofer and Digital Amplifier - Black',299.99,'imagesNew/11.jpg','JBL','New',10,15,'SoundSystems'),(12,'JBL - 3.1-Channel Soundbar System with 10\" Wireless Subwoofer and Digital Amplifier - Black',499.99,'imagesNew/12.jpg','JBL','New',10,15,'SoundSystems'),(13,'Bose® - Wireless Surround Speakers for Home Theater (Pair) - Black',299.99,'imagesNew/13.jpg','Bose','New',10,15,'SoundSystems'),(14,'Bose® - Wave® SoundTouch® Music System IV - Black',599.99,'imagesNew/14.jpg','Bose','New',10,15,'SoundSystems'),(15,'Bose® - Soundbar 500 with Voice Control and Bass Module 500 Subwoofer Home Theater Package',499.99,'imagesNew/15.jpg','Bose','New',10,15,'SoundSystems'),(16,'Sony - MHC-M20 High-Power Audio System - Black',229.99,'imagesNew/16.jpg','Sony','New',10,15,'SoundSystems'),(17,'Sony - 2.1-Channel 320W Soundbar System with Wireless Subwoofer - Black',199.99,'imagesNew/17.jpg','Sony','New',10,15,'SoundSystems'),(18,'Sony - 2.1-Channel Soundbar System with 4.72\" Wireless Subwoofer and Digital Amplifier - Charcoal black',349.99,'imagesNew/18.jpg','Sony','New',10,15,'SoundSystems'),(19,'Apple - iPhone 11 128GB - Black',749,'imagesNew/19.jpg','Apple','New',10,15,'Phones'),(20,'Apple - iPhone 11 Pro 256GB - Space Gray',1150.99,'imagesNew/20.jpg','Apple','New',10,15,'Phones'),(21,'Apple - iPhone 11 Pro Max 256GB - Space Gray',1250,'imagesNew/21.jpg','Apple','New',10,15,'Phones'),(22,'OnePlus 6T - Midnight Black - 8 GB RAM + 128 GB Storage',499,'imagesNew/22.png','OnePlus','New',0,15,'Phones'),(23,'OnePlus 6T - Midnight Black - 8 GB RAM + 256 GB Storage',549,'imagesNew/23.png','OnePlus','New',0,15,'Phones'),(24,'OnePlus 7 Pro -Nebula Blue - 12 GB RAM + 256 GB Storage ',749,'imagesNew/24.png','OnePlus','New',0,15,'Phones'),(25,'OnePlus 7 Pro -Mirror Gray - 6 GB RAM + 128 GB Storage ',669,'imagesNew/25.png','OnePlus','New',0,15,'Phones'),(26,'Samsung - Galaxy Note10+ 256GB - Aura Glow',900,'imagesNew/26.jpg','Samsung','New',10,15,'Phones'),(27,'Samsung - Galaxy S10+ with 128GB Memory Cell Phone Prism - Black',800,'imagesNew/27.jpg','Samsung','New',10,15,'Phones'),(28,'Samsung - Galaxy S10 with 128GB Memory Cell Phone - Flamingo Pink',700,'imagesNew/28.jpg','Samsung','New',10,15,'Phones'),(29,'Apple - MacBook Air 13.3\" Laptop with Touch ID - Intel Core i5 - 8GB Memory - 128GB Solid State Drive (Latest Model) - Space Gray',899.99,'imagesNew/29.jpg','Apple','New',10,15,'Laptops'),(30,'\nApple - MacBook Pro - 13\" Display with Touch Bar - Intel Core i5 - 8GB Memory - 256GB SSD - Silver',1299.99,'imagesNew/30.jpg','Apple','New',10,15,'Laptops'),(31,'\nApple - MacBook Pro 15.4\" Display with Touch Bar - Intel Core i7 - 16GB Memory - AMD Radeon Pro 555X - 256GB SSD - Space Gray',2099.99,'imagesNew/31.jpg','Apple','New',10,15,'Laptops'),(32,'Dell - Inspiron 15.6\" 7000 2-in-1 4K Ultra HD Touch-Screen Laptop - Intel Core i7 - 16GB - GeForce MX250 - 512GB SSD + Optane - Black',1199.99,'imagesNew/32.jpg','Dell','New',10,15,'Laptops'),(33,'Dell - 15.6\" Gaming Laptop - Intel Core i7 - 16GB Memory - NVIDIA GeForce GTX 1660 Ti - 1TB Hard Drive + 256GB SSD - Deep Space Black\n',1149.99,'imagesNew/33.jpg','Dell','New',10,15,'Laptops'),(34,'Microsoft - Surface Book 2 - 13.5\" Touch-Screen PixelSense? - 2-in-1 Laptop - Intel Core i5 - 8GB Memory - 256GB SSD - Platinum',1299,'imagesNew/34.jpg','Microsoft','New',10,15,'Laptops'),(35,'Microsoft - Surface Laptop 2 13.5\" Touch-Screen - Intel Core i5 - 8GB Memory - 256GB Solid State Drive (Latest Model) - Platinum',899,'imagesNew/35.jpg','Microsoft','New',10,15,'Laptops'),(36,'Amazon - Echo Dot (3rd Gen) - Smart Speaker with Alexa - Charcoal',49.99,'imagesNew/36.jpg','Amazon','New',10,15,'VoiceAssistant'),(37,'Amazon - Echo (2nd Gen) - Smart Speaker with Alexa - Charcoal Fabric',99.99,'imagesNew/37.jpg','Amazon','New',10,15,'VoiceAssistant'),(38,'Amazon - Echo Sub 100W Subwoofer - Charcoal',129.99,'imagesNew/38.jpg','Amazon','New',10,15,'VoiceAssistant'),(39,'Google - Home Mini - Smart Speaker with Google Assistant - Chalk',49,'imagesNew/39.jpg','Google','New',10,15,'VoiceAssistant'),(40,'Google - Home - Smart Speaker with Google Assistant - White/Slate',99.99,'imagesNew/40.jpg','Google','New',10,15,'VoiceAssistant'),(41,'Fitbit - Charge 3 Activity Tracker + Heart Rate - Black/Graphite',149.95,'imagesNew/41.jpg','Fitbit','New',10,15,'FitnessWatches'),(42,'Fitbit - Inspire HR Activity Tracker + Heart Rate - Black',99.95,'imagesNew/42.jpg','Fitbit','New',10,15,'FitnessWatches'),(43,'Fitbit - Charge 2 Activity Tracker + Heart Rate (Large) - Black Silver',129.95,'imagesNew/43.jpg','Fitbit','New',10,15,'FitnessWatches'),(44,'Fossil - Gen 5 44mm Stainless Steel - Black with Brown Leather Band',295,'imagesNew/44.jpg','Fossil','New',10,15,'FitnessWatches'),(45,'Fossil - Gen 5 44mm Stainless Steel - Smoke with Smoke Stainless Steel Band',295,'imagesNew/45.jpg','Fossil','New',10,15,'FitnessWatches'),(46,'Apple - Apple Watch Series 5 (GPS) 44mm Space Gray Aluminum Case with Black Sport Band - Space Gray Aluminum',429,'imagesNew/46.jpg','Apple','New',10,15,'SmartWatches'),(47,'Apple - Apple Watch Series 3 (GPS) 38mm Silver Aluminum Case with White Sport Band - Silver Aluminum',199,'imagesNew/47.jpg','Apple','New',10,15,'SmartWatches'),(48,'Apple - Apple Watch Series 4 (GPS) 44mm Space Gray Aluminum Case with Black Sport Band - Space Gray Aluminum',379,'imagesNew/48.jpg','Apple','New',10,15,'SmartWatches'),(49,'Samsung - Gear S3 Frontier Smartwatch 46mm - Dark Gray',299.99,'imagesNew/49.jpg','Samsung','New',10,15,'SmartWatches'),(50,'Samsung - Galaxy Watch Smartwatch 42mm Stainless Steel - Midnight Black',329.99,'imagesNew/50.jpg','Samsung','New',10,15,'SmartWatches'),(51,'Skullcandy - Crusher Wireless Over-the-Ear Headphones - Black/Coral',118.99,'imagesNew/51.jpg','Skullcandy','New',10,15,'Headphones'),(52,'Skullcandy - Venue Wireless Noise Canceling Over-the-Ear Headphones - Black',149.99,'imagesNew/52.jpg','Skullcandy','New',10,15,'Headphones'),(53,'Skullcandy - Venue Wireless Noise Canceling Over-the-Ear Headphones - Black',52.99,'imagesNew/53.jpg','Skullcandy','New',10,15,'Headphones'),(54,'Sennheiser - HD 4.50 BTNC Wireless Over-the-Ear Noise Canceling Headphones - Black',149.99,'imagesNew/54.jpg','Sennheiser','New',10,15,'Headphones'),(55,'Sennheiser - PXC 550 Wireless Over-the-Ear Noise Canceling Headphones - Black',242.99,'imagesNew/55.jpg','Sennheiser','New',10,15,'Headphones'),(56,'Tmobile - Basic',19.99,'imagesNew/56.png','Basic','New',10,15,'Wirelessplan'),(57,'Verizon- Basic',24.99,'imagesNew/57.png','Basic','New',10,15,'Wirelessplan'),(58,'Tmobile - Premium',29.99,'imagesNew/56.png','Premium','New',10,15,'Wirelessplan'),(59,'Verizon - Premium',34.99,'imagesNew/57.png','Premium','New',10,15,'Wirelessplan'),(60,'Tmobile - Ultimate',39.99,'imagesNew/56.png','Ultimate','New',10,15,'Wirelessplan'),(61,'Verizon - Ultimate',44.99,'imagesNew/57.png','Ultimate','New',10,15,'Wirelessplan'),(101,'Screen Cleaner Kit',19.95,'imagesNew/101.jpg','Screenmom',NULL,0,20,'Accessory'),(102,'Premium Series Advanced Tilt TV Wall Mount',149.99,'imagesNew/102.jpg','Sanus',NULL,0,20,'Accessory'),(103,'3-Device Universal Remote',19.99,'imagesNew/103.jpg','RCA',NULL,0,20,'Accessory'),(104,'4 Pack - LG Cinema 3D Glasses',35.99,'imagesNew/104.jpg','LG',NULL,0,20,'Accessory'),(105,'Outdoor TV Cover for LCD and LED',23.95,'imagesNew/105.jpg','Kolife',NULL,0,20,'Accessory'),(106,'PERLESMITH Universal TV Stand',28.99,'imagesNew/106.jpg','Perlesmith',NULL,0,20,'Accessory'),(107,'Soundbar Wall Bracket - Black',39.99,'imagesNew/107.jpg','Bose',NULL,0,20,'Accessory'),(108,'Solo 5 Soundbar Wall mount kit - Black',24.99,'imagesNew/108.jpg','Bose',NULL,0,20,'Accessory'),(109,'Insignia - 6 3.5mm Mini-to-RCA Stereo Audio Cable - Black',11.99,'imagesNew/109.jpg','Insignia',NULL,0,20,'Accessory'),(110,'Rocketfis - 24 In-Wall Subwoofer Cable - Gray',38.99,'imagesNew/110.jpg','Rocketfish',NULL,0,20,'Accessory'),(111,'Rocketfish - Tilting Wall Mounts for Most Small Speakers (2-Pack) - Black',24.99,'imagesNew/111.jpg','Rocketfish',NULL,0,20,'Accessory'),(112,'Rocketfish - Multi-Directional Speaker Wall Mount - Black',39.99,'imagesNew/112.jpg','Rocketfish',NULL,0,20,'Accessory'),(113,'Speck - Presidio STAY CLEAR Case for Apple® iPhone® 11 - Clear',39.99,'imagesNew/11.jpg','Speck',NULL,0,20,'Accessory'),(114,'Anker - PowerCore 20,000 mAh Portable Charger for Most USB-Enabled Devices - Black',44.99,'imagesNew/114.jpg','Anker',NULL,0,20,'Accessory'),(115,'PopSockets - Car Holder for Mobile Phones - Black',14.99,'imagesNew/115.jpg','PopSockets',NULL,0,20,'Accessory'),(116,'PopSockets - PopGrip - Aluminum Batic Blue',9.99,'imagesNew/116.jpg','PopSockets',NULL,0,20,'Accessory'),(117,'Insignia - Phone Ring Stand Finger Grip/Kickstand for Mobile Phones - Black',9.99,'imagesNew/117.jpg','Insignia',NULL,0,20,'Accessory'),(118,'kate spade new york - Universal Stability Ring - Gold/Cream',19.99,'imagesNew/118.jpg','Kate',NULL,0,20,'Accessory'),(119,'Case Logic - Huxton Sleeve for 13.3\" Laptop - Blue/Midnight Navy',12.99,'imagesNew/119.jpg','Case Logic',NULL,0,20,'Accessory'),(120,'j5create - USB 3.0 Mini Docking Station - silver',79.99,'imagesNew/120.jpg','j5Create',NULL,0,20,'Accessory'),(121,'Solo - Urban Laptop Briefcase - Black/Orange',22.99,'imagesNew/121.jpg','Solo',NULL,0,20,'Accessory'),(122,'Targus - 65W AC Power Adapter for Select Asus, Lenovo and HP Laptops - Black',49.99,'imagesNew/122.jpg','Targus',NULL,0,20,'Accessory'),(123,'Google - Base for Google Home - Carbon',40.99,'imagesNew/123.jpg','Google',NULL,0,20,'Accessory'),(124,'Amazon - Smart Plug - White',24.99,'imagesNew/124.jpg','Amazon',NULL,0,20,'Accessory'),(125,'Fitbit Charge 2 Charger',6.99,'imagesNew/125.jpg','SoulenUSA',NULL,0,20,'Accessory'),(126,'Fossil 22 mm Leather Watch Strap - S221245',25.89,'imagesNew/126.jpg','Fossil',NULL,0,20,'Accessory'),(127,'Platinum - Magnetic Stainless Steel Mesh Band for Apple Watch? 38mm and 40mm - Black',39.99,'imagesNew/127.jpg','Platinum',NULL,0,20,'Accessory'),(128,'Samsung - 9W Wireless Charger Pad - Black',39.99,'imagesNew/57.png','Samsung',NULL,0,20,'Accessory'),(129,'Insignia - 20 Headphone Extension Cable and Adapter Kit - Black',6.49,'imagesNew/129.jpg','Insignia',NULL,0,20,'Accessory'),(130,'Insignia - Dual 3.5mm Mini Headphone Jack Splitter - Black',4.49,'imagesNew/57.png','Insignia',NULL,0,20,'Accessory'),(131,'International Calls',10.49,'imagesNew/56.png','Tmobile-International Calls',NULL,0,20,'Accessory'),(132,'International Calls',14.49,'imagesNew/57.png','Verizon-International Calls',NULL,0,20,'Accessory');
/*!40000 ALTER TABLE `productcatalog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registration`
--

DROP TABLE IF EXISTS `registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registration` (
  `userid` varchar(45) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `fname` varchar(45) DEFAULT NULL,
  `lname` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `mno` varchar(45) DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registration`
--

LOCK TABLES `registration` WRITE;
/*!40000 ALTER TABLE `registration` DISABLE KEYS */;
INSERT INTO `registration` VALUES ('a@1','a1','Arpit','Patel','apatel@gmail.com','3124805771',0),('d@1','d1','Darsh','Patel','dpatel@gmail.com','3124805772',0),('h@1','h1','Harsh','Thakkar','hthakkar@gmail.com','3124805775',0),('j@1','j1','Jay','Upadhyay','jay@gmail.com','3124805767',0),('k@1','k1','Krusha','Patel','kpatel@gmail.com','3124805770',0),('m@1','m1','Mitul','Gohil','mitul@gmail.com','3124805766',2),('p@1','p1','Parth','Patel','parth@gmail.com','3124805764',1),('r@1','r1','Rudresh','Shah','rudresh@gmail.com','3124805765',0),('s@1','s1','Shubham','Patel','spatel@gmail.com','3124805768',0),('v@1','v1','Vidhi','Amin','vamin@gmail.com','3124805769',0);
/*!40000 ALTER TABLE `registration` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-13 18:10:32
