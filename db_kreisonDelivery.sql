-- MySQL dump 10.13  Distrib 8.0.18, for Linux (x86_64)
--
-- Host: localhost    Database: mysql
-- ------------------------------------------------------
-- Server version	8.0.18-google

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
-- Current Database: `db_kreison_delivery`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `db_kreison_delivery` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `db_kreison_delivery`;

--
-- Table structure for table `db_pedido`
--

DROP TABLE IF EXISTS `db_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_pedido` (
  `id_pedido` int(11) NOT NULL AUTO_INCREMENT,
  `especiProduto` varchar(200) NOT NULL,
  `nomeEntregador` varchar(200) NOT NULL,
  `enderEntrega` varchar(200) NOT NULL,
  `nomeCliente` varchar(200) NOT NULL,
  `bairro` varchar(200) NOT NULL,
  `cep` int(11) NOT NULL,
  `numero` int(11) NOT NULL,
  `quantidade` int(11) NOT NULL,
  `data_entrega` datetime NOT NULL,
  PRIMARY KEY (`id_pedido`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_pedido`
--

LOCK TABLES `db_pedido` WRITE;
/*!40000 ALTER TABLE `db_pedido` DISABLE KEYS */;
INSERT INTO `db_pedido` VALUES (90,'idaia','funcionario ','trtrtrt','fdsgdsgds','dfsgfsdgfsd',11111111,111111,50,'2021-04-02 00:00:00'),(91,'idaia','funcionario ','dfgdfsgfsdg','dfgdfs','fdsgfdsg',3333333,333333,25,'2021-04-02 00:00:00'),(92,'idaia','funcionario ','dqwedwq','dwqdqwd','qdqw',2222222,222222,20,'2021-04-02 00:00:00'),(94,'Teste','funcionario ','dasfsda','fdasf','as',333,333333,40,'2021-04-03 00:00:00'),(95,'Teste','funcionario ','sadfsda','fasdf','dasfdsaf',33333333,333333,6,'2021-04-07 22:29:16'),(96,'idaia','funcionario ','khsjkdahskjdsa','fsdfdsfds','Centro',22222222,222222,2,'2021-04-02 21:29:36'),(97,'Teste','funcionario ','hdj asfhdasjkfhdksafa','dsafdasfdas','dsfasfasdfasdf',22222222,222222,50,'2021-04-02 21:35:18');
/*!40000 ALTER TABLE `db_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_produto`
--

DROP TABLE IF EXISTS `db_produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_produto` (
  `id_produto` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(200) NOT NULL,
  `preco` float NOT NULL,
  `quantidade` int(11) NOT NULL,
  `venda` int(11) NOT NULL,
  PRIMARY KEY (`id_produto`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_produto`
--

LOCK TABLES `db_produto` WRITE;
/*!40000 ALTER TABLE `db_produto` DISABLE KEYS */;
INSERT INTO `db_produto` VALUES (41,'Teste',5.5,50,270),(43,'idaia',7.5,0,100);
/*!40000 ALTER TABLE `db_produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_usuario`
--

DROP TABLE IF EXISTS `db_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_usuario` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(200) NOT NULL,
  `cpf` varchar(30) NOT NULL,
  `data_nasc` varchar(15) NOT NULL,
  `senha` varchar(300) NOT NULL,
  `tipo_usuario` smallint(6) NOT NULL,
  `codAdmin` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_usuario`
--

LOCK TABLES `db_usuario` WRITE;
/*!40000 ALTER TABLE `db_usuario` DISABLE KEYS */;
INSERT INTO `db_usuario` VALUES (1,'Admin','07765328557','2021-03-01','bcb15f821479b4d5772bd0ca866c00ad5f926e3580720659cc80d39c9d09802a',1,232401),(2,'Kreison','99272624002','2021-03-16','bcb15f821479b4d5772bd0ca866c00ad5f926e3580720659cc80d39c9d09802a',1,232401),(3,'funcionario ','80389061018','2021-03-24','4cc8f4d609b717356701c57a03e737e5ac8fe885da8c7163d3de47e01849c635',2,NULL);
/*!40000 ALTER TABLE `db_usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-03  3:51:42
