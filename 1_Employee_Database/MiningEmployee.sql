/*!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19  Distrib 10.6.18-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: MiningEmployee
-- ------------------------------------------------------
-- Server version	10.6.18-MariaDB-0ubuntu0.22.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Department`
--

DROP TABLE IF EXISTS `Department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Department` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `department` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Department`
--

LOCK TABLES `Department` WRITE;
/*!40000 ALTER TABLE `Department` DISABLE KEYS */;
INSERT INTO `Department` VALUES (1,'MANAGEMENT'),(2,'DRILL & BLAST'),(3,'SHE'),(4,'PRODUCTION'),(5,'ENGINEERING'),(6,'HRGA'),(7,'MAINTENANCE'),(8,'LOGISTIC'),(9,'TC');
/*!40000 ALTER TABLE `Department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Position`
--

DROP TABLE IF EXISTS `Position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Position` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `position` varchar(80) NOT NULL,
  `iddept` smallint(5) unsigned NOT NULL,
  `staff` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  KEY `iddept` (`iddept`),
  CONSTRAINT `Position_ibfk_1` FOREIGN KEY (`iddept`) REFERENCES `Department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Position`
--

LOCK TABLES `Position` WRITE;
/*!40000 ALTER TABLE `Position` DISABLE KEYS */;
INSERT INTO `Position` VALUES (1,'SITE OPERATION HEAD',1,1),(2,'DSOH OPERATION',1,1),(3,'DSOH PLANT',1,1),(4,'DRILL BLAST DH',2,1),(5,'ENGINEERING DH',5,1),(6,'GA / IR DH',6,1),(7,'HR TC DH',9,1),(8,'HRGA DH',6,1),(9,'LOGISTIC DH',8,1),(10,'MAINTENANCE DH',7,1),(11,'PRODUCTION DH',4,1),(12,'SHE DH',3,1),(13,'ACT. ENGINEERING DH',5,1),(14,'PRODUCTION DEPUTY DH',4,1),(19,'DRILL BLAST SH',2,1),(20,'ENGINEERING SH',5,1),(21,'FA SH',6,1),(22,'GA SH',6,1),(23,'HR SH',6,1),(24,'INDUSTRIAL RELATIONS SH',6,1),(25,'LOGISTICS PROC SH',8,1),(26,'LOGISTICS WHI SH',8,1),(27,'MAINTENANCE PLANNER SH',7,1),(28,'MAINTENANCE SH',7,1),(29,'PRODUCTION SH',4,1),(30,'SHE OPERATION SH',3,1),(31,'SHE SH',3,1),(32,'SURVEYOR SH',5,1),(33,'CIVIL SH',6,1),(34,'SHORT TERM SH',5,1),(35,'LOGISTIC SH',8,1),(36,'HAULING ROUTE MAINTENANCE SH',4,1),(37,'LONG TERM SH',5,1),(40,'CIVIL & MECHANIC GL',6,1),(41,'DRILL BLAST GL',2,1),(42,'ENGINEERING GL',5,1),(43,'ENGINEERING DATA PROCESSING GL',5,1),(44,'EXTERNAL RELATION GL',6,1),(45,'FA GL',6,1),(46,'GA GL',6,1),(47,'HR GL',6,1),(48,'HR JR. SPECIALIST',6,1),(49,'INDUSTRIAL RELATIONS GL',6,1),(50,'IT REPRESENTATIVE',6,1),(51,'IT SUPPORT GL',6,1),(52,'LOGISTIC GL',8,1),(53,'LOGISTICS PROC GL',8,1),(54,'LOGISTICS WHS GL',8,1),(55,'MAINTENANCE GL',7,1),(56,'OH & IH GL',3,1),(57,'PRODUCTION GL',4,1),(58,'SHE GL',3,1),(59,'SHE OPERATION GL',3,1),(60,'SHE SYSTEM COMPLIANCE GL',3,1),(61,'SHORT TERM GL',5,1),(62,'SURVEY GL',5,1),(63,'TC GL',9,1),(64,'TRANSPORTATION GL',6,1),(65,'TYRE GL',7,1),(66,'WELDER GL',7,1),(67,'MAINTENANCE PLANNER GL',7,1),(68,'LONG TERM GL',5,1),(73,'BLASTING ENGINEER',2,1),(74,'ENGINEERING DATA PROCESSING OFFICER',5,1),(75,'ENGINEERING OFFICER',5,1),(76,'FA OFFICER',6,1),(77,'FOOD & BEVERAGE OFFICER',6,1),(78,'GA OFFICER',6,1),(79,'GA TECHNICIAN',6,1),(80,'HR OFFICER',6,1),(81,'INSTRUKTUR MECHANIC',9,1),(82,'IT SUPPORT',6,1),(83,'JR. INSTRUKTUR OPERATOR',9,1),(84,'MAINTENANCE ENGINEER',7,1),(85,'MAINTENANCE OFFICER',7,1),(86,'MAINTENANCE PLANNER',7,1),(87,'MAINTENANCE TECHNICAL OFFICER',7,1),(88,'MINING EDP',5,1),(89,'PARAMEDIC',3,1),(90,'PROC WHI OFFICER',8,1),(91,'PRODUCTION OFFICER',4,1),(92,'RECRUITMENT',6,1),(93,'SHE OFFICER',3,1),(94,'SURVEY OFFICER',5,1),(95,'TRANSPORTATION OFFICER',6,1),(96,'TYREMAN',7,0),(97,'TRAININER MASTER OPERATION',9,1),(98,'TRAININER MECHANICAL',9,1),(107,'PENGAWAS PENIMBUNAN',4,0),(108,'DRILL BLAST ADM',2,0),(109,'GA ADM',6,1),(110,'HAULING ADM',4,0),(111,'LOGISTIC ADM',8,0),(112,'LOGISTICS PROC ADM',8,0),(113,'LOGISTICS WHS ADM',8,0),(114,'MAINTENANCE ADM',7,0),(115,'PRODUCTION ADM',4,0),(116,'SHE ADM',3,0),(117,'TRAINING CENTER ADM',9,0),(118,'PRODUCTION ODP',4,1),(123,'CHECKER',4,0),(124,'MECHANIC G-1',7,0),(125,'MECHANIC G-2',7,0),(126,'MECHANIC G-3',7,0),(127,'PRA MECHANIC',7,0),(128,'SERVICEMAN',7,0),(129,'WELDER',7,0),(130,'TRANSKON',7,0),(131,'CARPENTER',6,0),(132,'OPERATOR CRANE TRUCK',7,0),(133,'OPERATOR D 155',4,0),(134,'OPERATOR D 85',4,0),(135,'OPERATOR GD 705',4,0),(136,'OPERATOR GD 825',4,0),(137,'OPERATOR HD 465',4,0),(138,'OPERATOR HD 785',4,0),(139,'OPERATOR PC 1250',4,0),(140,'OPERATOR PC 2000',4,0),(141,'OPERATOR PC 400',4,0),(142,'DRIVER DUMP TRUCK',4,0),(143,'DRIVER FUEL TRUCK',8,0),(144,'DRIVER SARANA',6,0),(145,'DRIVER WATER TRUCK',4,0),(146,'HELPER SURVEY',5,0),(147,'HELPER GA',6,0),(148,'HELPER LOGISTIC (FUELMAN)',8,0),(149,'HELPER MAINTENANCE',7,0),(150,'HELPER PRODUCTION',4,0),(151,'HELPER SAFETY & ENVIRO',3,0),(152,'HELPER SUPPORT (OFFICE BOY)',6,0),(153,'HELPER DRILL BLAST',2,0),(154,'PIT SERVICE CREW',4,0);
/*!40000 ALTER TABLE `Position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TK`
--

DROP TABLE IF EXISTS `TK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TK` (
  `NIK` int(10) unsigned NOT NULL,
  `Fullname` varchar(40) NOT NULL,
  `IDPosition` smallint(5) unsigned NOT NULL,
  `IDDepartment` smallint(5) unsigned NOT NULL,
  `SID` varchar(5) DEFAULT NULL,
  `Status` enum('hired','terminate') NOT NULL,
  PRIMARY KEY (`NIK`),
  KEY `IDPosition` (`IDPosition`),
  KEY `IDDepartment` (`IDDepartment`),
  CONSTRAINT `TK_ibfk_1` FOREIGN KEY (`IDPosition`) REFERENCES `Position` (`id`),
  CONSTRAINT `TK_ibfk_2` FOREIGN KEY (`IDDepartment`) REFERENCES `Department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TK`
--

LOCK TABLES `TK` WRITE;
/*!40000 ALTER TABLE `TK` DISABLE KEYS */;
INSERT INTO `TK` VALUES (88180008,'Sandra Wicaksono',118,4,'ABCDE','terminate'),(91070481,'Agus Ariyanto',55,7,'CBKB4','terminate'),(91080821,'I Wayan Sumertanadi',9,8,'SBFLD','hired'),(91080823,'Wayan Anang Ardana',3,1,'S6LXE','hired'),(91080836,'Eko Hadi Saputro',8,6,'XFBIC','hired'),(91080858,'Ois Jati Setiyawan',33,6,'ABCDE','terminate'),(91089010,'Hasan Fadhur Rozi',27,7,'GB0O0','hired'),(91091059,'Eko Mega Prasetiya',36,4,'ABCDE','hired'),(91091196,'Ibnu Muthi Zamawi',3,1,'ABCDE','terminate'),(91091504,'Arif Tri Kisetyanto',4,2,'DNI1O','terminate'),(91094021,'Christianus Sunardi',1,1,'B0UK0','hired'),(91095025,'Fitria Mahardika',28,7,'PEIY6','hired'),(91101609,'Khusnul Afandi',11,4,'LR6GN','hired'),(91102126,'Adi Candra',81,9,'CYTTU','hired'),(91102158,'Dwi Agus Setiawan',55,7,'ABCDE','terminate'),(91102226,'Danu Fitrianto',50,6,'NQTM7','hired'),(91102323,'Muhammad Saugi',11,4,'ABCDE','terminate'),(91102332,'Fery Sudharta',35,8,'PR0YN','terminate'),(91112367,'Heri Purnomo',12,3,'Q80FP','hired'),(91112445,'Robinson Sianturi',37,5,'ABCDE','terminate'),(91112546,'Bayu Laksmono Putrantomo',22,6,'IOM9K','hired'),(91112620,'Ibnu Putra',37,5,'ABCDE','terminate'),(91112668,'Priyo Prasojo',29,4,'R2FQE','terminate'),(91112832,'Edi Mulyono',2,1,'TZE6V','terminate'),(91112833,'Didik Suprayogi',14,4,'ABCDE','terminate'),(91112924,'Syaiful Hidayat',13,5,'CF8DO','hired'),(91112969,'Dian Ari Wahyudi',62,5,'F1NRT','hired'),(91122993,'Langgeng Bagaskoro',10,7,'347YB','terminate'),(91123390,'Christian Surya Dwi Hartanto',28,7,'3WKL1','hired'),(91123808,'Karjono',55,7,'ABCDE','terminate'),(91134057,'Soni Muhardika',55,7,'VNITA','terminate'),(91134261,'Zainudin Hasan',31,3,'ABCDE','terminate'),(91134270,'Hendri Kariyudi',19,2,'ABCDE','terminate'),(91150037,'Fitria Prasetyama',57,4,'LUYFF','terminate'),(91160020,'Arian Tiarno Setya Pribadi',52,8,'ABCDE','terminate'),(91160077,'Fendrik Romadhon',46,6,'BWEMW','hired'),(91160110,'Tahyudin',34,5,'ABCDE','terminate'),(91160135,'Azis Budiyoko',57,4,'ABCDE','terminate'),(91160139,'Hari Cahyadi',52,8,'3IJL4','terminate'),(91160257,'Ahmad Inam Mubarok',19,2,'ABCDE','terminate'),(91160287,'Atep Hamdani',57,4,'ABCDE','terminate'),(91160301,'Akhmad Royani',12,3,'ABCDE','terminate'),(91160387,'Sukardi Imron',55,7,'D6AI7','terminate'),(91160406,'Yudi Kurniawan',10,7,'ABCDE','terminate'),(91170089,'Andri Rahmanto',66,7,'Z4TWL','hired'),(91170110,'Andri Agus Dwi Saputra',55,7,'TTCW1','terminate'),(91170125,'Andi Abdillah',57,4,'ABCDE','terminate'),(91170127,'Irawan Tri Basuki',68,5,'ABCDE','hired'),(91170128,'Hendri Susanto',57,4,'ABCDE','terminate'),(91170132,'Alfan Kuncahyo',41,2,'ABCDE','terminate'),(91170134,'Herman Faslyi',48,6,'JWWD8','hired'),(91170137,'Ahmad Firmansyah',55,7,'ABCDE','terminate'),(91170180,'Achmad Jubaidi',57,4,'ABCDE','terminate'),(91180036,'Dedy Wirawan',57,4,'9P9PD','terminate'),(91180046,'Yulianto',55,7,'I9E9D','hired'),(91180052,'Andi Rahman',57,4,'ABCDE','terminate'),(91180058,'Fredy Eka Martha',57,4,'LVOC5','terminate'),(91180060,'Hendri Rofiyanto',61,5,'ABCDE','hired'),(91180064,'Agus Setiyono',55,7,'4F4TW','hired'),(91180069,'Indriyo Bayu Laksono',80,6,'QFJGT','hired'),(91180198,'M. Yanwar Fahruddin',55,7,'ABCDE','terminate'),(91180356,'Ahmad Faiq',57,4,'4L5H2','terminate'),(91180428,'Rizki Noor Wahyudi',57,4,'DGU5K','terminate'),(91180432,'Anton Subagyo',132,7,'DS3SO','hired'),(91180443,'Muhammad Yanuri',57,4,'Y12AW','terminate'),(91180470,'Muhammad Faiq',78,6,'RO8EU','terminate'),(91180532,'Tofan Kurnia Jaya',57,4,'ABCDE','terminate'),(91180534,'Agus Kurniawan',61,5,'LVFGT','hired'),(91180536,'Herin Irmawan',57,4,'B64E3','terminate'),(91180538,'Haiq Aziz Manopo',57,4,'NYUD6','hired'),(91180542,'Indra Yuana',46,6,'VH8OI','hired'),(91180546,'Dadang Dwi Adidian Sulistiono',55,7,'E7JKN','terminate'),(91180548,'Fadil Indrawan',55,7,'QUCCU','terminate'),(91180553,'Eko Galih Suprianto',57,4,'BNYDN','terminate'),(91180556,'Hendri Yana',42,5,'JM08F','hired'),(91180559,'Agus Salim',57,4,'A0FB0','terminate'),(91180560,'Mochammad Zakkiy Ardianto',118,4,'ABCDE','terminate'),(91180600,'Sugeng Haryanto',57,4,'R6BMN','terminate'),(91180637,'Imam Mustakim',75,5,'PXCJE','terminate'),(91180658,'Arif Setiyawan',41,2,'ABCDE','terminate'),(91180678,'Erwin Swasono',89,3,'4I3GB','hired'),(91180679,'Fauzi Rahman',57,4,'W5BQ7','terminate'),(91180682,'Fibriaris Crita Mariade',67,7,'HXOXC','hired'),(91180685,'Jarot Haryanto',60,3,'4VRLN','hired'),(91180688,'Alip Hariyanto',65,7,'E5QHZ','terminate'),(91180690,'Agus Trisna Raharjo',56,3,'KPDLU','hired'),(91180691,'Abdul Hamid Bin Asmai',57,4,'TUDNA','terminate'),(91180692,'Firman Rizky Bachtiar',61,5,'ABCDE','terminate'),(91180695,'Indra Gunawan',63,9,'ZCITN','hired'),(91180696,'Andik Kusbandi',57,4,'2GTTX','terminate'),(91190014,'Cecep Supriyadi',19,2,'SY2H0','terminate'),(91190017,'Bahrur Rozi Shaifudin',55,7,'VY3RH','terminate'),(91190021,'Lukman Fauzi',23,6,'0OB4D','hired'),(91190024,'Agus Nuryadi',87,7,'2MSSI','terminate'),(91190031,'Martinus Sawu',61,5,'5M7E2','hired'),(91190065,'Gempar Prambudianto Jaya Putra',82,6,'4SOAK','terminate'),(91190088,'Joko Iswanto',57,4,'D0AUN','terminate'),(91190098,'Abdul Halim',57,4,'7IIKZ','hired'),(92180243,'Sudiyawan',57,4,'ABCDE','terminate'),(93080864,'Fahri Ahmadi',55,7,'ETOGQ','terminate'),(93091168,'Erik Khamdani',29,4,'3C4MN','hired'),(93101778,'Deky Firmansyah',55,7,'ABCDE','terminate'),(93101784,'Pedut Prasetyo',55,7,'TIOI2','terminate'),(93101839,'Sigit Priyambodo',52,8,'JLRH7','hired'),(93101856,'Ariek Tri Vuliandaru',44,6,'CLMX5','hired'),(93101928,'Ahmad Faqih',29,4,'UN8CO','hired'),(93101989,'Dwi Yuniarto',36,4,'HRDPQ','terminate'),(93101990,'Sulistiyo Atmaji',29,4,'0DODR','terminate'),(93102027,'Bindan Bino Bintoro',131,6,'E7MS4','hired'),(93102078,'Agung Bayu Resdyoko',133,4,'3H8Q2','hired'),(93112385,'Hendri Lestiyono',59,3,'4CTNM','hired'),(93112951,'Abdurrahman',28,7,'L9VOS','terminate'),(93123292,'Firman Agus Prasetyo',57,4,'ABCDE','terminate'),(93123371,'Ruslan Abdul Manaf',55,7,'XL7HK','hired'),(93123461,'Syaiful Rohman',29,4,'ABCDE','terminate'),(93123598,'Dony Hermanto',96,7,'VX11Q','hired'),(93123664,'Alex Kurnia',85,7,'JQDF5','hired'),(93123785,'Wawan Toni Argo',46,6,'RTD65','terminate'),(93133913,'Samsudin',45,6,'39DLJ','hired'),(93133914,'Heri Yuli Kuncoro',57,4,'J0FHH','hired'),(93133948,'Handoko',62,5,'RUTA5','terminate'),(93133957,'Gigih Mulyono',57,4,'IXGYZ','hired'),(95160026,'Ibnu Malik Wahyudi',124,7,'FSB4P','hired'),(95160027,'Hariyono',127,7,'QV13W','hired'),(95160062,'Askuriyanto',124,7,'TJD52','hired'),(95160063,'Danang Karyanto Munadji',125,7,'CNUV1','hired'),(95160069,'Andi Radika Hadisam Nababan',125,7,'ZMDCE','hired'),(95160109,'Ahmad Hanafi',128,7,'B6DPW','hired'),(95160129,'Feri Aryanto',55,7,'NOD81','terminate'),(95160136,'Fithrix Arifiyul Ferdiansyah',124,7,'W6HSF','hired'),(95160320,'Hermanto',137,4,'Y5SI2','hired'),(95160386,'Aminadi',83,9,'EG462','hired'),(95160403,'Mahrus',52,8,'D0PMH','hired'),(95170004,'Pantun Haryanto Hutabarat',30,3,'0NCA7','hired'),(95170012,'Samsul Arifin',41,2,'FM1N3','terminate'),(95170046,'Achmad Wahyudi',96,7,'3V20W','hired'),(95170048,'Ari Budi Setiawan',57,4,'5HT5N','hired'),(95170075,'Anis Zulkarnaien',127,7,'98VZF','hired'),(95170082,'Agustono Purnomo Hadi',124,7,'7EB45','hired'),(95170126,'Ahmad Fauzi',57,4,'ABCDE','terminate'),(95170261,'Andi Herdi Ansyah',82,6,'ABCDE','terminate'),(95170270,'Fery Abdi Fatah',82,6,'ABCDE','terminate'),(95180090,'Deddy Herdiyanto',137,4,'KZJH3','hired'),(95180108,'Bambang Ali Anwar',41,2,'ABCDE','terminate'),(95180195,'Soeprianto',126,7,'F58Z7','hired'),(95180196,'Suparman',55,7,'ABCDE','terminate'),(95180222,'Puji Unggul Jauhari',57,4,'ABCDE','terminate'),(95180231,'Aldino Hadianto',126,7,'H544R','hired'),(95180258,'Ahmad Basiron',29,4,'ZU3M8','terminate'),(95180269,'Arief Ardianto',56,3,'XWFFT','hired'),(95180275,'Rian Bastian',125,7,'LIL62','hired'),(95180277,'Budiono',125,7,'LL2M9','hired'),(95180350,'Didik Herdianto',137,4,'MQOY2','hired'),(95180358,'Firman Faelani',138,4,'Z9233','hired'),(95180373,'Duwi Yugowiyono',138,4,'HUICZ','hired'),(95180384,'Dendi Yulianto',137,4,'KHTGR','hired'),(95180392,'Abdi Rahman Farikh',140,4,'BQDWE','hired'),(95180419,'Slamet Rahardi Prihadianto',19,2,'ABCDE','terminate'),(95180435,'Amir Muhsin',55,7,'JUT3Y','terminate'),(95180441,'Mochammad Amnan',135,4,'1MDGI','hired'),(95180463,'Krisna Sahwono',141,4,'GNYKR','hired'),(95180469,'Mahfudz Rusdiyanto',89,3,'Q5TY2','hired'),(95180511,'Anto Prayogi',137,4,'EM7CK','hired'),(95180545,'Ery Supriatna',55,7,'2DAF5','terminate'),(95180549,'W.J. Hariyanto',55,7,'PLPTG','terminate'),(95180630,'Hasanuddin',57,4,'ABCDE','hired'),(95180631,'Yohar Bahrus Salam',29,4,'YIRC3','terminate'),(95180672,'Deicky Natalius Manalif',57,4,'7160G','terminate'),(95190073,'Hendrik Susanto',79,6,'VXCW2','hired'),(95190082,'Chairomy Shefry',79,6,'L8MM4','hired'),(97150038,'Dodik Hendraswanto',78,6,'ABCDE','hired'),(97160015,'Imam Buhariyanto',134,4,'8X8U0','hired'),(97160017,'Edy Purwanto',137,4,'5XY0I','hired'),(97160018,'Aguk Firman Pribadi',143,8,'ZW3NZ','hired'),(97160029,'Pristyan Vendyk Kristanto',142,4,'2L6FG','hired'),(97160040,'Ardhian Sulistiyo Wicaksono',142,4,'PCFZY','hired'),(97160041,'Icuk Nugroho',142,4,'KU9LB','hired'),(97160044,'Anto Iwahyudi',133,4,'9EICA','hired'),(97160050,'Yuliantok Prastyo',148,8,'K1BTW','hired'),(97160051,'Warno',43,5,'T1QJW','hired'),(97160053,'Ajun Prayitno',146,5,'NHTXK','terminate'),(97160055,'Dinah Purnomo',144,6,'EF42O','hired'),(97160071,'Adiat Bambang Slamet',152,6,'MCHFX','hired'),(97160088,'Doni Arista Budi',94,5,'GKES3','hired'),(97160098,'Fariyz Adi Nugroho',144,6,'WX81E','hired'),(97160099,'Imam Basorin',94,5,'58KHX','hired'),(97160104,'Dodiek Adhianto',74,5,'4YHUF','hired'),(97160141,'Hendro',123,4,'YH64K','terminate'),(97160146,'Tutut Agung Pranyoto',133,4,'8EFSQ','hired'),(97160153,'Dwi Rendra Puji Yuniarko',123,4,'AVC5T','hired'),(97160156,'Nurdiansyah Ellisabet',136,4,'AED62','hired'),(97160159,'Agung Damba Pamungkas',153,2,'N223P','terminate'),(97160160,'Catur Adi Nugroho',153,2,'ABCDE','terminate'),(97160161,'Teguh Hadi Wijaya',153,2,'9X9V8','terminate'),(97160164,'Dadang Sumarna',139,4,'YDIWI','hired'),(97160165,'Iftachul Fauzin',78,6,'P86AO','hired'),(97160175,'Ashad Eko Mashobib',133,4,'LLK3O','hired'),(97160200,'Teguh Arius Ferdiansah',138,4,'JRU15','hired'),(97160204,'Catur Panji Sabugo',142,4,'MD6JQ','hired'),(97160220,'Prasetyo Agung Pambudhi',142,4,'7DMGP','hired'),(97160221,'Andang Triawan',148,8,'D0XZM','hired'),(97160222,'Abdul Azis',107,4,'E0E31','hired'),(97160234,'Achmad Faharudin',107,4,'8T8QY','hired'),(97160236,'Andi Suharyoko',123,4,'BZN48','hired'),(97160237,'Eko Hari Cahyono',123,4,'XXYKY','hired'),(97160242,'Harseno Yudhono',148,8,'DW08C','hired'),(97160248,'Abu Sairi',153,2,'ABCDE','terminate'),(97160250,'Nur Wahib',146,5,'Q4ZEQ','hired'),(97160251,'Dony Purwanto',150,4,'ABCDE','terminate'),(97160254,'Khoirunnas Budiman',154,4,'S8B5Q','terminate'),(97160264,'Ibnu Hidayat',147,6,'DHOGO','hired'),(97160290,'Alen Argianto',141,4,'4RKP7','hired'),(97160296,'Ahmad Isnaeni',149,7,'B2BJA','hired'),(97160302,'Abdul Munib',57,4,'X08PW','hired'),(97160310,'Haris Andi Wijaya',142,4,'JGNEX','hired'),(97160315,'Fajar Edi',75,5,'ABCDE','hired'),(97160316,'Walterius Paulus Puran Radho',75,5,'SEZ13','hired'),(97160334,'Anang Agus Efendi',134,4,'Z5SOT','hired'),(97160339,'Agung Rakhmadyo Putro',141,4,'0TC62','hired'),(97160373,'Ika Udy Antoro',141,4,'OWAL0','hired'),(97160424,'Eka Apriyanto',131,6,'957IX','hired'),(97160434,'Mohammad Arifianto',141,4,'K5957','hired'),(97170002,'Heru Joko Waskito',149,7,'7RINI','hired'),(97170013,'Andika Sukma Affandi',128,7,'F4200','hired'),(97170015,'Akhmad',146,5,'H44M8','terminate'),(97170016,'Srihadi Eko Yuwono',111,8,'2L4KD','hired'),(97170020,'David Andi Harianto',142,4,'WKEHJ','hired'),(97170026,'Sinung Mujiono',150,4,'DGDK1','terminate'),(97170027,'Dedy Hari Kurniawan',107,4,'S0JD7','hired'),(97170047,'Bambang Dwi Setiyo Samudro',137,4,'UYGWS','hired'),(97170074,'Alex Abdurrahman',150,4,'5CZ6H','hired'),(97170085,'Ahmad Mustholik',93,3,'PW31G','hired'),(97170090,'Ahmad Alfan Khoiriyanto',150,4,'USFBP','hired'),(97170091,'Muh. Gunteja Wira Prahara',91,4,'5T4Z8','hired'),(97170092,'Hendro Setio Budi',132,7,'SFHKW','hired'),(97170095,'Sugianto',137,4,'PHQ5V','hired'),(97170113,'Ahmad Yusuf Muzaki',129,7,'6O3YU','hired'),(97170119,'Ifan Yusuf Maulana',80,6,'SBJLG','hired'),(97170120,'Juli Riamoeryanto',137,4,'HGJRV','hired'),(97170141,'Deny Nurdiyansah',141,4,'ERP9R','hired'),(97170144,'Ahmad Faruq Iqbal',134,4,'SO92W','hired'),(97170166,'Mochamad Abdul Razak Asyakur',137,4,'82BYE','hired'),(97170169,'Achmat Fatoni',141,4,'KE5SW','hired'),(97170172,'Ryan Nugroho',142,4,'YP95W','hired'),(97170173,'Henu Suprapta',134,4,'ZWG44','hired'),(97170182,'Yunus Isnaini Jamil',124,7,'D4RV3','hired'),(97170184,'Taufik Anshori',41,2,'Y16SM','terminate'),(97170193,'Eko Setiyawan',145,4,'KM4MD','hired'),(97170194,'Ferdinandus Malo Noe',75,5,'Q8MIY','hired'),(97170195,'Puriyanto',28,7,'ABCDE','terminate'),(97170273,'Handy Istianto',133,4,'JXMZ5','hired'),(97170274,'Abdul Azis Musafra',145,4,'PLGC0','hired'),(97170279,'Ariful Fajri',129,7,'3DEV3','hired'),(97180050,'Ainul Walid',135,4,'LUTTN','hired'),(97180055,'Amirul Yaqin',146,5,'ABCDE','terminate'),(97180057,'Albar',127,7,'MSTCC','hired'),(97180073,'Abdi Kuspriyantono',139,4,'I5E69','hired'),(97180101,'Gutiawan Dwi Yusuf',146,5,'9SH0V','hired'),(97180102,'Rohmanu',75,5,'8PRNK','terminate'),(97180118,'Frenki Yulianto',141,4,'FNBZG','hired'),(97180123,'Andi Rohman',133,4,'SBJEN','hired'),(97180131,'Billy Gotama',134,4,'F1X7C','hired'),(97180140,'Abdul Hamid Bin Moh. Hasim',134,4,'EVSLG','hired'),(97180141,'Edy Siswanto',148,8,'4700N','hired'),(97180143,'Ujang Tri Cahyono',139,4,'P4ROT','hired'),(97180185,'Dhany Sabri',142,4,'WLA9G','hired'),(97180232,'Achmad Khusyairi',151,3,'JVVF2','hired'),(97180237,'Joko Sugiharto',133,4,'PWNVZ','hired'),(97180327,'Dhani Apri Sidharta',141,4,'LLFCZ','hired'),(97180359,'Tony Setiabudi',141,4,'PFZ9G','hired'),(97180413,'Harris Mahesa',143,8,'J3QEP','hired'),(97180464,'Aris Pramono',146,5,'C2BY0','hired'),(97180488,'Sayogo Longgar Saputra',145,4,'5J8NQ','hired'),(97180676,'Ferdiyan Andriyanto',141,4,'I3A0I','hired'),(97190083,'Gaguk Setyo Wibowo',57,4,'I02K2','terminate'),(97190162,'Erik Krisdianto',146,5,'VWQ3A','terminate');
/*!40000 ALTER TABLE `TK` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-15 22:00:38
