package com.github.alllef.model.dao;

import com.github.alllef.model.ConnectionSingleton;
import com.github.alllef.model.entity.User;
import com.github.alllef.utils.enums.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO extends AbstractDAO<User> {
    private static UserDAO userDAO = null;

    protected static UserDAO getInstance() {
        if (userDAO == null)
            userDAO = new UserDAO(ConnectionSingleton.getConnection());

        return userDAO;
    }

    private UserDAO(Connection connection) {
        super("users", connection);
    }

    @Override
    public Optional<User> findById(long id) throws SQLException {
        String findById = "select * from users where user_id=?";
        try (PreparedStatement pstmt = con.prepareStatement(findById)) {
            pstmt.setLong(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next())
                return Optional.of(mapToEntity(resultSet));
        }
        return Optional.empty();
    }

    @Override
    public void update(User entity) {
        String updatePositionSql = String.format("""
                UPDATE %s
                 SET user_type=?,
                 first_name=?,
                 last_name=?,
                 email=?,
                 user_password=?,
                 is_blocked=?
                 WHERE user_id=?""", tableName);
        try (PreparedStatement pstmt = con.prepareStatement(updatePositionSql)) {
            pstmt.setString(1, entity.getUserType().toString());
            pstmt.setString(2, entity.getFirstName());
            pstmt.setString(3, entity.getLastName());
            pstmt.setString(4, entity.getEmail());
            pstmt.setString(5, entity.getPassword());
            pstmt.setBoolean(6, entity.isBlocked());
            pstmt.setLong(7, entity.getUserId());
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("Watafuck");
            throwables.printStackTrace();
        }
    }

    public User findUserByEmail(String email) {
        String findByEmailSQL = "select * from users where email =?";
        try (PreparedStatement pstmt = con.prepareStatement(findByEmailSQL)) {
            pstmt.setString(1, email);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next())
                return mapToEntity(resultSet);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<User> findUsersByType(UserType userType) {
        List<User> usersList = new ArrayList<>();

        String findByEmailSQL = "select * from users where user_type =?";
        try (PreparedStatement pstmt = con.prepareStatement(findByEmailSQL)) {
            pstmt.setString(1, userType.toString());
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next())
                usersList.add(mapToEntity(resultSet));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return usersList;
    }

    @Override
    public void create(User entity) {
        String insertSQL = String.format("""
                insert into %s(user_type,first_name,last_name,email,user_password)
                  values(?,?,?,?,?)""", tableName);
        try (PreparedStatement pstmt = con.prepareStatement(insertSQL)) {
            pstmt.setString(1, entity.getUserType().toString());
            pstmt.setString(2, entity.getFirstName());
            pstmt.setString(3, entity.getLastName());
            pstmt.setString(4, entity.getEmail());
            pstmt.setString(5, entity.getPassword());
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String deleteQuery = String.format("DELETE FROM %s where user_id=?", tableName);
        try (PreparedStatement pstmt = con.prepareStatement(deleteQuery)) {
            pstmt.setLong(1, id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public User mapToEntity(ResultSet resultSet) throws SQLException {
        return User.builder()
                .userId(resultSet.getLong("user_id"))
                .email(resultSet.getString("email"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .userType(UserType.valueOf(resultSet.getString("user_type")))
                .password(resultSet.getString("user_password"))
                .isBlocked(resultSet.getBoolean("is_blocked"))
                .build();
    }
}
