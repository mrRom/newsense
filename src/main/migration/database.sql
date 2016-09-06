DROP DATABASE IF EXISTS `newsensedb`;
CREATE SCHEMA `newsensedb` DEFAULT CHARACTER SET utf8;

CREATE TABLE IF NOT EXISTS `newsensedb`.`links` (
  `linkvalue` VARCHAR(250) NOT NULL,
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `host` VARCHAR(80) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `linkvalue_UNIQUE` (`linkvalue` ASC),
  UNIQUE INDEX `source_UNIQUE` (`host` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `newsensedb`.`news` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(250) NOT NULL,
  `url` VARCHAR(200) NOT NULL,
  `autor` VARCHAR(100) NULL DEFAULT NULL,
  `description` VARCHAR(1000) NULL DEFAULT NULL,
  `publish_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `newsensedb`.`selected_sites` (
  `user_name` VARCHAR(120) NOT NULL,
  `site` VARCHAR(120) NOT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `newsensedb`.`texts` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `text` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `newsensedb`.`users` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `enabled` TINYINT(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `newsensedb`.`user_roles` (
  `user_role_id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_role_id`),
  UNIQUE INDEX `uni_username_role` (`role` ASC, `username` ASC),
  INDEX `fk_username_idx` (`username` ASC),
  CONSTRAINT `fk_username`
    FOREIGN KEY (`username`)
    REFERENCES `newsensedb`.`users` (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

