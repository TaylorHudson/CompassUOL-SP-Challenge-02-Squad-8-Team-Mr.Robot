create database challenge2;
use challenge2;

CREATE TABLE IF NOT EXISTS customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    active BOOLEAN NOT NULL
    );

CREATE TABLE IF NOT EXISTS product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description VARCHAR(150) NOT NULL
    );

CREATE TABLE IF NOT EXISTS Orders(
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    status VARCHAR(10) NOT NULL,
    customer_id INT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES Customer (customer_id)
    );

CREATE TABLE IF NOT EXISTS product_quantity(
    product_quantity_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    order_id INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES Product (product_id),
    FOREIGN KEY (order_id) REFERENCES orders (order_id)
    );

CREATE TABLE payment (
     payment_id INT AUTO_INCREMENT PRIMARY KEY,
     payment_method VARCHAR(12) NOT NULL,
     payment_date DATE NOT NULL,
     order_id INT NOT NULL,
     FOREIGN KEY (order_id) REFERENCES orders(order_id)
);