/*
 * Title:        EdgeCloudSim - Location
 * 
 * Description:  Location class used in EdgeCloudSim
 * 
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 * Copyright (c) 2017, Bogazici University, Istanbul, Turkey
 */

package edu.boun.edgecloudsim.utils;

public class Location {
	private double xPos;
	private double yPos;
	private int servingWlanId;
	private int placeTypeIndex;

	/**
	 * 
	 * @param _placeTypeIndex
	 * @param _servingWlanId
	 * @param _xPos           longitude
	 * @param _yPos           latitude
	 */
	public Location(int _placeTypeIndex, int _servingWlanId, double _xPos, double _yPos) {
		servingWlanId = _servingWlanId;
		placeTypeIndex = _placeTypeIndex;
		xPos = _xPos;
		yPos = _yPos;
	}

	@Override
	public String toString() {
		String s = " WlanId: " + Integer.toString(servingWlanId) + ",\t x: " + Double.toString(xPos) + ",\t y: "
				+ Double.toString(yPos);

		return s;
	}

	/*
	 * Default Constructor: Creates an empty Location
	 */
	public Location() {
	}

	@Override
	public boolean equals(Object other) {
		boolean result = false;
		if (other == null)
			return false;
		if (!(other instanceof Location))
			return false;
		if (other == this)
			return true;

		Location otherLocation = (Location) other;
		if (this.xPos == otherLocation.xPos && this.yPos == otherLocation.yPos)
			result = true;

		return result;
	}

	public int getServingWlanId() {
		return servingWlanId;
	}

	public void setServingWlanId(int wLanId) {
		this.servingWlanId = wLanId;
	}

	public int getPlaceTypeIndex() {
		return placeTypeIndex;
	}

	/**
	 * The absolute X coordinate of the vehicle (center of front bumper). The value
	 * depends on the given geographic projection
	 * 
	 * @return x or longitude
	 */

	public double getXPos() {
		return xPos;
	}

	/**
	 * The absolute X coordinate of the vehicle (center of front bumper). The value
	 * depends on the given geographic projection
	 * 
	 * @return x or longitude
	 */
	public double getLongitude() {
		return xPos;
	}

	/**
	 * The absolute Y coordinate of the vehicle (center of front bumper). The value
	 * depends on the given geographic projection
	 * 
	 * @return m or latitude
	 */

	public double getYPos() {
		return yPos;
	}

	/**
	 * The absolute Y coordinate of the vehicle (center of front bumper). The value
	 * depends on the given geographic projection
	 * 
	 * @return y or latitude
	 */
	public double getLatitude() {
		return yPos;
	}

}
