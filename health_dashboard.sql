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

-- Volcando estructura para tabla health_dashboard.address
DROP TABLE IF EXISTS `address`;
CREATE TABLE IF NOT EXISTS `address` (
  `id_address` int NOT NULL AUTO_INCREMENT,
  `address` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `id_district` int DEFAULT NULL,
  PRIMARY KEY (`id_address`),
  KEY `id_district__fk` (`id_district`),
  CONSTRAINT `id_district__fk` FOREIGN KEY (`id_district`) REFERENCES `districts` (`id_district`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- Volcando datos para la tabla health_dashboard.address: ~24 rows (aproximadamente)
DELETE FROM `address`;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` (`id_address`, `address`, `id_district`) VALUES
	(2, 'Corrales', 2),
	(3, 'Tumbes', 3),
	(4, 'Cayma', 4),
	(6, 'Lurigancho', 6),
	(7, 'Pampas', 7),
	(14, 'Plateros', 1),
	(16, 'La Peña', 1),
	(17, 'Francos', 1),
	(18, 'Luri', 6),
	(20, 'Tampo dxdcx', 12),
	(22, 'No se', 1),
	(23, 'San Juan', 1),
	(24, 'hghgkk', 1),
	(25, 'Cualquier lugar', 1),
	(26, 'Cualquiera', 1),
	(27, 'santa rosa', 1),
	(28, 'No se dx', 3),
	(33, 'Xdassss', 1),
	(36, 'Su casita xd', 3),
	(37, 'Barrio Chino', 1),
	(38, 'Casqa', 2),
	(39, 'afaf', 6),
	(40, 'sdsdsd', 6),
	(44, 'ssdddd', 1),
	(45, 'dssd', 1);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;

-- Volcando estructura para tabla health_dashboard.cities
DROP TABLE IF EXISTS `cities`;
CREATE TABLE IF NOT EXISTS `cities` (
  `id_city` int NOT NULL AUTO_INCREMENT,
  `city_name` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `id_country` int DEFAULT NULL,
  PRIMARY KEY (`id_city`),
  KEY `id_country__fk` (`id_country`),
  CONSTRAINT `id_country__fk` FOREIGN KEY (`id_country`) REFERENCES `countries` (`id_country`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- Volcando datos para la tabla health_dashboard.cities: ~9 rows (aproximadamente)
DELETE FROM `cities`;
/*!40000 ALTER TABLE `cities` DISABLE KEYS */;
INSERT INTO `cities` (`id_city`, `city_name`, `id_country`) VALUES
	(1, 'Tumbes', 1),
	(2, 'Sullana', 1),
	(3, 'Lima', 1),
	(4, 'Cartagena', 2),
	(5, 'La Plata', 3),
	(6, 'Trujillo', 1),
	(7, 'Piura', 1),
	(8, 'Cusco', 1),
	(9, 'Arequipa', 1);
/*!40000 ALTER TABLE `cities` ENABLE KEYS */;

-- Volcando estructura para tabla health_dashboard.countries
DROP TABLE IF EXISTS `countries`;
CREATE TABLE IF NOT EXISTS `countries` (
  `id_country` int NOT NULL AUTO_INCREMENT,
  `country_name` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id_country`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- Volcando datos para la tabla health_dashboard.countries: ~6 rows (aproximadamente)
DELETE FROM `countries`;
/*!40000 ALTER TABLE `countries` DISABLE KEYS */;
INSERT INTO `countries` (`id_country`, `country_name`) VALUES
	(1, 'Perú'),
	(2, 'Colombia'),
	(3, 'Argentina'),
	(4, 'Chile'),
	(5, 'Brazil'),
	(6, 'Venezuela');
/*!40000 ALTER TABLE `countries` ENABLE KEYS */;

-- Volcando estructura para tabla health_dashboard.customers
DROP TABLE IF EXISTS `customers`;
CREATE TABLE IF NOT EXISTS `customers` (
  `id_customer` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `id_people` int DEFAULT NULL,
  PRIMARY KEY (`id_customer`),
  UNIQUE KEY `customers_user_name_uindex` (`user_name`),
  KEY `id_people__fk2` (`id_people`),
  CONSTRAINT `id_people__fk2` FOREIGN KEY (`id_people`) REFERENCES `peoples` (`id_people`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- Volcando datos para la tabla health_dashboard.customers: ~0 rows (aproximadamente)
DELETE FROM `customers`;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` (`id_customer`, `user_name`, `id_people`) VALUES
	(13, 'juanamartz', 24),
	(14, 'andreaperez', 25),
	(15, 'mariaperfern', 27);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;

-- Volcando estructura para tabla health_dashboard.districts
DROP TABLE IF EXISTS `districts`;
CREATE TABLE IF NOT EXISTS `districts` (
  `id_district` int NOT NULL AUTO_INCREMENT,
  `district_name` char(250) COLLATE utf8_bin DEFAULT NULL,
  `id_city` int DEFAULT NULL,
  PRIMARY KEY (`id_district`),
  KEY `id_city__fk` (`id_city`),
  CONSTRAINT `id_city__fk` FOREIGN KEY (`id_city`) REFERENCES `cities` (`id_city`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- Volcando datos para la tabla health_dashboard.districts: ~12 rows (aproximadamente)
DELETE FROM `districts`;
/*!40000 ALTER TABLE `districts` DISABLE KEYS */;
INSERT INTO `districts` (`id_district`, `district_name`, `id_city`) VALUES
	(1, 'San Jacinto', 1),
	(2, 'Corrales', 1),
	(3, 'Tumbes', 1),
	(4, 'Cayma', 2),
	(5, 'La Joya', 2),
	(6, 'Lurigancho', 3),
	(7, 'Pampas de hospital', 1),
	(8, 'San José', 1),
	(10, 'Demo xd', 4),
	(12, 'No se xd', 4),
	(13, 'Chancay', 3),
	(14, 'Zorritos', 1);
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
  `birth_date` date DEFAULT NULL,
  `id_address` int DEFAULT NULL,
  PRIMARY KEY (`id_people`),
  UNIQUE KEY `peoples_dni_uindex` (`dni`),
  UNIQUE KEY `peoples_mail_uindex` (`mail`),
  UNIQUE KEY `peoples_phone_uindex` (`phone`),
  UNIQUE KEY `peoples_url_img_profile_uindex` (`url_img_profile`),
  KEY `id_address__FK` (`id_address`),
  CONSTRAINT `id_address__FK` FOREIGN KEY (`id_address`) REFERENCES `address` (`id_address`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- Volcando datos para la tabla health_dashboard.peoples: ~6 rows (aproximadamente)
DELETE FROM `peoples`;
/*!40000 ALTER TABLE `peoples` DISABLE KEYS */;
INSERT INTO `peoples` (`id_people`, `dni`, `name`, `last_name`, `mail`, `phone`, `url_img_profile`, `birth_date`, `id_address`) VALUES
	(12, '96541254', 'Juanxd', 'Perez', 'juanpe@mail.com', '965412365', NULL, '1990-11-01', 23),
	(14, '75690429', 'Ronny Harol', 'Clavijo Céspedes', 'ronnycla@mail.com', '999999991', NULL, '2002-05-07', 27),
	(15, '75332012', 'Marco', 'Tal Tal', 'marcotal@mail.com', '954120324', NULL, '1996-11-01', 28),
	(24, '75201235', 'Juanaa', 'Martz', 'juanamartz@mail.com', '962200001', NULL, '2000-12-07', 37),
	(25, '00221444', 'Andrea', 'Perez', 'andreaperez@mail.com', '966632111', NULL, '2002-09-01', 38),
	(27, '33333333', 'María Fernanda', 'Pérez Fernandez', 'mariaperfern@mail.com', '962111112', NULL, '1998-12-02', 40);
/*!40000 ALTER TABLE `peoples` ENABLE KEYS */;

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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- Volcando datos para la tabla health_dashboard.reservation_appointments: ~4 rows (aproximadamente)
DELETE FROM `reservation_appointments`;
/*!40000 ALTER TABLE `reservation_appointments` DISABLE KEYS */;
INSERT INTO `reservation_appointments` (`id_reservation_quote`, `id_customer`, `id_user`, `id_type_reservations`, `id_reserved_day`) VALUES
	(12, 13, 9, 8, 12),
	(13, 14, 9, 2, 13),
	(14, 14, 9, 2, 14),
	(15, 15, 9, 3, 15),
	(16, 15, 9, 3, 16);
/*!40000 ALTER TABLE `reservation_appointments` ENABLE KEYS */;

-- Volcando estructura para tabla health_dashboard.reserved_days
DROP TABLE IF EXISTS `reserved_days`;
CREATE TABLE IF NOT EXISTS `reserved_days` (
  `id_reserved_day` int NOT NULL AUTO_INCREMENT,
  `reservation_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id_reserved_day`),
  UNIQUE KEY `reserved_days_reservation_date_uindex` (`reservation_date`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- Volcando datos para la tabla health_dashboard.reserved_days: ~5 rows (aproximadamente)
DELETE FROM `reserved_days`;
/*!40000 ALTER TABLE `reserved_days` DISABLE KEYS */;
INSERT INTO `reserved_days` (`id_reserved_day`, `reservation_date`) VALUES
	(12, '2021-12-04 19:30:00'),
	(13, '2021-12-09 08:00:00'),
	(14, '2021-12-09 10:30:00'),
	(15, '2021-12-14 10:00:00'),
	(16, '2021-12-30 08:30:00');
/*!40000 ALTER TABLE `reserved_days` ENABLE KEYS */;

-- Volcando estructura para tabla health_dashboard.type_reservations
DROP TABLE IF EXISTS `type_reservations`;
CREATE TABLE IF NOT EXISTS `type_reservations` (
  `id_type_reservation` int NOT NULL AUTO_INCREMENT,
  `name` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id_type_reservation`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- Volcando datos para la tabla health_dashboard.type_reservations: ~8 rows (aproximadamente)
DELETE FROM `type_reservations`;
/*!40000 ALTER TABLE `type_reservations` DISABLE KEYS */;
INSERT INTO `type_reservations` (`id_type_reservation`, `name`, `price`) VALUES
	(1, 'Dentista', 150.00),
	(2, 'Odontologia', 50.00),
	(3, 'Cardiologia', 50.00),
	(4, 'Dermatologia', 40.00),
	(5, 'Traumatologia', 40.00),
	(6, 'Oftalmologia', 50.50),
	(8, 'No seeeee', 60.00),
	(9, 'ssewes', 35.00),
	(10, 'No sesfd', 75.00);
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- Volcando datos para la tabla health_dashboard.users: ~3 rows (aproximadamente)
DELETE FROM `users`;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id_user`, `user_name`, `password`, `id_people`, `id_user_rol`) VALUES
	(7, 'juanpe', '123456', 12, 1),
	(9, 'rclavijoc', '123456', 14, 1),
	(10, 'marcotal', '12345', 15, 2);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

-- Volcando estructura para tabla health_dashboard.user_roles
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE IF NOT EXISTS `user_roles` (
  `id_user_rol` int NOT NULL AUTO_INCREMENT,
  `role_name` char(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id_user_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- Volcando datos para la tabla health_dashboard.user_roles: ~3 rows (aproximadamente)
DELETE FROM `user_roles`;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` (`id_user_rol`, `role_name`) VALUES
	(1, 'ADMINISTRADOR'),
	(2, 'USUARIO'),
	(3, 'SECRETARIA');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
