package User;

import Database.PostgreSQLJDBC;
import java.sql.*;

public class admin extends user {
    private String email;
    private String password;
    private boolean isAdmin;

    public admin(int userId, String name, String surname, String email, String password, boolean isAdmin) {
        super(userId, name, surname);
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public static admin getAdmin(String email, String password) {
        String query = "SELECT user_id, name, surname, email, password, is_admin FROM users WHERE email = ? AND password = ? AND is_admin = TRUE";
        try (Connection conn = PostgreSQLJDBC.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new admin(
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
}
