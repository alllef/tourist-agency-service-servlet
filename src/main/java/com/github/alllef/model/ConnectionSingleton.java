package com.github.alllef.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionSingleton {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream("db.properties"));
                connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"), properties.getProperty("password"));
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
