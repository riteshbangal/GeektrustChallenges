/*
* Copyright (c) 2017, Ritesh. All rights reserved.
*
*/
package in.geektrust.lengaburu.traffic.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import in.geektrust.lengaburu.traffic.beans.Orbit;
import in.geektrust.lengaburu.traffic.beans.TraverseDetail;
import in.geektrust.lengaburu.traffic.beans.Vehicle;
import in.geektrust.lengaburu.traffic.beans.Weather;
import in.geektrust.lengaburu.traffic.initializer.LengaburuTrafficInitializer;
import in.geektrust.lengaburu.traffic.validator.LengaburuTrafficValidator;

/**
 * DESCRIPTION - This is a helper class injected in LengaburuTrafficFinder.
 * 
 * It performs following operations:
 * 	1.	Get all suitable vehicle names for the weather and get their corresponding Vehicle objects.
 * 	2.	Get all possible orbits/route between any source and destination or get orbit sequences, in case of multiple destinations. 
 * 	3.	Identify actual number of craters and suitable vehicles on any orbit, based on weather.
 * 	4.	Calculate optimum traverse time, for each route and vehicle and populate list of TraverseDetail objects.
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

	// Get the only object available for LengaburuTrafficValidator
	private static LengaburuTrafficValidator validator = LengaburuTrafficValidator.getInstance();

	private LengaburuTrafficHelper() {
		// Make the constructor private so that this class cannot be instantiated
	}

	// Get the only object available
	public static LengaburuTrafficHelper getInstance(){
		return helperInstance;
	}
	
	/**
	 * Parse input speed from string to integer.
	 * 
	 * @param pSpeedLimit - User input
	 * @return Integer value. If input is valid return actual entered value, else -1 
	 */
	public int parseOrbitSpeed(String pSpeedLimit) {
		try {
			return Integer.parseInt(pSpeedLimit);
		} catch (NumberFormatException e) {
			return -1;
		}
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
	 * Get vehicle objects corresponding to vehicle names.
	 * 
	 * @param pVehicleNames - suitable vehicle names for the weather
	 * @return - List of Vehicle objects
	 */
	public List<Vehicle> getSuitableVehicles(List<String> pVehicleNames) {
		return initializer.getAllVehicles().stream()
				.filter(vehicle -> pVehicleNames.contains(vehicle.getName()))
				.collect(Collectors.toList());
	}
	
	/**
	 * Find out all available sequence of routes/orbits between any source and destination.
	 * Here each orbit-sequence contains only one orbit, i.e. orbit between any source and destination.
	 * This source and destination is coming through user-input.
	 * 
	 * Note: Here source and destination, can't be interchanged. As road could be two ways. 
	 * 
	 * @param pSource - User input
	 * @param pDestination - User input
	 * @return - all possible orbit/route - sequence between any source and destination.
	 */
	public List<List<Orbit>> getAvailableOrbits(String pSource, String pDestination) {
		return initializer.getAllOrbits().stream()
				.filter(orbit -> orbit.getSource().equalsIgnoreCase(pSource)
							  && orbit.getDestination().equalsIgnoreCase(pDestination))
				.map(Arrays::asList)
				.collect(Collectors.toList());
	}
	
	/**
	 * This method is responsible for:
	 * 		-  	Iterates over vehicles and available orbit-sequences.
	 * 		- 	Populate TraverseDetail object with the traverse time, orbit-sequence and vehicle.
	 * 		-	Add all populated TraverseDetail objects into a list and return it back.
	 * 
	 * @param pWeather - User input
	 * @param pVehicles - Suitable vehicles for input weather
	 * @param pAvailableOrbitSequences - Available orbits/routes sequences, to traverse multiple destinations
	 * 
	 * @return List of TraverseDetailForMultiSuburbs objects for these vehicles and orbit-sequences
	 */
	public List<TraverseDetail> getTraverseDetails(Weather pWeather,
			List<Vehicle> pVehicles, List<List<Orbit>> pAvailableOrbitSequences) {

		List<TraverseDetail> traverseDetails = new ArrayList<>();
		pVehicles.stream()
				.forEach(vehicle -> pAvailableOrbitSequences.stream()
						.forEach(orbitSequence -> {
							// Populate TraverseDetailForMultiSuburbs object with the traverse time, orbit-sequence and vehicle.
							TraverseDetail traverseDetail = new TraverseDetail();
							
							// Get optimized time to traverse an orbit with a vehicle, 
							traverseDetail.setTraverseTime(calculateOptimizedTraverseTime(pWeather, vehicle, orbitSequence));
							traverseDetail.setOrbit(orbitSequence);
							traverseDetail.setVehicle(vehicle);
							
							// Add all populated TraverseDetailForMultiSuburbs objects into a list
							traverseDetails.add(traverseDetail);
							//System.out.println(traverseDetail);
						})
				);
		return traverseDetails;
	}

	/**
	 * This method is responsible for:
	 * 		-	Identify actual number of craters based on weather.
	 * 		-	Calculate optimum traverse time for any orbit and vehicle combination.
	 * 
	 * Calculation steps:
	 *  	1. 	Get total distance for all the orbits in the sequence.
	 *  	2.	Calculate maximum speed from orbit's speed limit and vehicle's maximum speed.
	 *  	3.	Apply change rate on given craters and get actual applicable crater's number.
	 *  	4. 	Convert crater cross time into velocity time unit.
	 *  	5. 	Calculate traverse time = distance/applicable speed + crater cross time * number of actual craters.
	 *  
	 *  Assumption: Unit of speed limit of orbit-sequence and vechicle's speed should be same. Default unit is megamiles/hour.
	 *  
	 * @param pWeather - Weather object
	 * @param pVehicle - Vehicle object
	 * @param pOrbit - Sequence of Orbit objects
	 * 
	 * @return Optimized traverse time for orbit-sequence with a vehicle
	 */
	private static Integer calculateOptimizedTraverseTime(Weather pWeather, Vehicle pVehicle, List<Orbit> pOrbitSequence) {
		
		// Get distance of the orbit. Default unit is megamiles. 
		int distance = pOrbitSequence.stream()
				.mapToInt(Orbit::getDistance).sum();
		
		// Get number of craters from orbit-sequence. 
		int numberOfCratersFromOrbitSequence = pOrbitSequence.stream()
				.mapToInt(Orbit::getNumberOfCraters).sum();
		
		// Get minimum orbit's speed limit among those sequence. Default unit is megamiles/hour.
		int orbitSpeedLimit = pOrbitSequence.stream()
				.mapToInt(orbit -> orbit.getVelocityLimit().getSpeed())
				.min().getAsInt();
		
		// Get vehicle's maximum speed. Default unit is megamiles/hour.
		int vehicleMaxSpeed = pVehicle.getVelocity().getSpeed();
		
		/*
		 * A vehicle cannot travel faster than the traffic speed limit of an orbit-sequence.
		 * Calculate maximum speed, which is applicable for the orbit-sequence with that vehicle.
		 */
		int applicableMaxSpeed = (orbitSpeedLimit > vehicleMaxSpeed) ? vehicleMaxSpeed : orbitSpeedLimit;
		
		// Apply change rate on given craters and get actual applicable crater's number.
		int actualNumberOfCraters = (int) Math.round(numberOfCratersFromOrbitSequence * (100 + pWeather.getCraterChangeRate()) / 100.00);
		
		/*
		 * Calculate traverse time in minutes. Default unit for crossing craters with a vehicle is in minutes.
		 * Return optimized traverse time for an orbit-sequence with a vehicle
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
	 * This method gets all possible orbit-sequences to traverse multiple destination. 
	 * It performs following operations:
	 * 	1. 	Get orbits for source and it's adjacent suburb. Add each of these orbits into separate orbit-sequence list.
	 * 	2.	Get all next available orbits among different destinations and add them into corresponding orbit-sequence list.
	 * 
	 * This source and destinations are coming through user-input.
	 * Note: Here source and destination, can't be interchanged. As road could be two ways. 
	 * 		
	 * TODO: Currently it's supporting only for two destinations.
	 * 		 This method needs more refactoring to make it more scalable, to support more than two destinations. 
	 * 
	 * @param pSource - User input
	 * @param pDestinations - User inputs, list of destinations
	 * @return - All possible sequences of orbits/routes between any source and different destinations.
	 */
	public List<List<Orbit>> getAvailableOrbitSequences(String pSource, List<String> pDestinations) {
		
		List<List<Orbit>> orbitsSequences = new ArrayList<>();
		
		// Check and create a possible list of orbits between source and it's adjacent (i.e. first) destination.
		List<Orbit> availableFirstOrbits = new ArrayList<>();
		pDestinations.stream()
			.filter(destination -> validator.isOrbitExists(pSource, destination))
			.forEach(destination -> initializer.getAllOrbits().stream()
					.filter(orbit -> orbit.getSource().equalsIgnoreCase(pSource)
								  && orbit.getDestination().equalsIgnoreCase(destination))
					.forEach(availableFirstOrbits::add));
		
		// Check and create list of orbits between via/intermediate destination (i.e. first) and final (i.e. second) destination.
		availableFirstOrbits.stream()
			.forEach(orbit -> pDestinations.stream()
				.filter(nextDestination -> validator.isOrbitExists(orbit.getDestination(), nextDestination))
				.forEach(nextDestination -> initializer.getAllOrbits().stream()
					.filter(nextOrbit -> nextOrbit.getSource().equalsIgnoreCase(orbit.getDestination())
									  && nextOrbit.getDestination().equalsIgnoreCase(nextDestination))
					.forEach(nextOrbit -> {
						List<Orbit> orbits = new ArrayList<>();
						orbits.add(orbit);
						orbits.add(nextOrbit);
						
						// Add all possible orbits.
						orbitsSequences.add(orbits);
						//System.out.println("Orbits: " + orbits);
					})
				)
			);	
		return orbitsSequences;
	}

	/**
	 * This method is responsible to generate output message from optimized TraverseDetail object 
	 * 
	 * @param pOptimumTraverseDetail - Optimized TraverseDetail object
	 * @param pProblemType - To make it generic, based on problem type (i.e. Problem1/Problem2), output will be generated.
	 * 
	 * @return - Output message from OptimumTraverseDetail object
	 */
	public String generateOutputmessage(TraverseDetail pOptimumTraverseDetail, String pProblemType) {
		StringBuilder output = new StringBuilder();
		output.append("Vehicle ").append(pOptimumTraverseDetail.getVehicle().getName());
		
		switch(pProblemType) {
		case "Problem1" :
			pOptimumTraverseDetail.getOrbits().stream()
				.forEach(orbit -> output.append(" on ").append(orbit.getOrbitName()));
			break;
		case "Problem2" :
			Orbit firstOrbit = pOptimumTraverseDetail.getOrbits().get(0);
			Orbit secondOrbit = pOptimumTraverseDetail.getOrbits().get(1);
			output.append(" to ").append(firstOrbit.getDestination()).append(" via ").append(firstOrbit.getOrbitName())
				.append(" and ")
				.append(secondOrbit.getDestination()).append(" via ").append(secondOrbit.getOrbitName());
			break;
		default :
			System.out.println("No output.");
		}
		
		return output.toString();
	}
}
