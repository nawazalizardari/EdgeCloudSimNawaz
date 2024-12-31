package edu.boun.edgecloudsim.applications.sample_app5;

import java.util.List;

import java.util.stream.Collectors;

public class MyUtilities {

	static final double speedOflight = 3.0e8; // Speed of light in meters/second
	static final double frequency = 3.5e9; // 3.5 GHz
	static final double powerThreshold = -95; // Power threshold in dBm

	public static List<DataCenter> getNearestDataCenterUnder1KM(List<DataCenter> dataCenters, Vehicle v) {

		int rangeKm = 1; // Kilometers

		double vehicleLat = v.getY();
		double vehicleLong = v.getX();

		List<DataCenter> centers = dataCenters.stream()
				.filter(dc -> Haversine.calculateDistance(vehicleLat, vehicleLong, dc.getY(), dc.getX()) <= rangeKm)
				.collect(Collectors.toList());

		return centers;

	}

	public static double calculateSignalStrength(double distance, double frequency, double transmittedPower) {
		// Free-space path loss formula (simplified)

		double pathLoss = 20 * Math.log10(distance) + 20 * Math.log10(frequency)
				+ 20 * Math.log10(4 * Math.PI / MyUtilities.speedOflight);

		double signalStrength = transmittedPower - pathLoss;

		return signalStrength;
	}

	
	
	
	public static int getNearestDataCenterByPower(List<DataCenter> dc, Vehicle v) {
		int nearestIndex = 0;
		double maxSignalStrength = Double.NEGATIVE_INFINITY;

		List<DataCenter> dataCenters = getNearestDataCenterUnder1KM(dc, v);
		if (dataCenters.size() <= 0) {
			dataCenters = dc;
		}

		double vehicleLat = v.getY();
		double vehicleLong = v.getX();

		double centerLong = -1;
		double centerLat = -1;
		double distance = -1;

		for (int i = 0; i < dataCenters.size(); i++) {
			DataCenter d = dataCenters.get(i);

			centerLong = d.getX();
			centerLat = d.getY();

			distance = Haversine.calculateDistance(vehicleLat, vehicleLong, centerLat, centerLong);

			double signalStrength = calculateSignalStrength(distance, MyUtilities.frequency, d.getTransmittedPower());

			if (signalStrength > maxSignalStrength && signalStrength >= powerThreshold) {
				nearestIndex = d.getId();
				maxSignalStrength = signalStrength;
			}

		}

		return nearestIndex;

	}

	public static int getNearestDataCenter(List<DataCenter> dc, Vehicle v) {
		int nearestIndex = 0;
		double nearestDistance = Double.MAX_VALUE;

		List<DataCenter> dataCenters = getNearestDataCenterUnder1KM(dc, v);
		if (dataCenters.size() <= 0) {
			dataCenters = dc;
		}

		for (int i = 0; i < dataCenters.size(); i++) {
			DataCenter d = dataCenters.get(i);
			double distance = calculateEuclideanDistance(v, d);

			if (distance < nearestDistance) {
				nearestDistance = distance;
				nearestIndex = i;
			}
		}

		DataCenter d = dataCenters.get(nearestIndex);

		/*
		 * SimLogger.printLine(""); SimLogger.print("Data Center: [x_pos]: ");
		 * SimLogger.print(Double.toString(d.getX())); SimLogger.print("[y_pos]: ");
		 * SimLogger.print(Double.toString(d.getY()));
		 * SimLogger.print("\t\t Vehicle: [x_pos]: ");
		 * SimLogger.print(Double.toString(v.getX())); SimLogger.print("[y_pos]: ");
		 * SimLogger.print(Double.toString(v.getY()));
		 */

		return nearestIndex;

	}

	private static double calculateEuclideanDistance(Vehicle v, DataCenter d) {
		return Math.sqrt(Math.pow(v.getX() - d.getX(), 2) + Math.pow(v.getY() - d.getY(), 2));
	}

//	private static void Nawaz() {
//		String a = "12345";
//		double x = 10.5;
//		int b = 10;
//		int c = Integer.parseInt(a);
//		
//		
//	}

}
