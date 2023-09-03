package com.atm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.bank.Transaction;
import com.bank.User;
import com.db.DatabaseConnection;
import com.security.OTPGenerator;
import com.security.SimpleOTPGenerator;


public class AccountManager {

	private UserInterface userInterface = new UserInterface();
	private OTPGenerator otpGenerator = new SimpleOTPGenerator();
	private DatabaseConnection databaseConnection = new DatabaseConnection();



	public void provideServices(User user) {

		while(true) {
			userInterface.displayMessage("1. Check Balance");
			userInterface.displayMessage("2. Withdraw");
			userInterface.displayMessage("3. Deposite");
			userInterface.displayMessage("4. Transaction History");
			userInterface.displayMessage("5. Logout");
			userInterface.displayMessage("============================================");
			String choice = userInterface.getInput("Enter your choice: ");

			switch(choice){
			case "1":
				userInterface.displayMessage("============================================");
				userInterface.displayMessage("Available Balance: Rs."+user.getAccount().getBalance());
				userInterface.displayMessage("============================================");
				break;

			case "2":
				withdrawMoney(user);
				break;

			case "3":
				depositeMoney(user);
				break;

			case "4":
				displayTransactionHistory(user);
				break;

			case "5":
				userInterface.displayMessage("============================================");
				userInterface.displayMessage("Logging out");
				userInterface.displayMessage("============================================");
				return;

			default:
				userInterface.displayMessage("============================================");
				userInterface.displayMessage("Invalid selection, Please enter a valid choice");
				userInterface.displayMessage("============================================");

			}
		}

	}



	private void withdrawMoney(User user) {
		double withdrawAmount = userInterface.getInputAsDouble("Enter Amount: ");

		String otp = otpGenerator.generateOTP();
		userInterface.displayMessage("OTP is : "+otp);
		String enteredOTP = userInterface.getInput("Enter the OTP sent on your registered mobile: ");


		if(!enteredOTP.equals(otp)) {
			//OTP not matched
			userInterface.displayMessage("============================================");
			userInterface.displayMessage("Incorrect OTP. Transaction failed, Please try again");
			userInterface.displayMessage("============================================");
			return;
		}

		//OTP Matched
		boolean isWithdrawn = user.getAccount().withdraw(withdrawAmount);

		if(isWithdrawn) {
			//balance updating to database
			user.getAccount().updateBalanceInDatabase(); 
			
			// Create a withdrawal transaction and insert it into the database
			LocalDateTime currentTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy E hh:mm:ss a");
			String currentTimeStr = currentTime.format(formatter);
			Transaction withdrawalTransaction = new Transaction(user.getUserId(), "Withdrawal", withdrawAmount, currentTimeStr);
			databaseConnection.insertTransaction(withdrawalTransaction);
		}

	}



	private void depositeMoney(User user) {
		double depositAmount = userInterface.getInputAsDouble("Enter Amount: ");
		boolean isDeposited = user.getAccount().deposit(depositAmount);

		if(isDeposited) {
			//balance updating to database
			user.getAccount().updateBalanceInDatabase();
			
			// Create a deposit transaction and insert it into the database
			LocalDateTime currentTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy E hh:mm:ss a");
			String currentTimeStr = currentTime.format(formatter);
			Transaction depositTransaction = new Transaction(user.getUserId(), "Deposit", depositAmount, currentTimeStr);
			databaseConnection.insertTransaction(depositTransaction);
		}

	}



	public void displayTransactionHistory(User user) {
		List<Transaction> transactions = databaseConnection.getTransactionHistory(user.getUserId());

		if (transactions.isEmpty()) {
			userInterface.displayMessage("============================================");
			userInterface.displayMessage("No transactions found.");
			userInterface.displayMessage("============================================");
		} else {
			userInterface.displayMessage("============================================");
			userInterface.displayMessage("	RECENT TRANSACTION DETAILS");
			userInterface.displayMessage("============================================");
			int serialNo = 1;
			for (int i=transactions.size()-1; i>=0; i--) {
				userInterface.displayMessage(String.format("%-4d%-12s	Rs.%-20.2f	%s", serialNo, transactions.get(i).getType(), transactions.get(i).getAmount(), transactions.get(i).getTime()));
				serialNo++;
			}
			userInterface.displayMessage("============================================\n");

		}
	}





}
