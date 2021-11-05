-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.27 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para health_dashboard
DROP DATABASE IF EXISTS `health_dashboard`;
CREATE DATABASE IF NOT EXISTS `health_dashboard` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `health_dashboard`;

-- Volcando estructura para tabla health_dashboard.cities
DROP TABLE IF EXISTS `cities`;
CREATE TABLE IF NOT EXISTS `cities` (
  `id_city` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `id_postal_code` int DEFAULT NULL,
  `id_district` int DEFAULT NULL,
  PRIMARY KEY (`id_city`),
  KEY `id_district__FK` (`id_district`),
  KEY `id_postal_code__FK` (`id_postal_code`),
  CONSTRAINT `id_district__FK` FOREIGN KEY (`id_district`) REFERENCES `districts` (`id_district`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_postal_code__FK` FOREIGN KEY (`id_postal_code`) REFERENCES `postal_codes` (`id_postal_code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- Volcando datos para la tabla health_dashboard.cities: ~5 rows (aproximadamente)
/*!40000 ALTER TABLE `cities` DISABLE KEYS */;
INSERT INTO `cities` (`id_city`, `name`, `id_postal_code`, `id_district`) VALUES
	(1, 'Tumbes', 1, 1),
	(3, 'Tumbes', 1, 2),
	(4, 'Tumbes', 1, 3),
	(11, 'Arequipa', 2, 4),
	(12, 'Arequipa', 2, 5);
/*!40000 ALTER TABLE `cities` ENABLE KEYS */;

-- Volcando estructura para tabla health_dashboard.countries
DROP TABLE IF EXISTS `countries`;
CREATE TABLE IF NOT EXISTS `countries` (
  `id_country` int NOT NULL AUTO_INCREMENT,
  `name` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `id_city` int DEFAULT NULL,
  PRIMARY KEY (`id_country`),
  UNIQUE KEY `countries_name_uindex` (`name`),
  KEY `id_city__fk` (`id_city`),
  CONSTRAINT `id_city__fk` FOREIGN KEY (`id_city`) REFERENCES `cities` (`id_city`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- Volcando datos para la tabla health_dashboard.countries: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `countries` DISABLE KEYS */;
INSERT INTO `countries` (`id_country`, `name`, `id_city`) VALUES
	(1, 'Perú', 1);
/*!40000 ALTER TABLE `countries` ENABLE KEYS */;

-- Volcando estructura para tabla health_dashboard.customers
DROP TABLE IF EXISTS `customers`;
CREATE TABLE IF NOT EXISTS `customers` (
  `id_customer` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `password` char(200) COLLATE utf8_bin DEFAULT NULL,
  `id_people` int DEFAULT NULL,
  PRIMARY KEY (`id_customer`),
  UNIQUE KEY `customers_user_name_uindex` (`user_name`),
  KEY `id_people__fk2` (`id_people`),
  CONSTRAINT `id_people__fk2` FOREIGN KEY (`id_people`) REFERENCES `peoples` (`id_people`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- Volcando datos para la tabla health_dashboard.customers: ~5 rows (aproximadamente)
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` (`id_customer`, `user_name`, `password`, `id_people`) VALUES
	(2, 'mariaperez2', '123456', 3),
	(3, 'oscarzegarra3', '123456', 5),
	(4, 'luciariquelme4', '123456', 9),
	(5, 'ximenaguzman5', '123456', 10),
	(6, 'elymejia6', '123456', 11);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;

-- Volcando estructura para tabla health_dashboard.districts
DROP TABLE IF EXISTS `districts`;
CREATE TABLE IF NOT EXISTS `districts` (
  `id_district` int NOT NULL AUTO_INCREMENT,
  `name` char(250) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id_district`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- Volcando datos para la tabla health_dashboard.districts: ~5 rows (aproximadamente)
/*!40000 ALTER TABLE `districts` DISABLE KEYS */;
INSERT INTO `districts` (`id_district`, `name`) VALUES
	(1, 'San Jacinto'),
	(2, 'Corrales'),
	(3, 'Tumbes'),
	(4, 'Cayma'),
	(5, 'La Joya');
/*!40000 ALTER TABLE `districts` ENABLE KEYS */;

-- Volcando estructura para tabla health_dashboard.peoples
DROP TABLE IF EXISTS `peoples`;
CREATE TABLE IF NOT EXISTS `peoples` (
  `id_people` int NOT NULL AUTO_INCREMENT,
  `dni` char(20) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `last_name` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `mail` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `phone` char(20) COLLATE utf8_bin DEFAULT NULL,
  `url_img_profile` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `birth-date` date DEFAULT NULL,
  `id_country` int DEFAULT NULL,
  PRIMARY KEY (`id_people`),
  UNIQUE KEY `peoples_dni_uindex` (`dni`),
  UNIQUE KEY `peoples_mail_uindex` (`mail`),
  UNIQUE KEY `peoples_phone_uindex` (`phone`),
  UNIQUE KEY `peoples_url_img_profile_uindex` (`url_img_profile`),
  KEY `id_country__FK` (`id_country`),
  CONSTRAINT `id_country__FK` FOREIGN KEY (`id_country`) REFERENCES `countries` (`id_country`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- Volcando datos para la tabla health_dashboard.peoples: ~10 rows (aproximadamente)
/*!40000 ALTER TABLE `peoples` DISABLE KEYS */;
INSERT INTO `peoples` (`id_people`, `dni`, `name`, `last_name`, `mail`, `phone`, `url_img_profile`, birth_date, `id_country`) VALUES
	(1, '77777777', 'Juan', 'Pérez', 'juanp@mail.com', '965412354', NULL, '2000-01-01', 1),
	(3, '88888888', 'María', 'Pérez', 'mariap@mail.com', '965412555', NULL, '2000-02-01', 1),
	(4, '94271656', 'Raul', 'Rivas', 'raulr@mail.com', '935478427', NULL, '1950-02-01', 1),
	(5, '52956817', 'Oscar', 'Zegarra', 'oscarz@mail.com', '920372929', NULL, '1997-12-23', 1),
	(6, '25686550', 'Angel', 'Correa', 'angelc@mail.com', '948161068', NULL, '1956-10-06', 1),
	(7, '65257881', 'Luis', 'Cordova', 'luisc@mail.com', '923285769', NULL, '1985-10-07', 1),
	(8, '34052278', 'Alonso', 'Castro', 'alonsoc@mail.com', '928375667', NULL, '1999-10-21', 1),
	(9, '66666666', 'Lucia', 'Riquelme', 'luciar@mail.com', '990257576', NULL, '2000-12-02', 1),
	(10, '55555555', 'Ximena', 'Guzman', 'ximenag@mail.com', '960542208', NULL, '1995-03-12', 1),
	(11, '33333333', 'Ely', 'Mejia', 'elym@mail.com', '990345678', NULL, '1997-07-23', 1);
/*!40000 ALTER TABLE `peoples` ENABLE KEYS */;

-- Volcando estructura para tabla health_dashboard.postal_codes
DROP TABLE IF EXISTS `postal_codes`;
CREATE TABLE IF NOT EXISTS `postal_codes` (
  `id_postal_code` int NOT NULL AUTO_INCREMENT,
  `postal_code` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id_postal_code`),
  UNIQUE KEY `postal_codes_postal_code_uindex` (`postal_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- Volcando datos para la tabla health_dashboard.postal_codes: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `postal_codes` DISABLE KEYS */;
INSERT INTO `postal_codes` (`id_postal_code`, `postal_code`) VALUES
	(2, '04014'),
	(1, '24000');
/*!40000 ALTER TABLE `postal_codes` ENABLE KEYS */;

-- Volcando estructura para tabla health_dashboard.reservation_appointments
DROP TABLE IF EXISTS `reservation_appointments`;
CREATE TABLE IF NOT EXISTS `reservation_appointments` (
  `id_reservation_quote` int NOT NULL AUTO_INCREMENT,
  `id_customer` int DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  `id_type_reservations` int DEFAULT NULL,
  `id_reserved_day` int DEFAULT NULL,
  PRIMARY KEY (`id_reservation_quote`),
  KEY `id_customer__fk` (`id_customer`),
  KEY `id_type_reservations__fk` (`id_type_reservations`),
  KEY `id_user__fk` (`id_user`),
  KEY `id_reserved_day__fk` (`id_reserved_day`),
  CONSTRAINT `id_customer__fk` FOREIGN KEY (`id_customer`) REFERENCES `customers` (`id_customer`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_reserved_day__fk` FOREIGN KEY (`id_reserved_day`) REFERENCES `reserved_days` (`id_reserved_day`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_type_reservations__fk` FOREIGN KEY (`id_type_reservations`) REFERENCES `type_reservations` (`id_type_reservation`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_user__fk` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- Volcando datos para la tabla health_dashboard.reservation_appointments: ~4 rows (aproximadamente)
/*!40000 ALTER TABLE `reservation_appointments` DISABLE KEYS */;
INSERT INTO `reservation_appointments` (`id_reservation_quote`, `id_customer`, `id_user`, `id_type_reservations`, `id_reserved_day`) VALUES
	(1, 2, 1, 1, 1),
	(2, 6, 3, 5, 5),
	(3, 4, 5, 2, 4),
	(4, 3, 4, 3, 2),
	(5, 5, 6, 4, 3);
/*!40000 ALTER TABLE `reservation_appointments` ENABLE KEYS */;

-- Volcando estructura para tabla health_dashboard.reserved_days
DROP TABLE IF EXISTS `reserved_days`;
CREATE TABLE IF NOT EXISTS `reserved_days` (
  `id_reserved_day` int NOT NULL AUTO_INCREMENT,
  `reservation_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id_reserved_day`),
  UNIQUE KEY `reserved_days_reservation_date_uindex` (`reservation_date`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- Volcando datos para la tabla health_dashboard.reserved_days: ~5 rows (aproximadamente)
/*!40000 ALTER TABLE `reserved_days` DISABLE KEYS */;
INSERT INTO `reserved_days` (`id_reserved_day`, `reservation_date`) VALUES
	(2, '2021-10-27 10:00:00'),
	(1, '2021-10-28 15:00:00'),
	(3, '2021-11-05 14:30:00'),
	(4, '2021-11-10 09:30:00'),
	(5, '2021-11-15 11:00:00');
/*!40000 ALTER TABLE `reserved_days` ENABLE KEYS */;

-- Volcando estructura para tabla health_dashboard.type_reservations
DROP TABLE IF EXISTS `type_reservations`;
CREATE TABLE IF NOT EXISTS `type_reservations` (
  `id_type_reservation` int NOT NULL AUTO_INCREMENT,
  `name` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id_type_reservation`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- Volcando datos para la tabla health_dashboard.type_reservations: ~6 rows (aproximadamente)
/*!40000 ALTER TABLE `type_reservations` DISABLE KEYS */;
INSERT INTO `type_reservations` (`id_type_reservation`, `name`, `price`) VALUES
	(1, 'Dentista', 150.00),
	(2, 'Odontologia', 50.00),
	(3, 'Cardiologia', 50.00),
	(4, 'Dermatologia', 40.00),
	(5, 'Traumatologia', 40.00),
	(6, 'Oftalmologia', 50.00);
/*!40000 ALTER TABLE `type_reservations` ENABLE KEYS */;

-- Volcando estructura para tabla health_dashboard.users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id_user` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `id_people` int DEFAULT NULL,
  `id_user_rol` int DEFAULT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `doctors_user_name_uindex` (`user_name`),
  KEY `id_people__fk` (`id_people`),
  KEY `id_user_rol__fk` (`id_user_rol`),
  CONSTRAINT `id_people__fk` FOREIGN KEY (`id_people`) REFERENCES `peoples` (`id_people`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_user_rol__fk` FOREIGN KEY (`id_user_rol`) REFERENCES `user_roles` (`id_user_rol`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- Volcando datos para la tabla health_dashboard.users: ~5 rows (aproximadamente)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id_user`, `user_name`, `password`, `id_people`, `id_user_rol`) VALUES
	(1, 'juanperez01', '123456', 1, 1),
	(3, 'raulrivas03', '654321', 4, 1),
	(4, 'angelcorrea04', '123456', 6, 2),
	(5, 'luiscordova05', '123456', 7, 2),
	(6, 'alonsocastro06', '123456', 8, 1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

-- Volcando estructura para tabla health_dashboard.user_roles
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE IF NOT EXISTS `user_roles` (
  `id_user_rol` int NOT NULL AUTO_INCREMENT,
  `name` char(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id_user_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- Volcando datos para la tabla health_dashboard.user_roles: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` (`id_user_rol`, `name`) VALUES
	(1, 'ROLE_ADMIN'),
	(2, 'ROLE_USER');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
