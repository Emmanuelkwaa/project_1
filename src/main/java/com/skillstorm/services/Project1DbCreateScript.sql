DROP TABLE IF EXISTS Category;
CREATE TABLE `Category` (
	`id` TINYINT unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`name` VARCHAR(20) NOT NULL
);

DROP TABLE IF EXISTS Product;
CREATE TABLE `Product` (
	`id` SMALLINT unsigned NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(100) NOT NULL,
	`price` DOUBLE(6, 2) unsigned NOT NULL,
	`details` VARCHAR(500) NOT NULL,
	`category_id` TINYINT unsigned NOT NULL,
	`available_quantity` SMALLINT unsigned NOT NULL,
	PRIMARY KEY (`id`),

    CONSTRAINT fk_Category FOREIGN KEY (category_id)
    REFERENCES Category (id)
    ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS Customer;
CREATE TABLE `Customer`
(
    `id` INT unsigned NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(50) NOT NULL,
    `last_name` VARCHAR(50) NOT NULL,
    `address_line1` VARCHAR(20) NOT NULL,
    `address_line2` VARCHAR(20) NULL,
    `city` VARCHAR(10) NOT NULL,
    `state_code` CHAR(2) NOT NULL,
    `zipcode` VARCHAR(10) NOT NULL,
    `country` VARCHAR(100) NOT NULL,
    `phone` VARCHAR(20) NOT NULL,
    `email` VARCHAR(100) UNIQUE NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `Order`;
DROP TABLE IF EXISTS `Orders`;
CREATE TABLE `Orders` (
	`id` INT unsigned NOT NULL AUTO_INCREMENT,
	`product_id` SMALLINT unsigned NOT NULL,
	`quantity` TINYINT unsigned NOT NULL,
	`customer_id` INT unsigned NOT NULL,
	`total_cost` DOUBLE unsigned NOT NULL,
	`order_date` DATE NOT NULL,
	PRIMARY KEY (`id`),

    CONSTRAINT fk_Product FOREIGN KEY (product_id)
    REFERENCES Product (id)
    ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT fk_Customer FOREIGN KEY (customer_id)
    REFERENCES Customer (id)
    ON DELETE CASCADE ON UPDATE CASCADE
);



