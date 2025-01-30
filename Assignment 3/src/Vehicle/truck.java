package Vehicle;

import Database.PostgreSQLJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class truck extends vehicle {
    private int maxLoad;
    private boolean withTrailer;

    public truck(int vehicleId, String brand, int rentalCount, boolean isAvailable, int vehicleCount, int maxLoad, boolean withTrailer) {
        super(vehicleId, brand, rentalCount, isAvailable, vehicleCount);
        this.maxLoad = maxLoad;
        this.withTrailer = withTrailer;
    }

    @Override
    public void showVehicleInfo() {
        super.showVehicleInfo();
        System.out.println("Type: Truck");
        System.out.println("Max Load: " + maxLoad + " kg");
        System.out.println("With Trailer: " + (withTrailer ? "Yes" : "No"));
    }

    public void saveToDatabase() {
        String vehicleQuery = "INSERT INTO vehicles (brand, rental_count, is_available, vehicle_count) VALUES (?, ?, ?, ?) RETURNING vehicle_id";
        String truckQuery = "INSERT INTO trucks (vehicle_id, max_load, with_trailer) VALUES (?, ?, ?)";
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

            // Сохраняем грузовик
            try (PreparedStatement truckStmt = conn.prepareStatement(truckQuery)) {
                truckStmt.setInt(1, getVehicleId());
                truckStmt.setInt(2, maxLoad);
                truckStmt.setBoolean(3, withTrailer);
                truckStmt.executeUpdate();
                System.out.println("Truck saved to database.");
            }
        } catch (SQLException e) {
            System.out.println("Error saving truck: " + e.getMessage());
        }
    }

    public static truck getFromDatabase(int vehicleId) {
        String vehicleQuery = "SELECT * FROM vehicles WHERE vehicle_id = ?";
        String truckQuery = "SELECT * FROM trucks WHERE vehicle_id = ?";
        try (Connection conn = PostgreSQLJDBC.connect()) {
            // Извлекаем данные транспорта
            try (PreparedStatement vehicleStmt = conn.prepareStatement(vehicleQuery)) {
                vehicleStmt.setInt(1, vehicleId);
                ResultSet vehicleRs = vehicleStmt.executeQuery();
                if (vehicleRs.next()) {
                    String brand = vehicleRs.getString("brand");
                    int rentalCount = vehicleRs.getInt("rental_count");
                    boolean isAvailable = vehicleRs.getBoolean("is_available");
                    int vehicleCount = vehicleRs.getInt("vehicle_count");

                    // Извлекаем данные грузовика
                    try (PreparedStatement truckStmt = conn.prepareStatement(truckQuery)) {
                        truckStmt.setInt(1, vehicleId);
                        ResultSet truckRs = truckStmt.executeQuery();
                        if (truckRs.next()) {
                            int maxLoad = truckRs.getInt("max_load");
                            boolean withTrailer = truckRs.getBoolean("with_trailer");
                            return new truck(vehicleId, brand, rentalCount, isAvailable, vehicleCount, maxLoad, withTrailer);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching truck: " + e.getMessage());
        }
        return null;
    }
}
