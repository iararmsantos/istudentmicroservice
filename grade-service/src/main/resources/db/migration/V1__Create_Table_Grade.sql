CREATE TABLE `grade` (
  `id` bigint(20) AUTO_INCREMENT PRIMARY KEY,
  `creation_date` datetime(6),
  `letter_grade` varchar(2),
  `number_grade` NUMERIC(5, 2),
  `student_id` bigint,
  `course_id` bigint
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;