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
-- Table structure for table `jd_chunks`
--

DROP TABLE IF EXISTS `jd_chunks`;
CREATE TABLE `jd_chunks` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(128) NOT NULL,
  `position` varchar(24) NOT NULL,
  `element` varchar(256) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jd_chunks`
--


/*!40000 ALTER TABLE `jd_chunks` DISABLE KEYS */;
LOCK TABLES `jd_chunks` WRITE;
INSERT INTO `jd_chunks` VALUES (1,'sample1','right','com.jdragon.element.CustomElementTest'),(2,'sample2','left','com.jdragon.element.CustomElementTest');
UNLOCK TABLES;
/*!40000 ALTER TABLE `jd_chunks` ENABLE KEYS */;

--
-- Table structure for table `jd_role_access`
--

DROP TABLE IF EXISTS `jd_role_access`;
CREATE TABLE `jd_role_access` (
  `id` int(11) NOT NULL auto_increment,
  `rid` int(11) NOT NULL,
  `access_token` varchar(56) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jd_role_access`
--


/*!40000 ALTER TABLE `jd_role_access` DISABLE KEYS */;
LOCK TABLES `jd_role_access` WRITE;
INSERT INTO `jd_role_access` VALUES (1,1,'testaccess');
UNLOCK TABLES;
/*!40000 ALTER TABLE `jd_role_access` ENABLE KEYS */;

--
-- Table structure for table `jd_roles`
--

DROP TABLE IF EXISTS `jd_roles`;
CREATE TABLE `jd_roles` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(56) NOT NULL,
  PRIMARY KEY  (`id`,`name`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jd_roles`
--


/*!40000 ALTER TABLE `jd_roles` DISABLE KEYS */;
LOCK TABLES `jd_roles` WRITE;
INSERT INTO `jd_roles` VALUES (1,'test');
UNLOCK TABLES;
/*!40000 ALTER TABLE `jd_roles` ENABLE KEYS */;

--
-- Table structure for table `jd_routes`
--

DROP TABLE IF EXISTS `jd_routes`;
CREATE TABLE `jd_routes` (
  `id` int(11) NOT NULL auto_increment,
  `path` varchar(256) NOT NULL,
  `element` varchar(64) NOT NULL,
  `callback` varchar(256) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jd_routes`
--


/*!40000 ALTER TABLE `jd_routes` DISABLE KEYS */;
LOCK TABLES `jd_routes` WRITE;
INSERT INTO `jd_routes` VALUES (2,'/Install','com.jdragon.system.element.JDInstall','mainContent'),(27,'/Index/%/view','com.jdragon.element.CustomElementTest','mainContent'),(28,'/Indextest','com.jdragon.element.CustomElementTest','mainContent'),(34,'/register','com.jdragon.system.element.JDAuth','register'),(35,'/login','com.jdragon.system.element.JDAuth','login'),(36,'/logout','com.jdragon.system.element.JDAuth','logout'),(40,'/admin/settings/%/edit','com.jdragon.system.element.JDSettings','settings'),(41,'/admin/settings/reload','com.jdragon.system.element.JDSettings','settings'),(42,'/admin/settings','com.jdragon.system.element.JDSettings','settings');
UNLOCK TABLES;
/*!40000 ALTER TABLE `jd_routes` ENABLE KEYS */;

--
-- Table structure for table `jd_settings`
--

DROP TABLE IF EXISTS `jd_settings`;
CREATE TABLE `jd_settings` (
  `name` varchar(48) NOT NULL,
  `value` varchar(512) NOT NULL,
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jd_settings`
--


/*!40000 ALTER TABLE `jd_settings` DISABLE KEYS */;
LOCK TABLES `jd_settings` WRITE;
INSERT INTO `jd_settings` VALUES ('tempdir','C:/temp',1),('CONTENT_DIR','E:/jdragon/WebContent',2),('DefaultTemplate','default',3);
UNLOCK TABLES;
/*!40000 ALTER TABLE `jd_settings` ENABLE KEYS */;

--
-- Table structure for table `jd_user_role`
--

DROP TABLE IF EXISTS `jd_user_role`;
CREATE TABLE `jd_user_role` (
  `id` int(11) NOT NULL auto_increment,
  `uid` int(11) NOT NULL,
  `rid` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `uid_rid_unique` (`uid`,`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jd_user_role`
--


/*!40000 ALTER TABLE `jd_user_role` DISABLE KEYS */;
LOCK TABLES `jd_user_role` WRITE;
INSERT INTO `jd_user_role` VALUES (1,2,1);
UNLOCK TABLES;
/*!40000 ALTER TABLE `jd_user_role` ENABLE KEYS */;

--
-- Table structure for table `jd_users`
--

DROP TABLE IF EXISTS `jd_users`;
CREATE TABLE `jd_users` (
  `id` int(11) NOT NULL auto_increment,
  `username` varchar(50) NOT NULL,
  `passwd` varchar(256) NOT NULL,
  `email` varchar(256) NOT NULL,
  `firstname` varchar(256) default NULL,
  `lastname` varchar(256) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `name` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jd_users`
--


/*!40000 ALTER TABLE `jd_users` DISABLE KEYS */;
LOCK TABLES `jd_users` WRITE;
INSERT INTO `jd_users` VALUES (1,'admin','6d67225ed5d73b7980d2fbf976c77e5f','admin@example.com','Administrator','Administrator'),(2,'test1','ad0234829205b9033196ba818f7a872b','fdas@example.com','test1','test1'),(3,'test3','8ad8757baa8564dc136c1e07507f4a98','test3@example.com',NULL,NULL);
UNLOCK TABLES;
/*!40000 ALTER TABLE `jd_users` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

