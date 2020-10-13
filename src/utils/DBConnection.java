package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

//    Server name: wgudb.ucertify.com
//    Port: 3306
//    Database name: WJ07E6m
//    Username: U07E6m
//    Password: 53689000355

    // JDBC Settings
    private static final String protocol = "jdbc:";
    private static final String vendor = "mysql:";
    private static final String address = "//wgudb.ucertify.com:3306/WJ07E6m";

    // JDBC url
    private static final String jdbcURL = protocol + vendor + address;

    // Driver and Connection reference
    private static final String MYSQLJDBCDRIVER = "com.mysql.jdbc.Driver";
    private static Connection conn = null;

    // Login
    private static final String username = "U07E6m";
    private static final String password = "53689000355";


    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDRIVER);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection is successful.");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;

    }

    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
