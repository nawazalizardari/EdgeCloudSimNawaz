package edu.boun.edgecloudsim.applications.sample_app5;

import java.util.ArrayList;
import java.util.List;

public class TimeStep {
    private double time;
    private List<Vehicle> vehicles;

    public TimeStep(double time) {
        this.time = time;
        this.vehicles = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

    // Getters
    public double getTime() {
        return time;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }
}

