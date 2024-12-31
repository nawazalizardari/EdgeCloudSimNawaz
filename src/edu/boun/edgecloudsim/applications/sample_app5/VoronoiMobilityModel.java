package edu.boun.edgecloudsim.applications.sample_app5;

import java.util.List;

public class VoronoiMobilityModel {
    private List<EdgeDataCenter> dataCenters; // Your data centers
    private List<double[]> vehicleLocations; // Your vehicles

    // Constructor
    public VoronoiMobilityModel(List<EdgeDataCenter> dataCenters, List<double[]> vehicleLocations) {
        this.dataCenters = dataCenters;
        this.vehicleLocations = vehicleLocations;
    }

    public double[] getLocation(int deviceId) {
        double[] vehicleLocation = vehicleLocations.get(deviceId);
        int nearestDataCenterIndex = getNearestDataCenter(vehicleLocation);
        EdgeDataCenter nearestDataCenter = dataCenters.get(nearestDataCenterIndex);

        // Assuming EdgeDataCenter has methods to get its location
        return new double[]{nearestDataCenter.getLongitude(), nearestDataCenter.getLatitude()};
    }

    private int getNearestDataCenter(double[] vehicleLocation) {
        int nearestIndex = 0;
        double nearestDistance = Double.MAX_VALUE;

        for (int i = 0; i < dataCenters.size(); i++) {
            EdgeDataCenter dataCenter = dataCenters.get(i);
            double distance = calculateEuclideanDistance(vehicleLocation, new double[]{dataCenter.getLongitude(), dataCenter.getLatitude()});

            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestIndex = i;
            }
        }

        return nearestIndex;
    }

    private double calculateEuclideanDistance(double[] point1, double[] point2) {
        return Math.sqrt(Math.pow(point1[0] - point2[0], 2) + Math.pow(point1[1] - point2[1], 2));
    }

    // Placeholder for EdgeDataCenter class
    public static class EdgeDataCenter {
        private double longitude;
        private double latitude;

        public EdgeDataCenter(double longitude, double latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public double getLatitude() {
            return latitude;
        }
    }
}
