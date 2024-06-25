package co.edu.poligran.server.clients;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseClient {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/employee_socket";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "password";

    public Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error database connection " + e.getMessage());
        }

        return connection;
    }
}
