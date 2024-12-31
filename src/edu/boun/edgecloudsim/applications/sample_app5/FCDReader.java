package edu.boun.edgecloudsim.applications.sample_app5;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.boun.edgecloudsim.core.SimSettings;
import edu.boun.edgecloudsim.utils.Location;
import edu.boun.edgecloudsim.utils.SimLogger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FCDReader {

	private List<DataCenter> dataCenters;
	private List<TimeStep> timeSteps;
	private List<Vehicle> vehicles;

	private static FCDReader instance = null;

	public static FCDReader getInstance() {
		if (instance == null) {
			instance = new FCDReader();
		}
		return instance;
	}

	public FCDReader() {
		this.vehicles = new ArrayList<>();
		this.dataCenters = new ArrayList<DataCenter>();
		this.timeSteps = new ArrayList<TimeStep>();

		Document doc = SimSettings.getInstance().getEdgeDevicesDocument();
		NodeList datacenterList = doc.getElementsByTagName("datacenter");
		for (int i = 0; i < datacenterList.getLength(); i++) {
			Node datacenterNode = datacenterList.item(i);
			Element datacenterElement = (Element) datacenterNode;
			Element locationElement = (Element) datacenterElement.getElementsByTagName("location").item(0);

			double x_pos = Double.parseDouble(locationElement.getElementsByTagName("x_pos").item(0).getTextContent());
			double y_pos = Double.parseDouble(locationElement.getElementsByTagName("y_pos").item(0).getTextContent());

			DataCenter d = new DataCenter(i, x_pos, y_pos);
			dataCenters.add(d);
		}

		readFCDFile("fcd-data.xml", dataCenters);

		SimLogger.printLine("");
		SimLogger.print("Total Cars: ");
		SimLogger.printLine(Integer.toString(vehicles.size()));

	}

	private void readFCDFile(String filePath, List<DataCenter> dataCenters) {
		Set<Integer> vehicleIds = new HashSet<>();
//		List<TimeStep> timeSteps = new ArrayList<>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(filePath);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("timestep");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Element tElement = (Element) nList.item(temp);
				double time = Double.parseDouble(tElement.getAttribute("time"));
				TimeStep timeStep = new TimeStep(time);

				NodeList vList = tElement.getElementsByTagName("vehicle");
				for (int count = 0; count < vList.getLength(); count++) {
					Element vElement = (Element) vList.item(count);
					String id = vElement.getAttribute("id").replace("veh", "");

					int vehicleId = Integer.parseInt(id);
					double x = Double.parseDouble(vElement.getAttribute("x"));
					double y = Double.parseDouble(vElement.getAttribute("y"));
					double pos = Double.parseDouble(vElement.getAttribute("pos"));

					Vehicle vehicle = new Vehicle(vehicleId, x, y, pos);
					timeStep.addVehicle(vehicle);

					if (!vehicleIds.contains(vehicleId)) {
						vehicleIds.add(vehicleId);

						int wLanId = MyUtilities.getNearestDataCenter(dataCenters, vehicle);
						Location loc = new Location(wLanId, wLanId, x, y);
						vehicle.setLocation(loc);

						vehicles.add(vehicle);

					}

					if (vehicleIds.contains(vehicleId)) {
						for (Vehicle v : vehicles) {
							if (v.getId() == vehicleId) {
								v.AddTimeStep(time);
							}
						}
					}

				}

				this.timeSteps.add(timeStep);

			}

//			return timeSteps;
		} catch (Exception e) {
			e.printStackTrace();
		}
//		return timeSteps;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public List<DataCenter> getDataCenters() {
		return this.dataCenters;
	}

	public List<TimeStep> getTimeSteps() {
		return this.timeSteps;
	}

}
