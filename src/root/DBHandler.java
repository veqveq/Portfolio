package root;

import com.mysql.jdbc.Driver;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class DBHandler {

//    private Connection connection;

    public DBHandler() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            DriverManager.registerDriver(new Driver());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Set<AuthService.Record> getRecords() {
        Connection connection = DBConnector.getConnection();
        try {
            return getDataBase();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public AuthService.Record getUser(String login, String password){
        AuthService.Record record = null;
        Connection connection = DBConnector.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM RECORDS WHERE (login = ?) AND (password = ?)");
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                record = (new AuthService.Record(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("avatar")
                )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Statement error");
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return record;
        }
    }

    public void setUser(String name, String login, String password, String avatar) {
        Connection connection = DBConnector.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO RECORDS (name, login, password, avatar) VALUES (?, ?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, login);
            statement.setString(3, password);
            statement.setString(4, avatar);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

//    private void openConnection() {
//        try {
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/webchat?serverTimezone=Europe/Moscow", "root", "0000");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//            throw new RuntimeException("Driver Registration error");
//        }
//    }

//    private void closeConnection() {
//        try {
//            connection.close();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }

    private Set<AuthService.Record> getDataBase(){
        Connection connection = DBConnector.getConnection();
        Set<AuthService.Record> records = new HashSet<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM RECORDS");
            while (resultSet.next()) {
                records.add(new AuthService.Record(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("login"),
                                resultSet.getString("password"),
                                resultSet.getString("avatar")
                        )
                );
            }
            return records;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("Statement error");
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}