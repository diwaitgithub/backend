
-- auto_incement

ALTER TABLE users AUTO_INCREMENT =1000;
ALTER TABLE roles AUTO_INCREMENT =1000;
ALTER TABLE authorities AUTO_INCREMENT =1000;
ALTER TABLE games AUTO_INCREMENT =1000;
ALTER TABLE slots AUTO_INCREMENT =1000;
ALTER TABLE bookings AUTO_INCREMENT =1000;


-- users 
INSERT INTO `users` (`user_id`,`user_name`, `email`, `password`, `status`) VALUES (1000, 'admin123', 'admin@gmail.com','$2a$10$hNUKiQxAviPJO5TcS2aKQO73J9PulgrfrSwRfjb7KA9gk2ask28V2', 'ACTIVE');
-- admiN@123
INSERT INTO `users` (`user_id`,`user_name`, `email`, `password`, `status`) VALUES (1001, 'user123', 'user@gmail.com','$2a$10$FBO03Sg4oUXTFOWg5GzgBeeSwJJjQjbsHpUtj/kMFVEPI5aXZrG06', 'ACTIVE');
-- useR@123 

-- roles
INSERT INTO `roles` (`role_id`,`role_name`) VALUES (1000, 'ADMIN');
INSERT INTO `roles` (`role_id`,`role_name`) VALUES (1001, 'USER');

-- authorities
INSERT INTO `authorities` (`user_id`, `role_id`) VALUES (1000, 1000);
INSERT INTO `authorities` (`user_id`, `role_id`) VALUES (1000, 1001);
INSERT INTO `authorities` (`user_id`, `role_id`) VALUES (1001, 1001);

-- games
INSERT INTO `games` (`game_id`, `game_name`, `image`) VALUES (1000, 'Cricket', '');

-- slots
INSERT INTO `slots` (`slot_id`,`end_time`,`game_id`,`location`,`slot_name`,`start_time`) VALUES (1000,'14:00:00.000000',1000,'Ground 2','Slot1','12:00:00.000000');
INSERT INTO `slots` (`slot_id`,`end_time`,`game_id`,`location`,`slot_name`,`start_time`) VALUES (1001,'14:00:00.000000',1000,'Ground 2','Slot2','12:00:00.000000');
INSERT INTO `slots` (`slot_id`,`end_time`,`game_id`,`location`,`slot_name`,`start_time`) VALUES (1002,'14:00:00.000000',1000,'Ground 2','Slot3','12:00:00.000000');
INSERT INTO `slots` (`slot_id`,`end_time`,`game_id`,`location`,`slot_name`,`start_time`) VALUES (1003,'14:00:00.000000',1000,'Ground 2','Slot4','12:00:00.000000');
INSERT INTO `slots` (`slot_id`,`end_time`,`game_id`,`location`,`slot_name`,`start_time`) VALUES (1004,'14:00:00.000000',1000,'Ground 2','Slot5','12:00:00.000000');
INSERT INTO `slots` (`slot_id`,`end_time`,`game_id`,`location`,`slot_name`,`start_time`) VALUES (1005,'14:00:00.000000',1000,'Ground 2','Slot6','12:00:00.000000');

-- bookings
INSERT INTO `bookings` (`booking_id`,`for_date`,`transaction_date`,`game_id`,`slot_id`,`user_id`) VALUES (1000,'2023-12-20','2023-11-10 21:04:28.138136',1000,1004,1000);
INSERT INTO `bookings` (`booking_id`,`for_date`,`transaction_date`,`game_id`,`slot_id`,`user_id`) VALUES (1001,'2023-12-21','2023-11-10 21:17:07.651174',1000,1004,1000);
INSERT INTO `bookings` (`booking_id`,`for_date`,`transaction_date`,`game_id`,`slot_id`,`user_id`) VALUES (1002,'2023-12-22','2023-11-10 21:27:06.617101',1000,1004,1000);
INSERT INTO `bookings` (`booking_id`,`for_date`,`transaction_date`,`game_id`,`slot_id`,`user_id`) VALUES (1003,'2023-12-23','2023-11-10 21:30:50.438710',1000,1004,1000);
INSERT INTO `bookings` (`booking_id`,`for_date`,`transaction_date`,`game_id`,`slot_id`,`user_id`) VALUES (1004,'2023-12-24','2023-11-10 21:31:19.003183',1000,1004,1000);
INSERT INTO `bookings` (`booking_id`,`for_date`,`transaction_date`,`game_id`,`slot_id`,`user_id`) VALUES (1005,'2023-12-25','2023-11-10 21:32:40.130680',1000,1004,1000);
INSERT INTO `bookings` (`booking_id`,`for_date`,`transaction_date`,`game_id`,`slot_id`,`user_id`) VALUES (1006,'2023-12-26','2023-11-11 10:46:30.119128',1000,1004,1000);
INSERT INTO `bookings` (`booking_id`,`for_date`,`transaction_date`,`game_id`,`slot_id`,`user_id`) VALUES (1007,'2023-12-26','2023-11-11 10:47:50.102505',1000,1003,1000);
INSERT INTO `bookings` (`booking_id`,`for_date`,`transaction_date`,`game_id`,`slot_id`,`user_id`) VALUES (1008,'2023-12-11','2023-11-11 19:41:34.271945',1000,1003,1000);
INSERT INTO `bookings` (`booking_id`,`for_date`,`transaction_date`,`game_id`,`slot_id`,`user_id`) VALUES (1009,'2023-12-12','2023-11-11 20:21:34.259092',1000,1003,1000);
INSERT INTO `bookings` (`booking_id`,`for_date`,`transaction_date`,`game_id`,`slot_id`,`user_id`) VALUES (1010,'2023-12-02','2023-11-11 20:21:47.672051',1000,1003,1000);
INSERT INTO `bookings` (`booking_id`,`for_date`,`transaction_date`,`game_id`,`slot_id`,`user_id`) VALUES (1011,'2023-12-31','2023-11-11 20:26:55.682659',1000,1004,1000);
