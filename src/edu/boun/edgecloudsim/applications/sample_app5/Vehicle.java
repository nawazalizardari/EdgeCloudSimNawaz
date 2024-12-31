package edu.boun.edgecloudsim.applications.sample_app5;

import java.util.ArrayList;
import java.util.List;

import edu.boun.edgecloudsim.utils.Location;

public class Vehicle {
	private int id;
	private double x;
	private double y;
	private double pos;
	private Location loc;
	private List<Double> timeSteps;

	public Vehicle() {
		timeSteps = new ArrayList<Double>();
	}

	public void AddTimeStep(double time) {
		timeSteps.add(time);
	}

	/**
	 * 
	 * @param id  = vehicle ID
	 * @param x   = Longitude
	 * @param y   = Latitude
	 * @param pos = The running position of the vehicle measured from the start of
	 *            the current lane
	 */
	public Vehicle(int id, double x, double y, double pos) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.pos = pos;

		this.loc = new Location(-1, -1, x, y);
		timeSteps = new ArrayList<Double>();

	}

	// Getters
	public int getId() {
		return id;
	}

	/**
	 * The absolute X coordinate of the vehicle (center of front bumper). The value
	 * depends on the given geographic projection
	 * 
	 * @return m or longitude
	 */
	public double getX() {
		return x;
	}

	/**
	 * The absolute Y coordinate of the vehicle (center of front bumper). The value
	 * depends on the given geographic projection
	 * 
	 * @return m or latitude
	 */
	public double getY() {
		return y;
	}

	public double getPos() {
		return pos;
	}

	public Location getLocation() {
		return loc;
	}

	public void setLocation(Location l) {
		loc = l;
		this.x = l.getXPos();
		this.y = l.getYPos();
	}

	public List<Double> getTimeSteps() {
		return timeSteps;
	}
}
