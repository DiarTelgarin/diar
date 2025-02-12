package com.example.Vehicle;

public class vehicle {

    private int vehicleId;

    private String brand;
    private int rentalCount;
    private boolean isAvailable;
    private int vehicleCount;

    public vehicle() {}

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

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getRentalCount() {
        return rentalCount;
    }

    public void setRentalCount(int rentalCount) {
        this.rentalCount = rentalCount;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public void showVehicleInfo() {
        System.out.println("\nVehicle ID: " + vehicleId);
        System.out.println("Brand: " + brand);
        System.out.println("Rental Count: " + rentalCount);
        System.out.println("Available: " + (isAvailable ? "Yes" : "No"));
        System.out.println("Vehicle Count: " + vehicleCount);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId=" + vehicleId +
                ", brand='" + brand + '\'' +
                ", rentalCount=" + rentalCount +
                ", isAvailable=" + isAvailable +
                ", vehicleCount=" + vehicleCount +
                '}';
    }
}
