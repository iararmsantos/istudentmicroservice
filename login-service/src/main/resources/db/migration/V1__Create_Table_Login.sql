CREATE TABLE `user` (
  `id` bigint(20) AUTO_INCREMENT PRIMARY KEY,
  `creation_date` datetime(6) DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `first_name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` enum('PARENT','STUDENT','TEACHER') COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;