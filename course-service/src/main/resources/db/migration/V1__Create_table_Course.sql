CREATE TABLE `course` (
  `id` bigint(20) AUTO_INCREMENT PRIMARY KEY,
  `creation_date` datetime(6) DEFAULT NULL,
  `section` enum('FALL','SPRING','SUMMER','WINTER') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `title` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `year` int(20) DEFAULT NULL,
  `teacher_id` bigint(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;