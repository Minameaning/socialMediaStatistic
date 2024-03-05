package com.example.socialmediastatistic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/socialMedia";
    private static final String USER = "root";
    private static final String PASS = "0851379225mean";

    public Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
}
