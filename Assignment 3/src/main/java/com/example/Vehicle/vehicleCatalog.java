package com.example.Vehicle;

import com.example.Database.PostgreSQLJDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class vehicleCatalog {
    public static void showAllVehicles() {
        String query = "SELECT * FROM vehicles";

        PostgreSQLJDBC DatabaseConnection = new PostgreSQLJDBC();
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\nКаталог машин:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("vehicle_id") +
                        ", Марка: " + rs.getString("brand") +
                        ", Доступность: " + (rs.getBoolean("is_available") ? "Да" : "Нет"));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении данных: " + e.getMessage());
        }
    }
}
