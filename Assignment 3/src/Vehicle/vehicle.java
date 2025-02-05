package Vehicle;

import Database.PostgreSQLJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class vehicle {
    private int vehicleId;
    private String brand;
    private int rentalCount;
    private boolean isAvailable;
    private int vehicleCount;

    public vehicle(int vehicleId, String brand, int rentalCount, boolean isAvailable, int vehicleCount) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.rentalCount = rentalCount;
        this.isAvailable = isAvailable;
        this.vehicleCount = vehicleCount;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getBrand() {
        return brand;
    }

    public int getRentalCount() {
        return rentalCount;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public void showVehicleInfo() {
        System.out.println("Vehicle ID: " + vehicleId);
        System.out.println("Brand: " + brand);
        System.out.println("Rental Count: " + rentalCount);
        System.out.println("Available: " + (isAvailable ? "Yes" : "No"));
        System.out.println("Vehicle Count: " + vehicleCount);
    }

    public void saveToDatabase() {
        String query = "INSERT INTO vehicles (brand, rental_count, is_available, vehicle_count) VALUES (?, ?, ?, ?)";
        try (Connection conn = PostgreSQLJDBC.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, brand);
            stmt.setInt(2, rentalCount);
            stmt.setBoolean(3, isAvailable);
            stmt.setInt(4, vehicleCount);
            stmt.executeUpdate();
            System.out.println("Vehicle saved to database.");
        } catch (SQLException e) {
            System.out.println("Error saving vehicle: " + e.getMessage());
        }
    }

    public static vehicle getFromDatabase(int vehicleId) {
        String query = "SELECT * FROM vehicles WHERE vehicle_id = ?";
        try (Connection conn = PostgreSQLJDBC.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, vehicleId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new vehicle(
                        rs.getInt("vehicle_id"),
                        rs.getString("brand"),
                        rs.getInt("rental_count"),
                        rs.getBoolean("is_available"),
                        rs.getInt("vehicle_count")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching vehicle: " + e.getMessage());
        }
        return null;
    }
}
