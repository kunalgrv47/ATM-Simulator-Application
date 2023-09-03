package com.bank;

public class Transaction {
	
	private int userId;
	private String type; // "Withdrawal" or "Deposit" or other transaction types
    private double amount;
    private String time;
    
    
    public Transaction(int userId, String type, double amount, String time) {
      this.userId = userId;
      this.type = type;
      this.amount = amount;
      this.time = time;
    }
    
    
    //Getters and Setters
    public int getUserId() {
    	return userId;
    }

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}
	


}
