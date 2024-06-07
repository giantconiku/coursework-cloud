CREATE DATABASE  IF NOT EXISTS `userportal`;

USE `userportal`;

CREATE TABLE IF NOT EXISTS `role` (
    `role_id` INT NOT NULL AUTO_INCREMENT,
    `role_name` VARCHAR(50) NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `created_by` VARCHAR(50) NOT NULL,
    `updated_at` TIMESTAMP DEFAULT NULL,
    `updated_by` VARCHAR(50) DEFAULT NULL,
    PRIMARY KEY (`role_id`)
);

CREATE TABLE IF NOT EXISTS `user` (
    `user_id` INT NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(30) NOT NULL,
    `fathers_name` VARCHAR(30) NOT NULL,
    `last_name` VARCHAR(30) NOT NULL,
    `phone_number` VARCHAR(20) NOT NULL,
    `birthday` DATE NOT NULL,
    `email` VARCHAR(50) NOT NULL,
    `password` VARCHAR(200) NOT NULL,
    `role_id` INT NOT NULL,
    `profile_image_path` VARCHAR(500) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `created_by` VARCHAR(50) NOT NULL,
    `updated_at` TIMESTAMP DEFAULT NULL,
    `updated_by` VARCHAR(50) DEFAULT NULL,
    PRIMARY KEY (`user_id`),
    FOREIGN KEY (role_id) REFERENCES role(role_id)
);