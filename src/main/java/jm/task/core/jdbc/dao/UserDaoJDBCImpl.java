package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Util connection = new Util();
        try (Statement statement = connection.getConnection().createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS users(id INT NOT NULL AUTO_INCREMENT, " +
                    "name VARCHAR(45) NOT NULL, " +
                    "lastName VARCHAR(45) NOT NULL, " +
                    "age INT(127) NOT NULL, " +
                    "PRIMARY KEY (id));");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы. " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        Util connection = new Util();
        try (Statement statement = connection.getConnection().createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users;");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы. " + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Util connection = new Util();
        PreparedStatement preparedStatement;
        try  {
            preparedStatement = connection.getConnection().prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании юзера. " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        Util connection = new Util();
        try (Statement statement = connection.getConnection().createStatement()) {
            statement.executeUpdate("DELETE FROM users WHERE id = " + id);
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении юзера. " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        Util connection = new Util();
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.getConnection().createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении таблицы. " + e.getMessage());
        }
        return userList;
    }

    public void cleanUsersTable() {
        Util connection = new Util();
        try (Statement statement = connection.getConnection().createStatement()) {
            statement.executeUpdate("DELETE FROM users;");
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке таблицы. " + e.getMessage());
        }
    }
}
