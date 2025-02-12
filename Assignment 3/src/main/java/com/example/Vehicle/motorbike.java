package com.example.Vehicle;

import com.example.Database.PostgreSQLJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class motorbike extends vehicle {
    private int maxSpeed;
    private boolean hasHelmetIncluded;
    private int engineCapacity;


    public motorbike(int vehicleId, String brand, int rentalCount, boolean isAvailable, int vehicleCount, int maxSpeed, boolean hasHelmetIncluded, int engineCapacity) {
        super(vehicleId, brand, rentalCount, isAvailable, vehicleCount);
        this.maxSpeed = maxSpeed;
        this.hasHelmetIncluded = hasHelmetIncluded;
        this.engineCapacity = engineCapacity;
    }

    @Override
    public void showVehicleInfo() {
        super.showVehicleInfo();
        System.out.println("Type: Motorbike");
        System.out.println("Max Speed: " + maxSpeed + " km/h");
        System.out.println("Helmet Included: " + (hasHelmetIncluded ? "Yes" : "No"));
        System.out.println("Engine Capacity: " + engineCapacity + " cc");
    }

    @Override
    public String toString() {
        return "Motorbike: " + super.toString();
    }

    public void saveToDatabase() {
        String vehicleQuery = "INSERT INTO vehicles (brand, rental_count, is_available, vehicle_count) VALUES (?, ?, ?, ?) RETURNING vehicle_id";
        String motorbikeQuery = "INSERT INTO motorbikes (vehicle_id, max_speed, has_helmet, engine_capacity) VALUES (?, ?, ?, ?)";
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

            try (PreparedStatement motorbikeStmt = conn.prepareStatement(motorbikeQuery)) {
                motorbikeStmt.setInt(1, getVehicleId());
                motorbikeStmt.setInt(2, maxSpeed);
                motorbikeStmt.setBoolean(3, hasHelmetIncluded);
                motorbikeStmt.setInt(4, engineCapacity);
                motorbikeStmt.executeUpdate();
                System.out.println("Motorbike saved to database.");
            }
        } catch (SQLException e) {
            System.out.println("Error saving motorbike: " + e.getMessage());
        }
    }

    public static motorbike getFromDatabase(int vehicleId) {
        String vehicleQuery = "SELECT * FROM vehicles WHERE vehicle_id = ?";
        String motorbikeQuery = "SELECT * FROM motorbikes WHERE vehicle_id = ?";
        try (Connection conn = PostgreSQLJDBC.connect()) {
            try (PreparedStatement vehicleStmt = conn.prepareStatement(vehicleQuery)) {
                vehicleStmt.setInt(1, vehicleId);
                ResultSet vehicleRs = vehicleStmt.executeQuery();
                if (vehicleRs.next()) {
                    String brand = vehicleRs.getString("brand");
                    int rentalCount = vehicleRs.getInt("rental_count");
                    boolean isAvailable = vehicleRs.getBoolean("is_available");
                    int vehicleCount = vehicleRs.getInt("vehicle_count");

                    try (PreparedStatement motorbikeStmt = conn.prepareStatement(motorbikeQuery)) {
                        motorbikeStmt.setInt(1, vehicleId);
                        ResultSet motorbikeRs = motorbikeStmt.executeQuery();
                        if (motorbikeRs.next()) {
                            int maxSpeed = motorbikeRs.getInt("max_speed");
                            boolean hasHelmet = motorbikeRs.getBoolean("has_helmet");
                            int engineCapacity = motorbikeRs.getInt("engine_capacity");
                            return new motorbike(vehicleId, brand, rentalCount, isAvailable, vehicleCount, maxSpeed, hasHelmet, engineCapacity);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching motorbike: " + e.getMessage());
        }
        return null;
    }
}
