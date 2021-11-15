CREATE DATABASE IF NOT EXISTS AuthenticationServer;
use AuthenticationServer;

CREATE TABLE IF NOT EXISTS CLIENT(
	id int(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nom varchar(255) not null,
    type int(2) unsigned NOT NULL, #1 = particulier / 2 = grossiste
    pwd varchar(255) not null
);
