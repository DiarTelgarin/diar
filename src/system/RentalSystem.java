package system;

import vehicles.Vehicle;
import java.util.ArrayList;
import java.util.List;

public class RentalSystem {
    private List<Vehicle> vehicles;

    public RentalSystem() {
        this.vehicles = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void displayAllVehicles() {
        for (Vehicle vehicle : vehicles) {
            vehicle.displayDetails();
            System.out.println("------------------");
        }
    }

    public Vehicle searchVehicleByID(String vehicleID) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getVehicleID().equals(vehicleID)) {
                return vehicle;
            }
        }
        return null;
    }

    public boolean rentVehicle(String vehicleID) {
        Vehicle vehicle = searchVehicleByID(vehicleID);
        if (vehicle != null && vehicle.isAvailable()) {
            vehicle.setAvailable(false);
            vehicle.incrementRentalCount();
            return true;
        }
        return false;
    }

    public boolean returnVehicle(String vehicleID) {
        Vehicle vehicle = searchVehicleByID(vehicleID);
        if (vehicle != null && !vehicle.isAvailable()) {
            vehicle.setAvailable(true);
            return true;
        }
        return false;
    }
}