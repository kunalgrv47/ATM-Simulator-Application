-- create database
CREATE DATABASE atm_app_db;
use atm_app_db;

-- Create the users table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(20) NOT NULL UNIQUE,
    hashed_pin VARCHAR(60) NOT NULL, -- Store the hashed PIN
    salt VARCHAR(29) NOT NULL
);


-- Create the accounts table
CREATE TABLE accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    account_number VARCHAR(18) NOT NULL,
    balance DECIMAL(16, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create the transactions table
CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    type VARCHAR(255) NOT NULL,
    amount DECIMAL(16, 2) NOT NULL,
    time VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


-- Create the counters table
CREATE TABLE counters (
    id INT AUTO_INCREMENT PRIMARY KEY,
    prefix VARCHAR(2) NOT NULL,
    counter1 INT NOT NULL,
    counter2 INT NOT NULL
);

-- Insert initial values into counters table
INSERT INTO counters (prefix, counter1, counter2) VALUES ('23', 0, 0);

