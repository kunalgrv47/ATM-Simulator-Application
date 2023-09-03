package com.atm;

import java.util.Scanner;

public class UserInterface {
	
	private Scanner scanner = new Scanner(System.in);

    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int getInputAsInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(getInput(prompt));
            } catch (NumberFormatException e) {
            	displayMessage("==================================================");
                System.out.println("Invalid input. Please enter a valid integer.");
            	displayMessage("==================================================");
            }
        }
    }

    public double getInputAsDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(getInput(prompt));
            } catch (NumberFormatException e) {
            	displayMessage("=======================================================");
                System.out.println("Invalid input. Please enter a valid numeric value.");
            	displayMessage("=======================================================");
            }
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
