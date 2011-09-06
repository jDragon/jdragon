-- MySQL dump 10.10
--
-- Host: localhost    Database: jd
-- ------------------------------------------------------
-- Server version	5.0.18-nt

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `jd_routes`
--

DROP TABLE IF EXISTS `jd_routes`;
CREATE TABLE `jd_routes` (
  `id` int(11) NOT NULL auto_increment,
  `path` varchar(256) NOT NULL,
  `ingredient` varchar(64) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jd_routes`
--


/*!40000 ALTER TABLE `jd_routes` DISABLE KEYS */;
LOCK TABLES `jd_routes` WRITE;
INSERT INTO `jd_routes` VALUES (2,'/Install/%','com.jdragon.system.Installer'),(5,'/login','com.jdragon.system.element.JDAuth'),(6,'/Index/%/view','com.jdragon.element.CustomElementTest'),(7,'/Indextest','com.jdragon.element.CustomElementTest'),(8,'/logout','com.jdragon.system.element.JDAuth'),(9,'/admin/settings','com.jdragon.system.element.JDSettings'),(10,'/admin/settings/reload','com.jdragon.system.element.JDSettings');
UNLOCK TABLES;
/*!40000 ALTER TABLE `jd_routes` ENABLE KEYS */;

--
-- Table structure for table `jd_seasonings`
--

DROP TABLE IF EXISTS `jd_seasonings`;
CREATE TABLE `jd_seasonings` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(128) NOT NULL,
  `ingredient` varchar(256) NOT NULL,
  `position` varchar(24) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jd_seasonings`
--


/*!40000 ALTER TABLE `jd_seasonings` DISABLE KEYS */;
LOCK TABLES `jd_seasonings` WRITE;
INSERT INTO `jd_seasonings` VALUES (1,'sample1','com.jdragon.element.CustomElementTest','right'),(2,'sample2','com.jdragon.element.CustomElementTest','left');
UNLOCK TABLES;
/*!40000 ALTER TABLE `jd_seasonings` ENABLE KEYS */;

--
-- Table structure for table `jd_settings`
--

DROP TABLE IF EXISTS `jd_settings`;
CREATE TABLE `jd_settings` (
  `name` varchar(48) default NULL,
  `value` varchar(512) default NULL,
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jd_settings`
--


/*!40000 ALTER TABLE `jd_settings` DISABLE KEYS */;
LOCK TABLES `jd_settings` WRITE;
INSERT INTO `jd_settings` VALUES ('tempdir','C:/temp'),('CONTENT_DIR','E:/jdragon/WebContent'),('DefaultTemplate','default');
UNLOCK TABLES;
/*!40000 ALTER TABLE `jd_settings` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

