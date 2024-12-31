package edu.boun.edgecloudsim.applications.sample_app5;

public class Haversine {
	private static final int EARTH_RADIUS_KM = 6371; // Approx Earth radius in KM

	public static double calculateDistance(double startLat, double startLong, double endLat, double endLong) {

		double dLat = Math.toRadians(endLat - startLat);
		double dLong = Math.toRadians(endLong - startLong);

		startLat = Math.toRadians(startLat);
		endLat = Math.toRadians(endLat);

		double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return EARTH_RADIUS_KM * c; // <-- Distance in kilometers
	}

	public static double calculateDistanceMeters(double startLat, double startLong, double endLat, double endLong) {
		double distanceKm = calculateDistance(startLat, startLong, endLat, endLong);
		return distanceKm * 1000;
	}

	public static double haversin(double val) {
		return Math.pow(Math.sin(val / 2), 2);
	}

//    
//    private static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
//        final double R = 6371e3; // Radius of the Earth in meters
//        double dLat = Math.toRadians(lat2 - lat1);
//        double dLon = Math.toRadians(lon2 - lon1);
//        lat1 = Math.toRadians(lat1);
//        lat2 = Math.toRadians(lat2);
//
//        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
//                   Math.cos(lat1) * Math.cos(lat2) *
//                   Math.sin(dLon/2) * Math.sin(dLon/2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        return R * c;
//    }
}
