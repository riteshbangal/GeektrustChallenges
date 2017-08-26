/*
* Copyright (c) 2017, Ritesh. All rights reserved.
*
*/
package in.geektrust.lengaburu.traffic.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import in.geektrust.lengaburu.traffic.beans.Orbit;
import in.geektrust.lengaburu.traffic.beans.TraverseDetail;
import in.geektrust.lengaburu.traffic.beans.Vehicle;
import in.geektrust.lengaburu.traffic.beans.Weather;
import in.geektrust.lengaburu.traffic.initializer.LengaburuTrafficInitializer;

/**
 * DESCRIPTION - This is a helper class injected in LengaburuTrafficCalculator.
 * 
 * It performs following operations:
 * 	1.	Get all suitable vehicle names from the weather and get their corresponding Vehicle objects 
 * 	2.	Get all possible orbits/routes between any source and destination. 
 * 	3.	Identify actual number of craters and possible vehicles on any orbit, based on weather.
 * 	4.	Find out all optimum traverse times, for each route and vehicle.
 * 	5.	Compare these traverse times and find out the optimized one.
 * 
 * @author - Ritesh Bangal
 * @version 1.0
 * @since <22-August-2017>
 */
public class LengaburuTrafficHelper {
	
	// Create an object of SingleObject
	private static LengaburuTrafficHelper helperInstance = new LengaburuTrafficHelper();
	
	private static final int MINUTES_PER_HOUR = 60;
	
	// Get the only object available for LengaburuTrafficInitializer
	private static LengaburuTrafficInitializer initializer = LengaburuTrafficInitializer.getInstance();

	private LengaburuTrafficHelper() {
		// Make the constructor private so that this class cannot be instantiated
	}

	// Get the only object available
	public static LengaburuTrafficHelper getInstance(){
		return helperInstance;
	}
	
	/**
	 * Find weather by weather type, from all available weathers.
	 * 
	 * @param pWeatherType - User input
	 * @return - Matched Weather.
	 */
	public Weather getWeatherByType(String pWeatherType) {
		return initializer.getAllWeatherDetails().parallelStream()
				.filter(weather -> pWeatherType.equalsIgnoreCase(weather.getWeatherType().toString()))
				.findAny().orElse(null);
	}
	
	/**
	 * Search vehicles by name and get suitable vehicle objects from available vehicles,
	 * 
	 * @param pVehicleNames - suitable vehicle names from the weather
	 * @return - List of suitable vehicles
	 */
	public List<Vehicle> getSuitableVehicles(List<String> pVehicleNames) {
		return initializer.getAllVehicles().parallelStream()
				.filter(vehicle -> pVehicleNames.contains(vehicle.getName()))
				.collect(Collectors.toList());
	}
	
	/**
	 * Find out all available routes/orbits between any source and destination.
	 * This source and destination is coming through user-input.
	 * 
	 * Note: Here source and destination, can't be interchanged. As road could be two ways. 
	 * 
	 * @param pSource - User input
	 * @param pDestination - User input
	 * @return - all possible orbits/routes between any source and destination.
	 */
	public List<Orbit> getAvailableOrbits(String pSource, String pDestination) {
		return initializer.getAllOrbits().parallelStream()
				.filter(orbit -> orbit.getSource().equalsIgnoreCase(pSource)
							  && orbit.getDestination().equalsIgnoreCase(pDestination))
				.collect(Collectors.toList());
	}
	
	/**
	 * This method is responsible for:
	 * 		-  	Iterates over vehicles and available orbits.
	 * 		- 	Populate TraverseDetail object with the traverse time, orbit and vehicle.
	 * 		-	Add all populated TraverseDetail objects into a list and return it back.
	 * 
	 * @param pWeather - User input
	 * @param pVehicles - Suitable vehicles for input weather
	 * @param pAvailableOrbits - Available orbits/routes between given source and destination
	 * 
	 * @return List of TraverseDeatail objects for these vehicles and orbits
	 */
	public List<TraverseDetail> getTraverseDetails(Weather pWeather, List<Vehicle> pVehicles, List<Orbit> pAvailableOrbits) {
		
		List<TraverseDetail> traverseDetails = new ArrayList<>();
		pVehicles.stream()
				.forEach(vehicle -> pAvailableOrbits.stream()
						.forEach(orbit -> {
							// Populate TraverseDetail object with the traverse time, orbit and vehicle.
							TraverseDetail traverseDetail = new TraverseDetail();
							
							// Get optimized time to traverse an orbit with a vehicle, 
							traverseDetail.setTraverseTime(calculateOptimizedTraverseTime(pWeather, vehicle, orbit));
							traverseDetail.setOrbit(orbit);
							traverseDetail.setVehicle(vehicle);
							
							// Add all populated TraverseDetail objects into a list
							traverseDetails.add(traverseDetail);
							//System.out.println(traverseDetail);
						})
				);
		return traverseDetails;
	}

