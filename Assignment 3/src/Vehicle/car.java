package Vehicle;

import Database.PostgreSQLJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class car extends vehicle {
    private int seatCount;
    private String color;
    private String fuelType;

    public car(int vehicleId, String brand, int rentalCount, boolean isAvailable, int vehicleCount, int seatCount, String color, String fuelType) {
        super(vehicleId, brand, rentalCount, isAvailable, vehicleCount);
        this.seatCount = seatCount;
        this.color = color;
        this.fuelType = fuelType;
    }

    @Override
    public void showVehicleInfo() {
        super.showVehicleInfo();
        System.out.println("Type: Car");
        System.out.println("Seat Count: " + seatCount);
        System.out.println("Color: " + color);
        System.out.println("Fuel Type: " + fuelType);
    }

    public void saveToDatabase() {
        String vehicleQuery = "INSERT INTO vehicles (brand, rental_count, is_available, vehicle_count) VALUES (?, ?, ?, ?) RETURNING vehicle_id";
        String carQuery = "INSERT INTO cars (vehicle_id, seat_count, color, fuel_type) VALUES (?, ?, ?, ?)";
        try (Connection conn = PostgreSQLJDBC.connect()) {
            // Сохраняем транспортное средство
            try (PreparedStatement vehicleStmt = conn.prepareStatement(vehicleQuery)) {
                vehicleStmt.setString(1, getBrand());
                vehicleStmt.setInt(2, getRentalCount());
                vehicleStmt.setBoolean(3, isAvailable());
                vehicleStmt.setInt(4, getVehicleCount());
                ResultSet rs = vehicleStmt.executeQuery();
                if (rs.next()) {
                    setVehicleId(rs.getInt("vehicle_id"));
                }
            }

            // Сохраняем автомобиль
            try (PreparedStatement carStmt = conn.prepareStatement(carQuery)) {
                carStmt.setInt(1, getVehicleId());
                carStmt.setInt(2, seatCount);
                carStmt.setString(3, color);
                carStmt.setString(4, fuelType);
                carStmt.executeUpdate();
                System.out.println("Car saved to database.");
            }
        } catch (SQLException e) {
            System.out.println("Error saving car: " + e.getMessage());
        }
    }
}
