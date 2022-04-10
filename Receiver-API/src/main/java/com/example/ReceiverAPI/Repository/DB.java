package com.example.ReceiverAPI.Repository;

import com.example.ReceiverAPI.Model.Transaction;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class DB {

    private Connection connection;
    private final String tableName = "Transaction";

    //    method to create a connection to database.
    public void getConnection() throws SQLException {
        if(connection == null) {
//            connect to the database of your choice with id and password.
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/natwest", "user", "password");
        }
    }

    //    method to close the connection
    public void closeConnection() {
        if(connection != null)
            connection = null;
    }

    public String insertTransaction(Transaction transaction) throws SQLException {
        getConnection();
        PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO "+tableName+" VALUES(NULL, ?, ?, ?, ?, ?)");
        prepareStatement.setLong(1, transaction.getAccountNumber());
        prepareStatement.setString(2, transaction.getType());
        prepareStatement.setLong(3, transaction.getAmount());
        prepareStatement.setString(4, transaction.getCurrency());
        prepareStatement.setLong(5, transaction.getAccountFrom());

        int row = prepareStatement.executeUpdate();
        String str = "";

        if(row >= 1)
            str = "Transaction added Successfully";
        else
            str = "Error during insertion";

        closeConnection();
        return str;
    }


}
