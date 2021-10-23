package com.c123.persistence;

import com.c123.db.MySQLConnectionPool;
import com.c123.model.User;
import com.c123.security.HashUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UserPersistenceLayer implements PersistenceLayer<User> {

    @Override
    public void create(User user) throws Exception {
        String sql = "INSERT INTO users(username, password, email) VALUES(?,?,?)";
        String hashedPassword = HashUtil.hashString(user.getPassword());

        try(Connection connection = MySQLConnectionPool.getConnection();
            PreparedStatement prepstmnt = connection.prepareStatement(sql)) {

            prepstmnt.setString(1,user.getUsername());
            prepstmnt.setString(2,hashedPassword);
            prepstmnt.setString(3,user.getEmail());

            prepstmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Collection<User> receive() {
        String sql = "SELECT * FROM users";

        try(Connection connection = MySQLConnectionPool.getConnection();
            PreparedStatement prepstmnt = connection.prepareStatement(sql)) {

            ResultSet resultSet = prepstmnt.executeQuery();
            List<User> list = new ArrayList<>();

            while(resultSet.next()) {
                list.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email")
                ));
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Optional<User> receiveById(int id) {
        String sql = "SELECT * FROM users WHERE id=?";

        try(Connection connection = MySQLConnectionPool.getConnection();
            PreparedStatement prepstmnt = connection.prepareStatement(sql)) {
            prepstmnt.setInt(1, id);

            ResultSet resultSet = prepstmnt.executeQuery();

            if(resultSet.next()) {
                return Optional.of(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email")
                ));
            }

            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Optional<User> receiveByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username=?";

        try(Connection connection = MySQLConnectionPool.getConnection();
            PreparedStatement prepstmnt = connection.prepareStatement(sql)) {
            prepstmnt.setString(1, username);

            ResultSet resultSet = prepstmnt.executeQuery();

            if(resultSet.next()) {
                return Optional.of(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email")
                ));
            }

            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Optional<User> receiveByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";

        try(Connection connection = MySQLConnectionPool.getConnection();
            PreparedStatement prepstmnt = connection.prepareStatement(sql)) {
            prepstmnt.setString(1, username);
            prepstmnt.setString(2, password);

            ResultSet resultSet = prepstmnt.executeQuery();

            if(resultSet.next()) {
                return Optional.of(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email")
                ));
            }

            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Optional<User> receiveByEmail(String email) {
        String sql = "SELECT * FROM users WHERE id=?";

        try(Connection connection = MySQLConnectionPool.getConnection();
            PreparedStatement prepstmnt = connection.prepareStatement(sql)) {
            prepstmnt.setString(1, email);

            ResultSet resultSet = prepstmnt.executeQuery();

            if(resultSet.next()) {
                return Optional.of(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email")
                ));
            }

            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void update(User user) throws SQLException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void deleteById(int id) throws SQLException {
        throw new RuntimeException("not implemented");
    }

}
