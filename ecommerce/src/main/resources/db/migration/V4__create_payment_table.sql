CREATE TABLE IF NOT EXISTS payment (
     payment_id INT AUTO_INCREMENT PRIMARY KEY,
     payment_method VARCHAR(12) NOT NULL,
     payment_date DATE NOT NULL,
     order_id INT NOT NULL,
     FOREIGN KEY (order_id) REFERENCES orders(order_id)
);