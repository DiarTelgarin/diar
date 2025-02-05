package User;

import Database.PostgreSQLJDBC;
import java.sql.*;

public class admin extends user {
    private String admin_name;
    private String admin_password;
    private boolean isAdmin;

    public admin(int userId, String name, String surname, String email, String password) {
        super(userId, name, surname);
        this.admin_name = email;
        this.admin_password = password;
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public static admin getAdmin(String admin_name, String admin_password) {
        String query = "SELECT * FROM users WHERE admin_name = ? AND admin_password = ? AND isAdmin = TRUE";
        try (Connection conn = PostgreSQLJDBC.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, admin_name);
            stmt.setString(2, admin_password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new admin(
                        rs.getInt("user_Id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("password"),
                        rs.getString("isAdmin")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching admin: " + e.getMessage());
        }
        return null;
    }
}


