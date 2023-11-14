CREATE TABLE IF NOT EXISTS `student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime(6) DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `first_name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` enum('PARENT','STUDENT','TEACHER') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `parent_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL,
  `student_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbq16gbmjbhy1b7tojckht5yxj` (`student_id`),
  CONSTRAINT `FKbq16gbmjbhy1b7tojckht5yxj` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;