package vehicles;

public class Motorbike extends Vehicle {
    private int engineCapacity;
    private boolean hasHelmetIncluded;

    public Motorbike(String vehicleID, String brand, int engineCapacity, boolean hasHelmetIncluded) {
        super(vehicleID, brand);
        this.engineCapacity = engineCapacity;
        this.hasHelmetIncluded = hasHelmetIncluded;
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Engine Capacity: " + engineCapacity + " cc");
        System.out.println("Helmet Included: " + hasHelmetIncluded);
    }
}