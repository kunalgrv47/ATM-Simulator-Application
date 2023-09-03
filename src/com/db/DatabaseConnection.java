package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import com.bank.Account;
import com.bank.Transaction;
import com.bank.User;

public class DatabaseConnection {

	// Database connection details
	private static final String DB_URL = "jdbc:mysql://localhost:3306/atm_app_db";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "1994";



	// Method inserts a User and Account and return true if inserted successfully
	public boolean insertUser(User user) {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String sql = "INSERT INTO users (name, username, hashed_Pin, salt) VALUES (?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getUserName());
			preparedStatement.setString(3, user.getHashedPin());
			preparedStatement.setString(4, user.getSalt());

			int rowsInserted = preparedStatement.executeUpdate();
			if (rowsInserted > 0) {
				ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
				if (generatedKeys.next()) {
					int userId = generatedKeys.getInt(1);
					user.getAccount().setUserId(userId); // Set the user ID for the account
					return insertAccount(user.getAccount());
				}
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	//Helper function for inserting account to database
	private boolean insertAccount(Account account) {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String sql = "INSERT INTO accounts (account_number, balance, user_id) VALUES (?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, account.getAccountNumber());
			preparedStatement.setDouble(2, account.getBalance());
			preparedStatement.setInt(3, account.getUserId()); // Use the associated user ID
			int rowsInserted = preparedStatement.executeUpdate();
			return rowsInserted > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}



	//Method to insert transaction
	public boolean insertTransaction(Transaction transaction) {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
	        String sql = "INSERT INTO transactions (user_id, type, amount, time) VALUES (?, ?, ?, ?)";
	        PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setInt(1, transaction.getUserId());
	        preparedStatement.setString(2, transaction.getType());
	        preparedStatement.setDouble(3, transaction.getAmount());
	        preparedStatement.setString(4, transaction.getTime());
	        int rowsInserted = preparedStatement.executeUpdate();
	        return rowsInserted > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}


	//Method to check if a username is already taken
	public boolean isUsernameTaken(String username) {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int count = resultSet.getInt(1);
				return count > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; // In case of an error or no records found

	}


	//Method to retrieve user from database using username
	public User getUserByUserName(String userName) {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String sql = "SELECT user_id, name, username, hashed_pin, salt FROM users WHERE username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userName);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int userId = resultSet.getInt("user_id");
				String name = resultSet.getString("name");
				String retrievedUserName = resultSet.getString("username");
				String hashedPin = resultSet.getString("hashed_pin");
				String salt = resultSet.getString("salt");

				// Create and return the User object
				User user = new User(name, retrievedUserName, hashedPin, salt);
				user.setUserId(userId);

				// Fetch and set the associated account
				Account account = getAccountByUserId(userId);
				user.setAccount(account);

				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//Helper Method to find Account by UserId
	private Account getAccountByUserId(int userId) {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String sql = "SELECT account_number, balance FROM accounts WHERE user_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, userId);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String accountNumber = resultSet.getString("account_number");
				double balance = resultSet.getDouble("balance");

				// Create and return the Account object
				return new Account(accountNumber, balance);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; // In case of an error or no matching account found
	}
	
	
	//Method to get transaction history
	public List<Transaction> getTransactionHistory(int userId) {
	    List<Transaction> transactions = new ArrayList<>();
	    try (Connection connection = getConnection()) {
	        String sql = "SELECT user_id, type, amount, time FROM transactions WHERE user_id = ?";
	        PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setInt(1, userId);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	            String type = resultSet.getString("type");
	            double amount = resultSet.getDouble("amount");
	            String time = resultSet.getString("time");
	            int transactionUserId = resultSet.getInt("user_id");

	            Transaction transaction = new Transaction(transactionUserId, type, amount, time);
	            transactions.add(transaction);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return transactions;
	}

	
	

	//Establish Connection with database
	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}


	//Terminate connection with database
	public void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
