
1)В постгресе создаем базу и пользователя

CREATE DATABASE mydb;

create user sanek with encrypted password 'sanek';

grant all privileges on database mydb to sanek;

2)Создаем таблицы и даем права на них


CREATE TABLE users (
  id  SERIAL PRIMARY KEY,
  name varchar(500) UNIQUE ,
  date_of_birth date DEFAULT NULL,
  password varchar(500) DEFAULT NULL 
);

CREATE TABLE email_data (
  id  SERIAL PRIMARY KEY,
  user_id BIGINT,
  email varchar(200) UNIQUE
) ;

CREATE TABLE phone_data (
  id  SERIAL PRIMARY KEY,
  user_id BIGINT,
  phone varchar(13) UNIQUE
 
) ;

CREATE TABLE account (
  id  SERIAL PRIMARY KEY,
  user_id BIGINT,
  balance decimal(10,2) DEFAULT NULL
 
) ;

GRANT ALL PRIVILEGES ON TABLE users TO sanek;
GRANT ALL PRIVILEGES ON TABLE email_data TO sanek;
GRANT ALL PRIVILEGES ON TABLE phone_data TO sanek;
GRANT ALL PRIVILEGES ON TABLE account TO sanek;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO sanek;


Test CreateUsers создает пользователей

