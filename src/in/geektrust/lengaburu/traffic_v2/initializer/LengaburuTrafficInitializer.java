/*
* Copyright (c) 2017, Ritesh. All rights reserved.
*
*/
package in.geektrust.lengaburu.traffic_v2.initializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import in.geektrust.lengaburu.traffic.beans.Orbit;
import in.geektrust.lengaburu.traffic.beans.Vehicle;
import in.geektrust.lengaburu.traffic.beans.Velocity;
import in.geektrust.lengaburu.traffic.beans.Weather;
import in.geektrust.lengaburu.traffic.beans.WeatherType;

/**
 * DESCRIPTION - This class is responsible to initialize all data with hard coded values.
 * 
 * It initializes following objects:
 * 	-	List of all possible weathers
 *  - 	List of available vehicles
 *  -  	List of routes/orbits, and its corresponding details.
 *  
 * Note: If data is fetched from any database or file system, we can retrieve them and populate these objects.
 *  
 * @author - Ritesh Bangal
 * @version 1.0
 * @since <22-August-2017>
 */
public class LengaburuTrafficInitializer {

	// Create an object of SingleObject
	private static LengaburuTrafficInitializer initializerInstance = new LengaburuTrafficInitializer();

	private LengaburuTrafficInitializer() {
		// Make the constructor private so that this class cannot be instantiated
	}

	// Get the only object available
	public static LengaburuTrafficInitializer getInstance(){
		return initializerInstance;
	}

	/**
	 * This method is responsible for
	 * 	-	Populate all Weather objects with hard coded values
	 * 
	 * In the list of vehicles, sequence should be maintained. 
	 * As, if there is a tie in which vehicle to choose, use bike, auto/tuktuk, car in that order.
	 * 
	 * @return - List of all possible weathers
	 */
	public List<Weather> getAllWeatherDetails() {
		List<Weather> weathers = new ArrayList<>();
		weathers.add(new Weather(WeatherType.SUNNY, -10, Arrays.asList("Bike", "Tuktuk", "Car")));
		weathers.add(new Weather(WeatherType.RAINY, +20, Arrays.asList("Tuktuk", "Car")));
		weathers.add(new Weather(WeatherType.WINDY, +0, getAllVehicleNames()));
		return weathers;
	}
	
	/**
	 * Returns all vehicle names. This is used while creating Weather object, where all types of vehicles can be used.
	 * 
	 * @return - List of all vehicle names
	 */
	public List<String> getAllVehicleNames() {
		return getAllVehicles().stream().map(Vehicle::getName).collect(Collectors.toList());
	}
	
	/**
	 * This method is responsible for
	 * 	-	Populate all Vehicle objects with hard coded values
	 *  
	 * In the list of vehicles, sequence should be maintained. 
	 * As, if there is a tie in which vehicle to choose, use bike, auto/tuktuk, car in that order.
	 *
	 * @return - List of available vehicles
	 */
	public List<Vehicle> getAllVehicles() {
		List<Vehicle> vehicles = new ArrayList<>();
		vehicles.add(new Vehicle("Bike", new Velocity(10, "megamiles/hour"), 2));
		vehicles.add(new Vehicle("Tuktuk", new Velocity(12, "megamiles/hour"), 1));
		vehicles.add(new Vehicle("Car", new Velocity(20, "megamiles/hour"), 3));
		return vehicles;
	}
	
	/**
	 * This method is responsible for
	 * 	-	Populate all Orbit objects with hard coded values
	 * 
	 * Note: Here source and destination, can't be interchanged. As road could be two ways. 
	 * 		 i.e. A to B is not same with B to A.
	 * 
	 * @return - List of routes/orbits along with its corresponding details.
	 */
	public List<Orbit> getAllOrbits() {
		List<Orbit> orbits = new ArrayList<>();
		orbits.add(new Orbit("Hallitharam", "Silk Drob", 18, 20, new Velocity(20, "megamiles/hour")));
		orbits.add(new Orbit("Hallitharam", "Silk Drob", 20, 10, new Velocity(12, "megamiles/hour")));
		orbits.add(new Orbit("Silk Drob", "Hallitharam", 18, 22, new Velocity(18, "megamiles/hour")));
		orbits.add(new Orbit("Silk Drob", "Hallitharam", 20, 12, new Velocity(10, "megamiles/hour")));
		orbits.add(new Orbit("RK Puram", "Silk Drob", 30, 15, new Velocity(15, "megamiles/hour")));
		orbits.add(new Orbit("RK Puram", "Silk Drob", 30, 4, new Velocity(25, "megamiles/hour")));
		orbits.add(new Orbit("RK Puram", "Silk Drob", 30, 10, new Velocity(18, "megamiles/hour")));
		orbits.add(new Orbit("RK Puram", "Silk Drob", 30, 2, new Velocity(28, "megamiles/hour")));
		orbits.add(new Orbit("Hallitharam", "RK Puram", 15, 18, new Velocity(12, "megamiles/hour")));
		
		// New suburb: Bark, and its orbit combinations
		orbits.add(new Orbit("Hallitharam", "Bark", 6, 4, new Velocity(10, "megamiles/hour")));
		orbits.add(new Orbit("Hallitharam", "Bark", 5, 2, new Velocity(14, "megamiles/hour")));
		orbits.add(new Orbit("RK Puram", "Bark", 15, 8, new Velocity(18, "megamiles/hour")));
		orbits.add(new Orbit("Silk Drob", "Bark", 15, 6, new Velocity(22, "megamiles/hour")));
		orbits.add(new Orbit("Bark", "Hallitharam", 5, 1, new Velocity(17, "megamiles/hour")));
		orbits.add(new Orbit("Bark", "RK Puram", 16, 7, new Velocity(20, "megamiles/hour")));
		orbits.add(new Orbit("Bark", "Silk Drob", 16, 8, new Velocity(18, "megamiles/hour")));
		
		return orbits;
	}
}