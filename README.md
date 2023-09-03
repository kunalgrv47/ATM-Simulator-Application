# ATM-Simulator-Application

A simple Java-based ATM (Automated Teller Machine) simulator that allows users to perform banking operations, manage accounts, and view transaction history.

## Features

- User Registration and Login
- Balance Enquiry
- Deposit and Withdrawal
- Transaction History
- MySQL Database Integration using JDBC
- Secure PIN Handling: Utilizes BCrypt for secure PIN encryption
- OTP Generation: Generates One-Time Passwords for added security

## Prerequisites

Before running the ATM application, make sure you have the following prerequisites installed:

- Java Development Kit (JDK) 8 or later
- Eclipse IDE (or your preferred Java IDE)
- MySQL Database

## Getting Started

To get started with the ATM SIMULATOR APPLICATION, follow these steps:

1. Clone this repository to your local machine.   git clone https://github.com/kunalgrv47/ATM-Simulator-Application.git
2. Set up the MySQL database as mentioned in the next heading.
3. Import the project into your preferred Java IDE (e.g., Eclipse).
4. Configure the database connection in the `DatabaseConnection` class. Update the DB_URL, DB_USERNAME, and DB_PASSWORD with your database URL, username, and password, respectively
5. Run the `ATMUse` class to start the application.

## Database Setup

Follow these steps to set up the MySQL database for the ATM SIMULATOR APPLICATION:
1. Install MySQL
2. Log in to MySQL
3. Run the SQL Script:
   Inside the ATM SIMULATOR APPLICATION project directory, you'll find a SQL script named create_table.sql in the db_resources directory. Run this script to create the necessary tables and initial values.
   


## Technologies Used

- Java
- MySQL
- JDBC
- BCrypt


## Acknowledgements

This project was created as a learning exercise and is not intended for production use.
