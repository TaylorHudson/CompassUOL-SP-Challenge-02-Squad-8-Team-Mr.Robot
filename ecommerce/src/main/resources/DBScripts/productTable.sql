use challenge2;

create table if not exists Product(

productId numeric auto_increment primary key,
name VARCHAR(150) NOT NULL,
price DECIMAL(10, 4) NOT NULL,
description TEXT NOT NULL

);