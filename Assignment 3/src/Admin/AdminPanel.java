package Admin;

import Database.PostgreSQLJDBC;
import User.email;
import java.sql.*;
import java.util.Scanner;

public class AdminPanel {
    public static void showAdminMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter admin email: ");
        String adminEmail = scanner.nextLine().trim();
        System.out.print("Enter admin password: ");
        String adminPassword = scanner.nextLine().trim();

        email admin = email.getAdminFromDatabase(adminEmail, adminPassword);

        if (admin == null || !admin.isAdmin()) {
            System.out.println("Access denied! You are not an admin.");
            return;
        }

        System.out.println("\nWelcome, Admin!");
        while (true) {
            System.out.println("\nAdmin Panel:");
            System.out.println("1. View all users");
            System.out.println("2. Delete a user");
            System.out.println("3. View all vehicles");
            System.out.println("4. Delete a vehicle");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    System.out.print("Enter user ID to delete: ");
                    if (scanner.hasNextInt()) {
                        int userId = scanner.nextInt();
                        deleteUser(userId);
                    } else {
                        System.out.println("Invalid input. Please enter a valid user ID.");
                        scanner.next();
                    }
                    break;
                case 3:
                    viewAllVehicles();
                    break;
                case 4:
                    System.out.print("Enter vehicle ID to delete: ");
                    if (scanner.hasNextInt()) {
                        int vehicleId = scanner.nextInt();
                        deleteVehicle(vehicleId);
                    } else {
                        System.out.println("Invalid input. Please enter a valid vehicle ID.");
                        scanner.next();
                    }
                    break;
                case 5:
                    System.out.println("Exiting admin panel...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void viewAllUsers() {
        String query = "SELECT user_id, name, surname, email, is_admin FROM users";
        try (Connection conn = PostgreSQLJDBC.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\nList of users:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("user_id") +
                        ", Name: " + rs.getString("name") +
                        ", Surname: " + rs.getString("surname") +
                        ", Email: " + rs.getString("email") +
                        ", Admin: " + (rs.getBoolean("is_admin") ? "Yes" : "No"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching users: " + e.getMessage());
        }
    }

    public static void deleteUser(int userId) {
        String query = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = PostgreSQLJDBC.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }

    public static void viewAllVehicles() {
        String query = "SELECT vehicle_id, brand, is_available FROM vehicles";
        try (Connection conn = PostgreSQLJDBC.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\nList of vehicles:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("vehicle_id") +
                        ", Brand: " + rs.getString("brand") +
                        ", Available: " + (rs.getBoolean("is_available") ? "Yes" : "No"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching vehicles: " + e.getMessage());
        }
    }

    public static void deleteVehicle(int vehicleId) {
        String query = "DELETE FROM vehicles WHERE vehicle_id = ?";
        try (Connection conn = PostgreSQLJDBC.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, vehicleId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Vehicle deleted successfully.");
            } else {
                System.out.println("Vehicle not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting vehicle: " + e.getMessage());
        }
    }
}
