-- MySQL Workbench Forward Engineering

DROP schema if exists `ssafy`;

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ssafy
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ssafy
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ssafy` DEFAULT CHARACTER SET utf8mb4 ;
USE `ssafy` ;

-- -----------------------------------------------------
-- Table `ssafy`.`member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafy`.`member` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(30) NULL,
  `user_pw` VARCHAR(100) NULL,
  `provider` VARCHAR(20) NULL,
  `provider_id` VARCHAR(255) NULL,
  `refresh_token` VARCHAR(255) NULL,
  `email` VARCHAR(45) NOT NULL,
  `name` VARCHAR(10) NOT NULL,
  `nickname` VARCHAR(20) NOT NULL DEFAULT 'unknown',
  `signdate` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `birth` DATETIME NULL,
  `grade` TINYINT NOT NULL DEFAULT 0,
  `interests` INT NOT NULL DEFAULT 0,
  `profile_img_directory` VARCHAR(255) DEFAULT '/assets/img/userProfile/default_profile.png',
  `last_login_date` DATETIME NULL,
  `total_visited` INT NOT NULL DEFAULT 0,
  `continuous_visited` INT NOT NULL DEFAULT 0,
  `follower_cnt` INT NOT NULL DEFAULT 0,
  `following_cnt` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ssafy`.`community`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafy`.`community` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `type` INT NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `content` TEXT NOT NULL,
  `views` INT NOT NULL DEFAULT 0,
  `upload_date` DATETIME NOT NULL,
  `update_date` DATETIME,
  `video_directory` VARCHAR(255) NULL,
  `thumbnail_directory` VARCHAR(255) NULL,
  `category` INT NULL,
  `member_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_community_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_community_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `ssafy`.`member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ssafy`.`challenges`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafy`.`challenges` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `challenge_name` VARCHAR(45) NOT NULL,
  `challenge_info` VARCHAR(45) NOT NULL,
  `challenge_icon_directory` VARCHAR(255) NOT NULL,
  `challenge_target` VARCHAR(45) NOT NULL,
  `challenge_goal` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ssafy`.`callenge_user_map`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafy`.`challenge_user_map` (
  `achieve_date` DATETIME NOT NULL,
  `challenges_id` BIGINT NOT NULL,
  `member_id` BIGINT NOT NULL,
  PRIMARY KEY (`challenges_id`, `member_id`),
  INDEX `fk_challenge_user_map_challenges1_idx` (`challenges_id` ASC) VISIBLE,
  INDEX `fk_challenge_user_map_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_challenge_user_map_challenges1`
    FOREIGN KEY (`challenges_id`)
    REFERENCES `ssafy`.`challenges` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_challenge_user_map_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `ssafy`.`member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ssafy`.`youtube_video`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafy`.`youtube_video` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `youtube_id` VARCHAR(50) NOT NULL UNIQUE,
  `channel_name` VARCHAR(100) NOT NULL,
  `view_cnt` BIGINT NOT NULL,
  `category` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ssafy`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafy`.`comment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(255) NOT NULL,
  `upload_date` DATETIME NOT NULL,
  `community_id` BIGINT NULL,
  `youtube_video_id` BIGINT NULL,
  `member_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_comment_community1_idx` (`community_id` ASC) VISIBLE,
  INDEX `fk_comment_youtube_video1_idx` (`youtube_video_id` ASC) VISIBLE,
  INDEX `fk_comment_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_comment_community1`
    FOREIGN KEY (`community_id`)
    REFERENCES `ssafy`.`community` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_youtube_video1`
    FOREIGN KEY (`youtube_video_id`)
    REFERENCES `ssafy`.`youtube_video` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `ssafy`.`member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ssafy`.`liked`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafy`.`liked` (
  `community_id` BIGINT NOT NULL,
  `member_id` BIGINT NOT NULL,
  PRIMARY KEY (`community_id`,`member_id`),
  INDEX `fk_liked_community1_idx` (`community_id` ASC) VISIBLE,
  INDEX `fk_liked_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_liked_community1`
    FOREIGN KEY (`community_id`)
    REFERENCES `ssafy`.`community` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_liked_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `ssafy`.`member` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ssafy`.`follow`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafy`.`follow` (
  `from` BIGINT NOT NULL,
  `to` BIGINT NOT NULL,
  PRIMARY KEY (`from`, `to`),
  INDEX `fk_follow_user2_idx` (`to` ASC) VISIBLE,
  CONSTRAINT `fk_follow_user1`
    FOREIGN KEY (`from`)
    REFERENCES `ssafy`.`member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_follow_user2`
    FOREIGN KEY (`to`)
    REFERENCES `ssafy`.`member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
