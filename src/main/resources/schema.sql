CREATE TABLE IF NOT EXISTS `Users` (
     `userid`         INTEGER  PRIMARY KEY,
     `name` VARCHAR(50) NOT NULL,
     `email`        VARCHAR(50) NOT NULL,
     `password`        VARCHAR(50) NOT NULL,
     `created`        DATE,
     `lastLogin`        DATE,
     `token`        VARCHAR(50) NOT NULL,
     `isActive`        INTEGER  NOT NULL
);