package com.bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.db.DatabaseConnection;


public class AccountNumberGenerator {

	private String prefix;
	private int counter1;
	private int counter2;
	private Random random = new Random();
	private DatabaseConnection databaseConnection = new DatabaseConnection();


	public AccountNumberGenerator() {
		retrieveCountersFromDatabase();
	}
	
	public String generateNumber() {
        if (counter2 > 99999) {
            counter2 = 0;
            counter1++;
        }

        String counter1Str = String.format("%05d", counter1);
        String counter2Str = String.format("%05d", counter2);
        String randomStr = generateRandomDigits(4);

        counter2++;
        
        saveCountersToDatabase();

        return prefix + counter1Str + counter2Str + randomStr;
    }

    private String generateRandomDigits(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }
	
	

	private void retrieveCountersFromDatabase() {
		try (Connection connection = databaseConnection.getConnection()) {
			String sql = "SELECT prefix, counter1, counter2 FROM counters WHERE id = 1"; 
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				prefix = resultSet.getString("prefix");
				counter1 = resultSet.getInt("counter1");
				counter2 = resultSet.getInt("counter2");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	private void saveCountersToDatabase() {
        try (Connection connection = databaseConnection.getConnection()) {
            String sql = "UPDATE counters SET prefix = ?, counter1 = ?, counter2 = ? WHERE id = 1";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, prefix);
            preparedStatement.setInt(2, counter1);
            preparedStatement.setInt(3, counter2);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
