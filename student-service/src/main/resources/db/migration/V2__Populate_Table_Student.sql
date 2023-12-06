insert into student (id, first_name, last_name, email, phone, creation_date, role) values
(1, 'Stephi', 'Tace', 'stace0@ycombinator.com', '3576523079', '2023-02-23 06:54:35', null),
(2, 'Curt', 'Newart', 'cnewart1@live.com', '2314072864', '2023-05-13 04:41:29', null),
(3, 'Kati', 'Cristoforetti', 'kcristoforetti2@simplemachines.org', '3058473147', '2023-09-08 08:23:57', null),
(4, 'Alejandra', 'Simic', 'asimic3@hubpages.com', '1117156012', '2023-03-12 22:02:41', null),
(5, 'Dodie', 'De''Vere - Hunt', 'ddeverehunt4@instagram.com', '4552603771', '2023-10-16 09:08:52', null);

INSERT INTO `student_parent` (`id`, `parent_id`, `student_id`) VALUES (1, 1, 1);
INSERT INTO `student_parent` (`id`, `parent_id`, `student_id`) VALUES (2, 2, 1);
INSERT INTO `student_parent` (`id`, `parent_id`, `student_id`) VALUES (3, 3, 2);
INSERT INTO `student_parent` (`id`, `parent_id`, `student_id`) VALUES (4, 4, 2);