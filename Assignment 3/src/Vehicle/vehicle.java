package Vehicle;

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
}
