CREATE TABLE `grade` (
  `id` bigint(20) NOT NULL,
  `creation_date` datetime(6),
  `letter_grade` varchar(2),
  `number_grade` NUMERIC(5, 2),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;