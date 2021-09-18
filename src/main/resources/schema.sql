DROP TABLE IF EXISTS Inventory;
  
CREATE TABLE Inventory (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  product_id INT NOT NULL,
  available_units INT NOT NULL,
  version INT NOT NULL
);

DROP TABLE IF EXISTS Product;
  
CREATE TABLE Product (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  product_name VARCHAR(250) NOT NULL,
  price INT NOT NULL,
  version INT NOT NULL
);

DROP TABLE IF EXISTS User;

CREATE TABLE User (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  username VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL,
  version INT NOT NULL
);