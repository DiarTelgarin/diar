package User;

import Database.PostgreSQLJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class email extends user {
    private String email;
    private String password;

    public email(int userId, String name, String surname, String email, String password) {
        super(userId, name, surname);
        this.email = email;
        this.password = password;
    }

    public static User.email getAdminFromDatabase(String adminEmail, String adminPassword) {
        return null;
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
            stmt.executeUpdate();
            System.out.println("Email user saved to database.");
        } catch (SQLException e) {
            System.out.println("Error saving email user: " + e.getMessage());
        }
    }

    public static email getFromDatabase(int userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
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
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching email user: " + e.getMessage());
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

    public boolean isAdmin() {
        return false;
    }
}
