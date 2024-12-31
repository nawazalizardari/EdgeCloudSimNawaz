package edu.boun.edgecloudsim.applications.sample_app5;

import edu.boun.edgecloudsim.utils.Location;

public class DataCenter {
	private int id;
	private double x;
	private double y;
	private double transmittedPower; // in dBm
	private Location loc;

	public DataCenter(int id, double x, double y) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.transmittedPower = 30;
		this.loc = new Location(-1, -1, x, y);
	}
	
	/**
	 * 
	 * @param id
	 * @param x longitude
	 * @param y latitude
	 * @param transmittedPower
	 */

	public DataCenter(int id, double x, double y, double transmittedPower) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.transmittedPower = transmittedPower;
		this.loc = new Location(-1, -1, x, y);

	}

	// Getters
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @return m or longitude
	 */
	public double getX() {
		return x;
	}

	/**
	 * 
	 * @return m or latitude
	 */
	public double getY() {
		return y;
	}
	
	public double getTransmittedPower() {
		return transmittedPower;
	}
	
	public Location getLocation() {
		return loc;
	}

}
