package services;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDb {
    private static String jdbcURL = "jdbc:mysql://localhost:3306/chat?useSSL=false";
    private static String jdbcUsername = "root";
    private static String jdbcPassword = "1234";

    public static java.sql.Connection getConnection() {
        java.sql.Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }
}
