package com.bank;

public class User {

	private String name;
	private String userName;
	private String hashedPin;
	private String salt;
	private Account account; // User has an associated account
	private int userId;


	public User(String name, String userName, String hashedPin, String salt) {
		this.name = name;
		this.userName = userName;
		this.hashedPin = hashedPin;
		this.salt = salt;
	}

	//Getters and Setters for name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	//Getters and Setters for username
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}


	//Getters and Setters for hashed pin
	public String getHashedPin() {
		return hashedPin;
	}
	public void setHashedPin(String hashedPin) {
		this.hashedPin = hashedPin;
	}


	//Getters and Setters for account
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}


	//Getters and Setters for salt
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}


	//Getters and Setters for userId
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}




}
