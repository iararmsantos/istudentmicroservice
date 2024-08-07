CREATE TABLE `user_entity` (
  `id` bigint(20) AUTO_INCREMENT PRIMARY KEY,
  `creation_date` datetime(6) DEFAULT NULL,
  `user_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `first_name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` TEXT COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` enum('PARENT','STUDENT','TEACHER') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `account_non_expired` bit(1) DEFAULT NULL,
    `account_non_locked` bit(1) DEFAULT NULL,
    `credentials_non_expired` bit(1) DEFAULT NULL,
    `enabled` bit(1) DEFAULT NULL,
    UNIQUE KEY `uk_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;