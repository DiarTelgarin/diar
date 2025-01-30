package User;

import Database.PostgreSQLJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class user {
    private int userId;
    private String name;
    private String surname;

    public user(int userId, String name, String surname) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void saveToDatabase() {
        String query = "INSERT INTO users (name, surname) VALUES (?, ?)";
        try (Connection conn = PostgreSQLJDBC.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.executeUpdate();
            System.out.println("User saved to database.");
        } catch (SQLException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    public static user getFromDatabase(int userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = PostgreSQLJDBC.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new user(rs.getInt("user_id"), rs.getString("name"), rs.getString("surname"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user: " + e.getMessage());
        }
        return null;
    }

    public void displayUserDetails() {
        System.out.println("User ID: " + userId);
        System.out.println("Name: " + name);
        System.out.println("Surname: " + surname);
    }
}
