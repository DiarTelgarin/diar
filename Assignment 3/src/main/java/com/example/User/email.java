package com.example.User;

import com.example.Database.PostgreSQLJDBC;
import java.sql.*;

public class email extends user {
    private String email;
    private String password;
    private boolean isAdmin;

    public email(int userId, String name, String surname, String email, String password, boolean isAdmin) {
        super(userId, name, surname);
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void saveToDatabase() {
        String query = "INSERT INTO users (name, surname, email, password, is_admin) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = PostgreSQLJDBC.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, getName());
            stmt.setString(2, getSurname());
            stmt.setString(3, email);
            stmt.setString(4, password);
            stmt.setBoolean(5, isAdmin);
            stmt.executeUpdate();
            System.out.println("Email user saved to database.");
        } catch (SQLException e) {
            System.out.println("Error saving email user: " + e.getMessage());
        }
    }

    public static email getFromDatabase(int userId) {
        String query = "SELECT user_id, name, surname, email, password, is_admin FROM users WHERE user_id = ?";
        try (Connection conn = PostgreSQLJDBC.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new email(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getBoolean("is_admin")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching email user: " + e.getMessage());
        }
        return null;
    }

    public static email getAdminFromDatabase(String adminEmail, String adminPassword) {
        String query = "SELECT user_id, name, surname, email, password, is_admin FROM users WHERE email = ? AND password = ? AND is_admin = TRUE";

        try (Connection conn = PostgreSQLJDBC.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, adminEmail);
            stmt.setString(2, adminPassword);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new email(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getBoolean("is_admin")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching admin: " + e.getMessage());
        }
        return null;
    }

    public void secureLogin(String inputEmail, String inputPassword) throws Exception {
        if (!this.email.equals(inputEmail)) {
            throw new Exception("Email not found!");
        }
        if (!this.password.equals(inputPassword)) {
            throw new Exception("Incorrect password!");
        }
        System.out.println("Login Successful!");
    }
}
