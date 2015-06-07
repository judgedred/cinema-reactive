CREATE SCHEMA IF NOT EXISTS `cinema` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE TABLE IF NOT EXISTS `cinema`.`Film` (
  `film_id` INT NOT NULL AUTO_INCREMENT,
  `film_name` VARCHAR(50) NOT NULL,
  `description` MEDIUMTEXT NOT NULL,
  PRIMARY KEY (`film_id`))
ENGINE = InnoDB;
CREATE TABLE IF NOT EXISTS `cinema`.`Hall` (
  `hall_id` INT NOT NULL AUTO_INCREMENT,
  `hall_number` INT UNSIGNED NOT NULL,
  `hall_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`hall_id`))
ENGINE = InnoDB;
CREATE TABLE IF NOT EXISTS `cinema`.`Filmshow` (
  `filmshow_id` INT NOT NULL AUTO_INCREMENT,
  `date_time` TIMESTAMP(0) NOT NULL,
  `film_id` INT NOT NULL,
  `hall_id` INT NOT NULL,
  PRIMARY KEY (`filmshow_id`),
  INDEX `fk_Filmshow_Film_idx` (`film_id` ASC),
  INDEX `fk_Filmshow_Hall1_idx` (`hall_id` ASC),
  CONSTRAINT `fk_Filmshow_Film`
    FOREIGN KEY (`film_id`)
    REFERENCES `cinema`.`Film` (`film_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Filmshow_Hall1`
    FOREIGN KEY (`hall_id`)
    REFERENCES `cinema`.`Hall` (`hall_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
CREATE TABLE IF NOT EXISTS `cinema`.`Seat` (
  `seat_id` INT NOT NULL AUTO_INCREMENT,
  `seat_number` INT UNSIGNED NOT NULL,
  `row_number` INT UNSIGNED NOT NULL,
  `hall_id` INT NOT NULL,
  PRIMARY KEY (`seat_id`),
  INDEX `fk_Seat_Hall1_idx` (`hall_id` ASC),
  CONSTRAINT `fk_Seat_Hall1`
    FOREIGN KEY (`hall_id`)
    REFERENCES `cinema`.`Hall` (`hall_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
CREATE TABLE IF NOT EXISTS `cinema`.`User` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;
CREATE TABLE IF NOT EXISTS `cinema`.`Ticket` (
  `ticket_id` INT NOT NULL AUTO_INCREMENT,
  `price` FLOAT UNSIGNED NOT NULL,
  `filmshow_id` INT NOT NULL,
  `seat_id` INT NOT NULL,
  PRIMARY KEY (`ticket_id`),
  INDEX `fk_Ticket_Filmshow1_idx` (`filmshow_id` ASC),
  INDEX `fk_Ticket_Seat1_idx` (`seat_id` ASC),
  CONSTRAINT `fk_Ticket_Filmshow1`
    FOREIGN KEY (`filmshow_id`)
    REFERENCES `cinema`.`Filmshow` (`filmshow_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ticket_Seat1`
    FOREIGN KEY (`seat_id`)
    REFERENCES `cinema`.`Seat` (`seat_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
CREATE TABLE IF NOT EXISTS `cinema`.`Reservation` (
  `reservation_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `ticket_id` INT NOT NULL,
  PRIMARY KEY (`reservation_id`),
  INDEX `fk_Reservation_User1_idx` (`user_id` ASC),
  INDEX `fk_Reservation_Ticket1_idx` (`ticket_id` ASC),
  CONSTRAINT `fk_Reservation_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `cinema`.`User` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Reservation_Ticket1`
    FOREIGN KEY (`ticket_id`)
    REFERENCES `cinema`.`Ticket` (`ticket_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
