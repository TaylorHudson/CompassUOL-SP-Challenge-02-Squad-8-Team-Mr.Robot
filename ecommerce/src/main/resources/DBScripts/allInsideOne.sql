use challenge2
CREATE TABLE IF NOT EXISTS customer (
customer_id INT AUTO_INCREMENT PRIMARY KEY,
 name VARCHAR(150) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    active BOOLEAN NOT NULL
    );

CREATE TABLE IF NOT EXISTS product (
                                       product_id INT AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(150) NOT NULL,
    price DECIMAL(10, 4) NOT NULL,
    description VARCHAR(150) NOT NULL
    );

CREATE TABLE IF NOT EXISTS Orders(
                                     order_id INT AUTO_INCREMENT PRIMARY KEY,
                                     date DATE NOT NULL,
                                     status VARCHAR(10),
    customer_id INT,
    FOREIGN KEY (customer_id) REFERENCES Customer (customer_id)
    );

CREATE TABLE IF NOT EXISTS orderItems (
                                          order_item_id INT AUTO_INCREMENT PRIMARY KEY,
                                          order_id INT,
                                          product_id INT,
                                          quantity INT,
                                          FOREIGN KEY (order_id) REFERENCES orders (order_id),
    FOREIGN KEY (product_id) REFERENCES Product (product_id)
    );
