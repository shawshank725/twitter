
-- ===== create and use twitter database.sql =====

create database if not exists `twitter`;

use `twitter`;

-- ===== create-authentication-table.sql =====

set foreign_key_checks = 1;
create table if not exists `users`(
  `user_id` BIGINT primary key auto_increment,
  `username` varchar(50) unique key NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL, 
  `bio` varchar(160), 
  `website` varchar(100), 
  `location` varchar(30), 
  
  `password` varchar(60) NOT NULL,
  `enabled` tinyint NOT NULL,
  `profile_photo` varchar(200) DEFAULT "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/Default_pfp.svg/2048px-Default_pfp.svg.png",
  `background_photo` varchar(200) DEFAULT "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5b/2011-03-09-fort-du-lomont-10.jpg/800px-2011-03-09-fort-du-lomont-10.jpg",
  `joined_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `roles` (
`role_id` int NOT NULL AUTO_INCREMENT,
`role_name` varchar(50) DEFAULT NULL,
PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `roles` VALUES 
(1, "ROLE_USER"),
(2, "ROLE_ADMIN");

CREATE TABLE `users_roles` (
  `user_id` BIGINT NOT NULL,
  `role_id` int NOT NULL,
  
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_ROLE_idx` (`role_id`),
  
  CONSTRAINT `FK_USER_05` FOREIGN KEY (`user_id`) 
  REFERENCES `users` (`user_id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`) 
  REFERENCES `roles` (`role_id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

set foreign_key_checks = 1;

-- ===== create-bookmarks-table.sql =====

use `twitter`;

CREATE TABLE IF NOT EXISTS `bookmarks` (
`bookmark_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
`bookmarked_by_user_id` BIGINT NOT NULL,
`bookmarked_post_id` BIGINT NOT NULL,
`bookmarked_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
UNIQUE (bookmarked_by_user_id, bookmarked_post_id)
);

-- ===== create-follower-followee-table.sql =====

use `twitter`;

CREATE TABLE IF NOT EXISTS `connections` (
    connection_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    follower_id BIGINT NOT NULL,
    followee_id BIGINT NOT NULL,

    UNIQUE (follower_id, followee_id),
    
    CHECK (follower_id <> followee_id)
);

CREATE TABLE IF NOT EXISTS `blocked_users` (
    blocker_id BIGINT NOT NULL,
    blocked_id BIGINT NOT NULL,
    blocked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (blocker_id, blocked_id)
);


-- ===== create-notifications-table.sql =====

use `twitter`;

create table if not exists `notifications` (
	`notification_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `post_id` BIGINT DEFAULT NULL, 
    `notification_type` ENUM( 'LIKE' , 'QUOTE','REPLY', 'FOLLOW', 'MENTION') NOT NULL,
    `notification_status` ENUM('READ', 'UNREAD') NOT NULL DEFAULT 'UNREAD',
    `notification_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `notified_user_id` BIGINT NOT NULL,
    `triggered_by_user_id` BIGINT NOT NULL,
    
	INDEX (`notified_user_id`),
    INDEX (`notification_status`),
    INDEX (`notification_time`)
);

-- ===== create-posting-database-table.sql =====

USE `twitter`;

CREATE TABLE IF NOT EXISTS `posts` (
  `post_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `post_text` VARCHAR(300),
  `quoted_post_id` BIGINT DEFAULT NULL,
  `reply_to_post_id` BIGINT DEFAULT NULL,
  `post_type` ENUM('ORIGINAL', 'REPLY', 'QUOTE'),
  `visibility` ENUM('PUBLIC', 'PRIVATE', 'FOLLOWERS'),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  -- Self-referencing foreign keys 
  CONSTRAINT `fk_post_quoted`
    FOREIGN KEY (`quoted_post_id`) REFERENCES `posts`(`post_id`)
    ON DELETE SET NULL ON UPDATE CASCADE,

  CONSTRAINT `fk_post_reply_to`
    FOREIGN KEY (`reply_to_post_id`) REFERENCES `posts`(`post_id`)
    ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `post_media` (
  `media_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `post_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `media_url` VARCHAR(300),
  `media_type` ENUM('IMAGE', 'VIDEO', 'GIF'),

  CONSTRAINT `fk_media_post`
    FOREIGN KEY (`post_id`) REFERENCES `posts`(`post_id`)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ===== make-notification-deleting-trigger.sql =====


-- ===== create-likes-table.sql =====

use `twitter`;

CREATE TABLE IF NOT EXISTS `likes` (
`like_id` BIGINT PRIMARY KEY AUTO_INCREMENT,
`liked_by_user_id` BIGINT NOT NULL,
`liked_post_id` BIGINT NOT NULL,
`liked_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
UNIQUE (liked_by_user_id, liked_post_id),
CONSTRAINT `fk_like_post`
FOREIGN KEY (`liked_post_id`) REFERENCES `posts`(`post_id`)
ON DELETE CASCADE ON UPDATE CASCADE
);

USE `twitter`;

DELIMITER //

CREATE TRIGGER prune_notifications_after_insert
AFTER INSERT ON notifications
FOR EACH ROW
BEGIN
    DECLARE unread_count INT;
    DECLARE total_count INT;
    DECLARE excess_count INT;

    -- Count unread for this user
    SELECT COUNT(*) INTO unread_count
    FROM notifications
    WHERE notified_user_id = NEW.notified_user_id
      AND notification_status = 'UNREAD';

    -- Count total for this user
    SELECT COUNT(*) INTO total_count
    FROM notifications
    WHERE notified_user_id = NEW.notified_user_id;

    -- If more than allowed total, delete oldest read ones
    IF total_count > (unread_count + 20) THEN
        SET excess_count = total_count - (unread_count + 20);

        DELETE FROM notifications
        WHERE notification_id IN (
            SELECT notification_id
            FROM (
                SELECT notification_id
                FROM notifications
                WHERE notified_user_id = NEW.notified_user_id
                  AND notification_status = 'READ'
                ORDER BY created_at ASC
                LIMIT excess_count
            ) AS sub
        );
    END IF;
END;
//

DELIMITER ;
