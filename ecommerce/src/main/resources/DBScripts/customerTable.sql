create database challenge2;

use challenge2;

create table if not exists Customer (
customer_id int auto_increment primary KEY,
name VARCHAR(150) NOT NULL,
cpf VARCHAR(14) NOT NULL UNIQUE,
email VARCHAR(255) NOT NULL UNIQUE,
active boolean NOT NULL
    )
