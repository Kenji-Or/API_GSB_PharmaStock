-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : jeu. 27 mars 2025 à 17:26
-- Version du serveur : 9.2.0
-- Version de PHP : 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `api_gsb`
--

-- --------------------------------------------------------

--
-- Structure de la table `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `id_category` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id_category`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `category`
--

INSERT INTO `category` (`id_category`, `name`) VALUES
(5, 'Vaccin'),
(6, 'Antibiotique'),
(7, 'Anti-inflammatoires'),
(8, 'Complements et Vitamines');

-- --------------------------------------------------------

--
-- Structure de la table `medicaments`
--

DROP TABLE IF EXISTS `medicaments`;
CREATE TABLE IF NOT EXISTS `medicaments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `date_expiration` varchar(255) DEFAULT NULL,
  `category` int DEFAULT NULL,
  `alerte_stock` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `category` (`category`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `medicaments`
--

INSERT INTO `medicaments` (`id`, `name`, `quantity`, `date_expiration`, `category`, `alerte_stock`) VALUES
(4, 'Dolipranne', 200, '12/05/2025', 7, 100),
(5, 'Advil', 10, '02/06/2025', 7, 50),
(6, 'Vaccin COVID-19', 900, '03/2025', 5, 200),
(9, 'Clamoxyl', 200, '06/2025', 6, 50),
(10, 'Vitamine C', 250, '07/2025', 8, 100);

-- --------------------------------------------------------

--
-- Structure de la table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `id_role` int NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_role`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `roles`
--

INSERT INTO `roles` (`id_role`, `name`, `description`) VALUES
(1, 'Administrateur', 'Administrateur'),
(2, 'Utilisateur', 'Utilisateur');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `role` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `first_name`, `last_name`, `mail`, `password`, `role`) VALUES
(2, 'Sophie', 'Foncek', 'sophiefoncek@mail.com', '$2a$10$zK0s5fdA9Knonv/0g3BK0eiGuCP65NpM2cRMf5PXa0DP3VkVLEq7m', 2),
(3, 'Agathe', 'FEELING', 'agathefeeling@mail.com', '$2a$10$fAzkl2rKqW2OzMXYSSCLE.Z/1XEbdq7Dz9VRtZo/9y1/vq75.lkPC', 2),
(6, 'Bobby', 'Marley', 'bobbymarley@mail.com', '$2a$10$z0d3Qm0PDUJihxScDwX4juPi2Sv2k3xZWTqTveyXuE0XK.3B/iY9e', 1),
(9, 'Kenji', 'Ogier', 'kenjiogier@gmail.com', '$2a$10$MnjKB3sik0byILPdK1ImrO75FDs9MFlQiEIkPmg9ffVGDy8jjabMy', 1),
(11, 'Paul', 'Logan', 'paul.logan@mail.com', '$2a$10$Vum1kFJ4ndkqJp6t1dlCruzTobF7JgfUbIG7au6t1gLSY1qzjVicC', 2),
(15, 'Admin', 'GSB', 'admin.gsb@mail.com', '$2a$10$gwdZTRRo3TkGHtORu6SqHu4c02NIMS.1ipn1v3Cuf64bSA2.4t9Wu', 1);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `medicaments`
--
ALTER TABLE `medicaments`
  ADD CONSTRAINT `medicaments_ibfk_1` FOREIGN KEY (`category`) REFERENCES `category` (`id_category`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role`) REFERENCES `roles` (`id_role`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
