# ATM-Simulator-Application

This is a simple Java ATM (Automated Teller Machine) application that allows users to perform basic banking operations such as account creation, balance checking, withdrawals, deposits, and viewing transaction history. The application is developed using Java and a MySQL database for data storage.

## Features

- User Registration and Login
- Balance Enquiry
- Deposit and Withdrawal
- Transaction History
- MySQL Database Integration
- Secure user authentication with hashed PIN and salt.

## Prerequisites

Before running the ATM application, make sure you have the following prerequisites installed:

- Java Development Kit (JDK) 8 or later
- Eclipse IDE (or your preferred Java IDE)
- MySQL Database

## Getting Started

1. Clone this repository to your local machine.   (Repository Link: https://github.com/kunalgrv47/ATM-Simulator-App.git )
2. Import the project into your preferred Java IDE (e.g., Eclipse).
3. Set up the required external libraries for JDBC, MySQL, and BCrypt.
4. Configure the database connection in the `DatabaseConnection` class.
5. Run the `ATMUse` class to start the application.

## Database Setup

The ATM application uses a MySQL database to store user and transaction data. You should set up the database and configure the connection details in the DatabaseConnection class.
- Create a MySQL database named atm_app_db.
- Import the provided SQL schema file (atm_app_db.sql) to create the necessary tables and initial data.
- Configure the database connection details in the DatabaseConnection class (DB_URL, DB_USERNAME, DB_PASSWORD) to match your MySQL setup.


## Technologies Used

- Java
- MySQL
- JDBC
- BCrypt

## Project Structure
com.atm: Contains classes related to ATM functionality.
com.bank: Contains classes related to user accounts and transactions.
com.db: Contains classes for database connection and operations.
com.security: Contains classes related to security and OTP generation.
db_resources: Contains the SQL script for creating database tables.

## Maven Dependency:
jbcrypt-0.4.jar: A library for handling password hashing and checking.
mysql-connector-j-8.0.33.jar: The MySQL JDBC driver for database connectivity.


## Acknowledgements

This project was created as a learning exercise and is not intended for production use.
