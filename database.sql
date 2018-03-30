CREATE DATABASE IF NOT EXISTS internshipsystem;

USE internshipsystem;

CREATE USER 'max'@'localhost' IDENTIFIED BY 'qwerty';
GRANT ALL PRIVILEGES ON *.* TO 'max'@'localhost'
WITH GRANT OPTION;
CREATE USER 'max'@'%' IDENTIFIED BY 'qwerty';
GRANT ALL PRIVILEGES ON *.* TO 'max'@'%'
WITH GRANT OPTION;

CREATE TABLE groups
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name CHAR(30) NOT NULL,
  periodStart DATE,
  periodFinish DATE
);
CREATE UNIQUE INDEX groups_id_uindex ON groups (id);

INSERT INTO groups (name, periodStart, periodFinish) VALUES ('inCamp S8', '2018-03-05', '2018-05-28');
