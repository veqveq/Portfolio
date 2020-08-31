package root;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/webchat?serverTimezone=Europe/Moscow", "root", "0000");
        } catch (SQLException throwables) {
            throw new RuntimeException("Ошибка соединения");
        }
    }
}
