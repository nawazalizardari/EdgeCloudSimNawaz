package edu.boun.edgecloudsim.edge_server;

import org.cloudbus.cloudsim.CloudletScheduler;
import org.cloudbus.cloudsim.ResCloudlet;
import org.cloudbus.cloudsim.Vm;

public abstract class Vm2 extends Vm {

	public Vm2(int id, int userId, double mips, int numberOfPes, int ram, long bw, long size, String vmm,
			CloudletScheduler cloudletScheduler) {
		super(id, userId, mips, numberOfPes, ram, bw, size, vmm, cloudletScheduler);
	}

//	public abstract double getTotalUtilizationOfRam(double time);

	public double getTotalUtilizationOfRam(double time) {

		double totalUtilization = 0;
		for (ResCloudlet gl : getCloudletScheduler().getCloudletExecList()) {
			totalUtilization += gl.getCloudlet().getUtilizationOfRam(time);
		}
		return totalUtilization;
	}
	
	public double getTotalUtilizationOfBw(double time) {

		double totalUtilization = 0;
		for (ResCloudlet gl : getCloudletScheduler().getCloudletExecList()) {
			totalUtilization += gl.getCloudlet().getUtilizationOfBw(time);
		}
		return totalUtilization;
	}

}
