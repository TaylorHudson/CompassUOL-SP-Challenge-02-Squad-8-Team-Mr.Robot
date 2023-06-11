CREATE TABLE IF NOT EXISTS product_quantity(
   product_quantity_id INT AUTO_INCREMENT PRIMARY KEY,
   product_id INT NOT NULL,
   quantity INT NOT NULL,
   order_id INT NOT NULL,
   FOREIGN KEY (product_id) REFERENCES Product (product_id),
   FOREIGN KEY (order_id) REFERENCES orders (order_id)
);