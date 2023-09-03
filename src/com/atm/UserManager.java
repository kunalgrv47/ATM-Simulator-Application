package com.atm;

import com.bank.AccountNumberGenerator;
import com.bank.User;
import com.bank.Account;
import com.db.DatabaseConnection;
import java.sql.Connection;
import org.mindrot.jbcrypt.BCrypt;


public class UserManager {

	private UserInterface userInterface = new UserInterface();
	private DatabaseConnection databaseConnection = new DatabaseConnection();

	//Function to create new User and Account
	public void createUser() {

		//Taking name, username, pin.
		String name = takeValidName();
		if(name == null) {
			return;
		}
		String userName = takeValidUserName();
		if(userName == null) {
			return;
		}
		String pin = takeValidPin();
		if(pin == null) {
			return;
		}


		//Encrypting pin
		String salt = BCrypt.gensalt();
		String hashedPin = BCrypt.hashpw(pin, salt);



		//Generating 16 digit Account Number using AccountNumGenerator class
		AccountNumberGenerator accountNumberGenerator = new AccountNumberGenerator();
		String accountNumber = accountNumberGenerator.generateNumber();



		//Creating User and Account
		User newUser = new User(name, userName, hashedPin, salt);
		Account newAccount = new Account(accountNumber, 0); //initial balance will be 0

		// Set the account for the user
		newUser.setAccount(newAccount);


		//Insert user into database
		if(!databaseConnection.insertUser(newUser)) {
			userInterface.displayMessage("============================================");
			userInterface.displayMessage("Something went wrong while creating Account. Please try again");
			userInterface.displayMessage("============================================");
			return;
		}
		userInterface.displayMessage("============================================");
		userInterface.displayMessage("ACCOUNT CREATED SUCCESSFULLY");
		userInterface.displayMessage("User Name: "+newUser.getUserName());
		userInterface.displayMessage("Account Number: "+newAccount.getAccountNumber());
		userInterface.displayMessage("============================================");
	}


	//Helper function for taking Name
	private String takeValidName() {

		String name = userInterface.getInput("Enter full name: ");

		// Validating the name criteria
		if (!name.matches("^[a-zA-Z0-9]+(\\s[a-zA-Z0-9]+)*$")) {
			userInterface.displayMessage("============================================");
			userInterface.displayMessage("Invalid name format. Please use only letters and numbers and name cannot start or end with space.");
			userInterface.displayMessage("============================================");
			return null;
		}
		return name;
	}


	//Helper function for taking userName
	private String takeValidUserName() {

		//Taking UserName
		String userName = userInterface.getInput("Enter desired User Name (Upto 18 character alphanumeric): ");

		//Checking database connectivity to ensure if there is any issue in connection
		Connection con = databaseConnection.getConnection();
		if(con == null) {
			userInterface.displayMessage("============================================");
			userInterface.displayMessage("There is some issue with database connectivity. Please try after some time or raise a complaint at support@myatm.com");
			userInterface.displayMessage("============================================");
			return null;
		}else {
			databaseConnection.closeConnection(con);
		}

		//Checking database if the user exists
		while (databaseConnection.isUsernameTaken(userName)) {
			userInterface.displayMessage("============================================");
			userInterface.displayMessage("User Name is already used.");
			userInterface.displayMessage("============================================");
			userInterface.displayMessage("1. Try a different User Name");
			userInterface.displayMessage("2. Exit");
			userInterface.displayMessage("============================================");

			String option = userInterface.getInput("Enter your choice: ");
			switch (option) {
			case "1":
				userName = userInterface.getInput("Enter a different User Name: ");
				break;

			case "2":
				userInterface.displayMessage("Exiting account creation.");
				return null;

			default:
				userInterface.displayMessage("Invalid selection");
			}
		}

		return userName;
	}


	//Helper function for taking pin
	private String takeValidPin() {

		//Taking pin from customer
		String pin = userInterface.getInput("Enter desired PIN: ");

		// Validating the pin criteria.
		if (!pin.matches("\\d{4}")) {
			userInterface.displayMessage("============================================");
			userInterface.displayMessage("Invalid PIN format. Please enter a 4-digit PIN.");
			userInterface.displayMessage("============================================");
			return null;
		}

		return pin;
	}
	
	
	
	
	//Method to validate user and return the validated user
	public User existingUserLogin() {
		String userName = userInterface.getInput("Enter your username: ");
		String enteredPin = userInterface.getInput("Enter your PIN: ");

		User user = databaseConnection.getUserByUserName(userName);

		//If no user found
		if (user == null) {
			userInterface.displayMessage("============================================");
			userInterface.displayMessage("No user found. Please enter valid username");
			userInterface.displayMessage("============================================");
			return null;
		}

		// Verify the entered PIN by hashing it with the stored salt and comparing with the stored hash
		String hashedPinInDB = user.getHashedPin();
		if (!BCrypt.checkpw(enteredPin, hashedPinInDB)) {
			// Authentication failed
			userInterface.displayMessage("============================================");
			userInterface.displayMessage("Invalid credentials. Please try again");
			userInterface.displayMessage("============================================");
			return null;
		}

		//Authentication successful
		userInterface.displayMessage("============================================");
		userInterface.displayMessage("	Welcome Mr."+user.getName());
		userInterface.displayMessage("============================================");
		
		return user;

		

	}



}
