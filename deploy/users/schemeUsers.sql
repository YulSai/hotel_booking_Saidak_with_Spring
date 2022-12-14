/*CREATE DATABASE "hotelBooking";
*/

/*
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
 */

CREATE TABLE IF NOT EXISTS roles (
id BIGSERIAL PRIMARY KEY,
name VARCHAR(15) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
id BIGSERIAL PRIMARY KEY,
username TEXT UNIQUE NOT NULL,
password text NOT NULL,
first_name TEXT NOT NULL,
last_name TEXT NOT NULL,
email TEXT UNIQUE NOT NULL,
phone_number TEXT NOT NULL,
role_id BIGINT NOT NULL REFERENCES roles,
avatar TEXT,
block BOOLEAN NOT NULL DEFAULT false,
deleted BOOLEAN NOT NULL DEFAULT false
);