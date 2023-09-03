package com.atm;

import com.bank.User;

public class ATM {

	private UserInterface userInterface;
	private UserManager userManager;
	private AccountManager accountManager;


	public ATM() {
		userInterface = new UserInterface();
		userManager = new UserManager();
		accountManager = new AccountManager();
	}


	public void start() {
		while (true) {
			userInterface.displayMessage("-------- WELCOME TO ATM INTERFACE ---------");
			userInterface.displayMessage("1. Existing User");
			userInterface.displayMessage("2. New User (Create Account)");
			userInterface.displayMessage("3. Exit");
			userInterface.displayMessage("============================================");
			String choice = userInterface.getInput("Enter your choice: ");

			switch (choice) {
			case "1":
				User user = userManager.existingUserLogin();
				if(user != null) {
					accountManager.provideServices(user);
				}
				break;
				
			case "2":
				userManager.createUser();
				break;
				
			case "3":
				userInterface.displayMessage("============================================");
				userInterface.displayMessage("Session Ended");
				userInterface.displayMessage("============================================");
				return;
				
			default:
				userInterface.displayMessage("============================================");
				userInterface.displayMessage("Invalid choice");
				userInterface.displayMessage("============================================");
			}
		}
	}
}
