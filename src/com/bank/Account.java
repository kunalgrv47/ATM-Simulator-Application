package com.bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.atm.UserInterface;
import com.db.DatabaseConnection;

public class Account {

	private String accountNumber;
	private double balance;
	private int userId; // To store the user ID associated with the account
	private UserInterface userInterface = new UserInterface();
	private DatabaseConnection databaseConnection = new DatabaseConnection();

	public Account(String accountNumber, double balance) {
		this.accountNumber = accountNumber;
		this.balance = balance;
	}

	public boolean deposit(double amount) {
		
		if(amount <= 0) {
			userInterface.displayMessage("============================================");
			userInterface.displayMessage("Transaction failed. Please enter a valid amount for deposit");
			userInterface.displayMessage("============================================");
			return false;
		}
		
		if((this.balance+amount) > 99999999999999.99) {
			userInterface.displayMessage("============================================");
			userInterface.displayMessage("Transaction failed. You are trying to deposit an amount which exceeds your bank account limit");
			userInterface.displayMessage("============================================");
			return false;
		}
		
		
		this.balance = balance + amount;
		userInterface.displayMessage("============================================");
		userInterface.displayMessage("Money deposited successfully. Available balance: Rs."+getBalance());
		userInterface.displayMessage("============================================");
		return true;
	}

	public boolean withdraw(double amount) {
		
		if(amount <= 0) {
			userInterface.displayMessage("============================================");
			userInterface.displayMessage("Transaction failed. Please enter a valid amount for withdrawal");
			userInterface.displayMessage("============================================");
			return false;
		}
		
		if(amount > balance) {
			userInterface.displayMessage("============================================");
			userInterface.displayMessage("Isufficient Balance.	Available balance: Rs."+getBalance());
			userInterface.displayMessage("============================================");
			return false;
		}
		this.balance = balance - amount;
		userInterface.displayMessage("============================================");
		userInterface.displayMessage("Please collect your cash Rs."+amount +"  Available balance: Rs."+getBalance());
		userInterface.displayMessage("============================================");
		return true;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}



	// Account class
	public void updateBalanceInDatabase() {
		try (Connection connection = databaseConnection.getConnection()) {
			String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDouble(1, this.balance);
			preparedStatement.setString(2, this.accountNumber);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
