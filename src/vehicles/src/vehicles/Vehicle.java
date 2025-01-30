package vehicles.src.vehicles;

public class Vehicle {
    private String vehicleID;
    private String brand;
    private boolean isAvailable;
    private int rentalCount;

    public Vehicle(String vehicleID, String brand) {
        this.vehicleID = vehicleID;
        this.brand = brand;
        this.isAvailable = true;
        this.rentalCount = 0;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public String getBrand() {
        return brand;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public int getRentalCount() {
        return rentalCount;
    }

    public void incrementRentalCount() {
        this.rentalCount++;
    }

    public void displayDetails() {
        System.out.println("Vehicle ID: " + vehicleID);
        System.out.println("Brand: " + brand);
        System.out.println("Available: " + isAvailable);
        System.out.println("Rental Count: " + rentalCount);
    }
}