package org.fuller.service;

import org.fuller.Main;
import org.fuller.bean.User;

import java.sql.*;

@Deprecated
public class UserService {

    public User getUserByEmail(String email) throws SQLException {
        try (Connection conn = Main.ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT id, password, createdAt FROM users WHERE email = ?")) {
                ps.setObject(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String name = rs.getString("name");
                        long id = rs.getLong("id");
                        String password = rs.getString("password");
                        long createdAt = rs.getLong("createdAt");
                        User user = new User(email, password, createdAt);
                        return user;
                    } else {
                        throw new RuntimeException("get user by email failed.");
                    }
                }
            }
        }
    }

    public User login(String email, String password) {
        try {
            User user = getUserByEmail(email);
            if (user.getPassword().equals(password)) {
                return user;
            } else {
                throw new RuntimeException("login failed(password != ?).");
            }
        } catch (SQLException e) {
            throw new RuntimeException("login failed.");
        }
    }

    public User register(String email, String password) throws SQLException {
        User user = new User(email, password, System.currentTimeMillis());
        try (Connection conn = Main.ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO users (email, password, createdAt) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                ps.setObject(1, user.getEmail());
                ps.setObject(2, user.getPassword());
                ps.setObject(3, user.getCreatedAt());
                int n = ps.executeUpdate();
                if (n != 1) {
                    throw new RuntimeException("register failed.");
                }
                System.out.println("update: " + n);
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        long id = rs.getLong(1);
                        user.setId(id);
                        System.out.println(id);
                    }
                }
            }
        }
        return user;
    }
}