	/**
	 * This method is responsible for:
	 * 		-	Identify actual number of craters based on weather.
	 * 		-	Get optimum traverse time for any particular orbit and vehicle.
	 * 
	 * Calculation steps:
	 *  	1. 	Get distance of the orbit.
	 *  	2.	Calculate maximum speed from orbit's speed limit and vehicle's maximum speed.
	 *  	3.	Apply change rate on given craters and get actual applicable crater's number.
	 *  	4. 	Convert crater cross time into velocity time unit.
	 *  	5. 	Calculate traverse time = distance/applicable speed + crater cross time * number of actual craters.
	 *  
	 *  Assumption: Unit of speed limit of an orbit and vechicle's speed should be same. Default unit is megamiles/hour.
	 *  
	 * @param pWeather - Weather object
	 * @param pVehicle - Vehicle object
	 * @param pOrbit - Orbit object
	 * 
	 * @return Optimized traverse time for an orbit with a vehicle
	 */
	private static Integer calculateOptimizedTraverseTime(Weather pWeather, Vehicle pVehicle, Orbit pOrbit) {
		
		// Get distance of the orbit. Default unit is megamiles. 
		int distance = pOrbit.getDistance();
		
		// Get orbit's speed limit. Default unit is megamiles/hour.
		int orbitSpeedLimit = pOrbit.getVelocityLimit().getSpeed();
		
		// Get vehicle's maximum speed. Default unit is megamiles/hour.
		int vehicleMaxSpeed = pVehicle.getVelocity().getSpeed();
		
		/*
		 * A vehicle cannot travel faster than the traffic speed limit of an orbit.
		 * Calculate maximum speed, which is applicable for the orbit with that vehicle.
		 */
		int applicableMaxSpeed = (orbitSpeedLimit > vehicleMaxSpeed) ? vehicleMaxSpeed : orbitSpeedLimit;
		
		// Apply change rate on given craters and get actu	al applicable crater's number.
		int actualNumberOfCraters = pOrbit.getNumberOfCraters() * (100 + pWeather.getCraterChangeRate()) / 100;
		
		/*
		 * Calculate traverse time in minutes. Default unit for crossing craters with a vehicle is in minutes.
		 * Return optimized traverse time for an orbit with a vehicle
		 */
		return ((distance * MINUTES_PER_HOUR) / applicableMaxSpeed) 
				+ (actualNumberOfCraters * pVehicle.getTimeToCrossCrater());
	}
	
	/**
	 * Compare TraverseDetail objects and find out the optimized one, based on traverse time.
	 * 
	 * Note: Time complexity of this solution is O(n).
	 * 
	 * @param pTraverseDetails - List of all populated TraverseDetail objects
	 * @return - Optimum TraverseDetail object among the list
	 */
	public TraverseDetail findOptimumTraverseDetail(List<TraverseDetail> pTraverseDetails) {
		
		TraverseDetail optimumTraverseDetail = null;
		int minimumTime = Integer.MAX_VALUE;
		for (int i = 0; i < pTraverseDetails.size(); i++) {
			int traverseTime = pTraverseDetails.get(i).getTraverseTime();
			if (traverseTime < minimumTime) {
				minimumTime = traverseTime;
				
				// No new memory allocation is happening, it's just a change of reference
				optimumTraverseDetail = pTraverseDetails.get(i); 
			} else if (traverseTime == minimumTime) {
				// If there is a tie in which vehicle to choose, use bike, auto/tuktuk, car in that order.
				// TODO: Need to consider in case of parallel processing with huge records. Currently it's working fine.
				//System.out.println("traverseTime == minimumTime");
			}
		}
		return optimumTraverseDetail;
	}

	/**
	 * This method is responsible to generate output message from optimized TraverseDetail object 
	 * 
	 * @param pOptimumTraverseDetail - Optimized TraverseDetail object
	 * @return - Output message from OptimumTraverseDetail object
	 */
	public String generateOutputmessage(TraverseDetail pOptimumTraverseDetail) {
		StringBuilder output = new StringBuilder();
		output.append("Vehicle ").append(pOptimumTraverseDetail.getVehicle().getName()).append(" on ")
				.append(pOptimumTraverseDetail.getOrbit().getSource()).append("-")
				.append(pOptimumTraverseDetail.getOrbit().getDestination()).append(" [Distance: ")
				.append(pOptimumTraverseDetail.getOrbit().getDistance()).append(", Number of Craters: ")
				.append(pOptimumTraverseDetail.getOrbit().getNumberOfCraters()).append("]");
		return output.toString();
	}
}