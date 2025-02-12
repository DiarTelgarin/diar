package com.example.Vehicle;

import com.example.Database.PostgreSQLJDBC;

import java.sql.*;

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

    @Override
    public String toString() {
        return "Car: " + super.toString();
    }

    public void saveToDatabase() {
        String vehicleQuery = "INSERT INTO vehicles (brand, rental_count, is_available, vehicle_count) VALUES (?, ?, ?, ?) RETURNING vehicle_id";
        String carQuery = "INSERT INTO cars (vehicle_id, seat_count, color, fuel_type) VALUES (?, ?, ?, ?)";

        try (Connection conn = PostgreSQLJDBC.connect();
             PreparedStatement vehicleStmt = conn.prepareStatement(vehicleQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement carStmt = conn.prepareStatement(carQuery)) {

            // Вставка в таблицу vehicles
            vehicleStmt.setString(1, getBrand());
            vehicleStmt.setInt(2, getRentalCount());
            vehicleStmt.setBoolean(3, isAvailable());
            vehicleStmt.setInt(4, getVehicleCount());
            vehicleStmt.executeUpdate();

            ResultSet rs = vehicleStmt.getGeneratedKeys();
            if (rs.next()) {
                setVehicleId(rs.getInt(1));
            }

            carStmt.setInt(1, getVehicleId());
            carStmt.setInt(2, seatCount);
            carStmt.setString(3, color);
            carStmt.setString(4, fuelType);
            carStmt.executeUpdate();

            System.out.println("Car saved to database.");
        } catch (SQLException e) {
            System.out.println("Error saving car: " + e.getMessage());
        }
    }

    public static car getFromDatabase(int vehicleId) {
        String query = "SELECT v.vehicle_id, v.brand, v.rental_count, v.is_available, v.vehicle_count, " +
                "c.seat_count, c.color, c.fuel_type FROM vehicles v " +
                "JOIN cars c ON v.vehicle_id = c.vehicle_id WHERE v.vehicle_id = ?";
        try (Connection conn = PostgreSQLJDBC.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, vehicleId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new car(
                        rs.getInt("vehicle_id"),
                        rs.getString("brand"),
                        rs.getInt("rental_count"),
                        rs.getBoolean("is_available"),
                        rs.getInt("vehicle_count"),
                        rs.getInt("seat_count"),
                        rs.getString("color"),
                        rs.getString("fuel_type")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching car: " + e.getMessage());
        }
        return null;
    }
}
