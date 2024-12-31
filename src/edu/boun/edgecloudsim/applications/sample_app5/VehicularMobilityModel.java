/*
 * Title:        EdgeCloudSim - Mobility model implementation
 * 
 * Description: 
 * VehicularMobilityModel implements basic vehicular mobility model
 * 
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 * Copyright (c) 2017, Bogazici University, Istanbul, Turkey
 */

package edu.boun.edgecloudsim.applications.sample_app5;

import edu.boun.edgecloudsim.utils.SimLogger;

import java.util.List;

import edu.boun.edgecloudsim.core.SimSettings;
import edu.boun.edgecloudsim.mobility.MobilityModel;
import edu.boun.edgecloudsim.utils.Location;

public class VehicularMobilityModel extends MobilityModel {

	private List<DataCenter> dataCenters;
	private List<Vehicle> vehicles;
	private SimLogger loger;

	public List<DataCenter> getDataCenters() {
		return this.dataCenters;
	}

	// prepare following arrays to decrease computation on getLocation() function
	// NOTE: if the number of clients is high, keeping following values in RAM
	// may be expensive. In that case sacrifice computational resources!
//	private int[] initialLocationIndexArray;
//	private int[] initialPositionArray; //in meters unit
//	private double[] timeToDriveLocationArray;//in seconds unit
//	private double[] timeToReachNextLocationArray; //in seconds unit

	private List<TimeStep> timeSteps;

	public VehicularMobilityModel(int _numberOfMobileDevices, double _simulationTime) {
		super(_numberOfMobileDevices, _simulationTime);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize() {
		// Find total length of the road
		FCDReader fcd = FCDReader.getInstance();
		dataCenters = fcd.getDataCenters();
		timeSteps = fcd.getTimeSteps();
		vehicles = fcd.getVehicles();

		loger = SimLogger.getInstance();

	}

	@Override
	public Location getLocation(int deviceId, double time) {

		try {

			Vehicle va1 = vehicles.get(deviceId);
			Location oldLoc = va1.getLocation();
			// int vId = va1.getId();

			for (TimeStep step : timeSteps) {
				int time2 = (int) step.getTime();
				if (time2 == (int) time) {

					loger.printLocationLog("Time: " + Integer.toString(time2) + SimSettings.DELIMITER);

					for (Vehicle v : step.getVehicles()) {
						int vehicleId = v.getId();
						if (vehicleId == deviceId) {

							double x = v.getX();
							double y = v.getY();

							int wLanId = MyUtilities.getNearestDataCenterByPower(dataCenters, v);

							Location loc = new Location(wLanId, wLanId, x, y);

							vehicles.get(deviceId).setLocation(loc);

							loger.printlnLocationLog("Vehicle: " + Integer.toString(deviceId) + SimSettings.DELIMITER
									+ " _x: " + Double.toString(va1.getX()) + SimSettings.DELIMITER + " _y: "
									+ Double.toString(va1.getY()) + SimSettings.DELIMITER + " _wlan: "
									+ Integer.toString(va1.getLocation().getServingWlanId()));

							return loc;

						}
					}

//					loger.printlnLocationLog("Vehicle: " + Integer.toString(deviceId) + "_x: " + Double.toString(va1.getX())
//					+ "_y: " + Double.toString(va1.getY()) + "_wlan: "
//					+ Integer.toString(va1.getLocation().getServingWlanId()));

					return oldLoc;

				}
			}

//			loger.printlnLocationLog("Vehicle: " + Integer.toString(deviceId) + "_x: " + Double.toString(va1.getX()) + "_y: "
//					+ Double.toString(va1.getY()) + "_wlan: " + Integer.toString(va1.getLocation().getServingWlanId()));

			return oldLoc;

		} catch (Exception e) {
			String x = String.format("Device Id: %s \t\t Time: %s", deviceId, time);
			System.out.println(x);
			throw e;
		}

	}

}
