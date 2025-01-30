package vehicles.src.vehicles;

public class Car extends Vehicle {
    private int seatingCapacity;
    private String fuelType;

    public Car(String vehicleID, String brand, int seatingCapacity, String fuelType) {
        super(vehicleID, brand);
        this.seatingCapacity = seatingCapacity;
        this.fuelType = fuelType;
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Seating Capacity: " + seatingCapacity);
        System.out.println("Fuel Type: " + fuelType);
    }
}