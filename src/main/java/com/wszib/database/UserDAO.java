package com.wszib.database;

import com.wszib.core.Authenticator;
import com.wszib.model.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO {
    private final Connection connection;
    private static final UserDAO instance = new UserDAO();

    private UserDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librarydb",
                    "root",
                    "");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findByLogin(String login) {
        try {
            String sql = "SELECT * FROM tuser WHERE login = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new User(
                        rs.getString("login"),
                        DigestUtils.md5Hex(rs.getString("password") + Authenticator.getInstance().getSeed()),
                        User.Role.valueOf(rs.getString("role")),
                        rs.getString("first_name"),
                        rs.getString("last_name")));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static UserDAO getInstance() {
        return instance;
    }

    public List<User> getUsers() { //sql -> java
        ArrayList<User> users = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tuser";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new User(
//                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        User.Role.valueOf(rs.getString("role")),
                        rs.getString("first_name"),
                        rs.getString("last_name"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
    public void register(User user) {
        try {
            String sql = "INSERT INTO tuser (login,password,role,first_name,last_name) VALUES (?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole().toString());
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getLastName());

            ps.executeUpdate();
//            ResultSet rs = ps.getGeneratedKeys();
//            if (rs.next())
//                user.setId(rs.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
