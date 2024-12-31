package edu.boun.edgecloudsim.applications.sample_app5;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.distribution.ExponentialDistribution;

import edu.boun.edgecloudsim.core.SimSettings;
import edu.boun.edgecloudsim.task_generator.LoadGeneratorModel;
import edu.boun.edgecloudsim.utils.SimLogger;
import edu.boun.edgecloudsim.utils.SimUtils;
import edu.boun.edgecloudsim.utils.TaskProperty;

public class VehicularLoadGenerator extends LoadGeneratorModel {
	int taskTypeOfDevices[];

	public VehicularLoadGenerator(int _numberOfMobileDevices, double _simulationTime, String _simScenario) {
		super(_numberOfMobileDevices, _simulationTime, _simScenario);
	}

	@Override
	public void initializeModel() {
		taskList = new ArrayList<TaskProperty>();
		FCDReader fcd = FCDReader.getInstance();
		List<Vehicle> vehicles = fcd.getVehicles();

		// Each mobile device utilizes an app type (task type)
		taskTypeOfDevices = new int[numberOfMobileDevices];

//		int taskTypes = SimSettings.getInstance().getTaskLookUpTable().length;

		for (int i = 0; i < numberOfMobileDevices; i++) {

			Vehicle v = vehicles.get(i);

			int randomTaskType = -1;
			double taskTypeSelector = SimUtils.getRandomDoubleNumber(0, 100);
			double taskTypePercentage = 0;
			for (int j = 0; j < SimSettings.getInstance().getTaskLookUpTable().length; j++) {
				taskTypePercentage += SimSettings.getInstance().getTaskLookUpTable()[j][0];
				if (taskTypeSelector <= taskTypePercentage) {
					randomTaskType = j;
					break;
				}
			}
			if (randomTaskType == -1) {
				SimLogger.printLine("Impossible is occurred! no random task type!");
				continue;
			}

			taskTypeOfDevices[i] = randomTaskType;

			/*
			 * 1 <usage_percentage>30</usage_percentage> 2
			 * <prob_cloud_selection>0</prob_cloud_selection> 13
			 * <delay_sensitivity>0.5</delay_sensitivity> 14
			 * <max_delay_requirement>0.5</max_delay_requirement> 3
			 * <poisson_interarrival>3</poisson_interarrival> 4
			 * <active_period>3600</active_period> 5 <idle_period>1</idle_period> 6
			 * <data_upload>20</data_upload> 7 <data_download>20</data_download> 8
			 * <task_length>3000</task_length> 9 <required_core>1</required_core> 10
			 * <vm_utilization_on_edge>6</vm_utilization_on_edge> 11
			 * <vm_utilization_on_cloud>1.2</vm_utilization_on_cloud> 12
			 * <vm_utilization_on_mobile>0</vm_utilization_on_mobile>
			 * 
			 */

			double activePeriodStartTime = SimUtils.getRandomDoubleNumber(SimSettings.CLIENT_ACTIVITY_START_TIME,
					SimSettings.CLIENT_ACTIVITY_START_TIME * 2); // active period starts shortly after the simulation
																	// started (e.g. 10 seconds)

			for (double time : v.getTimeSteps()) {
				if (time > activePeriodStartTime && time < simulationTime) {

					for (int n = 0; n < 5; n++) {

						long inputFileSize = (long) SimSettings.getInstance().getTaskLookUpTable()[randomTaskType][5]; // 6
																														// <data_upload>20</data_upload>
						long inputFileSizeBias = inputFileSize / 10;

						long outputFileSize = (long) SimSettings.getInstance().getTaskLookUpTable()[randomTaskType][6]; // 7
																														// <data_download>20</data_download>
						long outputFileSizeBias = outputFileSize / 10;

						long length = (long) SimSettings.getInstance().getTaskLookUpTable()[randomTaskType][7]; // 8
																												// <task_length>3000</task_length>
						long lengthBias = length / 10;

						int pesNumber = (int) SimSettings.getInstance().getTaskLookUpTable()[randomTaskType][8]; // 9
																													// <required_core>1</required_core>

						inputFileSize = SimUtils.getRandomLongNumber(inputFileSize - inputFileSizeBias,
								inputFileSize + inputFileSizeBias);
						outputFileSize = SimUtils.getRandomLongNumber(outputFileSize - outputFileSizeBias,
								outputFileSize + outputFileSizeBias);
						length = SimUtils.getRandomLongNumber(length - lengthBias, length + lengthBias);

						taskList.add(new TaskProperty(time, i, randomTaskType, pesNumber, length, inputFileSize,
								outputFileSize));
					}

				}
			}
		}
	}

	@Override
	public int getTaskTypeOfDevice(int deviceId) {
		// TODO Auto-generated method stub
		return taskTypeOfDevices[deviceId];
	}

}