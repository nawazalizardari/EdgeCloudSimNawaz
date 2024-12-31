package edu.boun.edgecloudsim.applications.sample_app5;

public class DelayLogItem {
	private double time;
	private int deviceId;
	private boolean isUpload;
	private double pathLoss;
	private double distance;
	private double signalStrength;

	public DelayLogItem(double time, int deviceId, boolean isUpload, double pathLoss, double distance, double signalStrength) {
		this.setTime(time);
		this.setDeviceId(deviceId);
		this.setUpload(isUpload);
		this.setPathLoss(pathLoss);
		this.setDistance(distance);
		this.setSignalStrength(signalStrength);
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public double getPathLoss() {
		return pathLoss;
	}

	public void setPathLoss(double pathLoss) {
		this.pathLoss = pathLoss;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public boolean isUpload() {
		return isUpload;
	}

	public void setUpload(boolean isUpload) {
		this.isUpload = isUpload;
	}

	public double getSignalStrength() {
		return signalStrength;
	}

	public void setSignalStrength(double signalStrength) {
		this.signalStrength = signalStrength;
	}
}