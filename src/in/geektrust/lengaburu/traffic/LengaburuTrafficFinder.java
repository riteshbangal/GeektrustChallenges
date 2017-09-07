/*
* Copyright (c) 2017, Ritesh. All rights reserved.
*
*/
package in.geektrust.lengaburu.traffic;

import java.util.List;
import java.util.Map;

import in.geektrust.lengaburu.traffic.beans.Orbit;
import in.geektrust.lengaburu.traffic.beans.TraverseDetail;
import in.geektrust.lengaburu.traffic.beans.Vehicle;
import in.geektrust.lengaburu.traffic.beans.Weather;
import in.geektrust.lengaburu.traffic.helper.LengaburuTrafficHelper;
import in.geektrust.lengaburu.traffic.utils.ObjectValidationUtils;
import in.geektrust.lengaburu.traffic.validator.LengaburuTrafficValidator;

/**
 * DESCRIPTION - This class is solution for problem: Lengaburu Traffic
 * 
 * It contains solutions for following problems: 
 * 1. Problem 1 | Goal: To go from Source(e.g. Silk Dorb) to destination (e.g. Hallitharam) in the shortest possible time.
 * 2. Problem 2 | Goal: To go from Source(e.g. Silk Dorb) to two different destinations (e.g. Hallitharam, RK Puram) in the shortest possible time.
 * 
 * @author - Ritesh Bangal
 * @version 1.0
 * @since <22-August-2017>
 */
public class LengaburuTrafficFinder {

	// Get the only object available for LengaburuTrafficHelper
	private static LengaburuTrafficHelper helper = LengaburuTrafficHelper.getInstance();
    
	// Get the only object available for LengaburuTrafficValidator
	private static LengaburuTrafficValidator validator = LengaburuTrafficValidator.getInstance();
    
	/**
	 * Solution for problem 1.
	 * This method is responsible to calculate optimum time to reach from source to destination.
	 * 
	 * To do this it performs following operations:
	 *  1.	Get weather by weather-type.
	 *  2. 	Based on weather type get all suitable vehicle names and get their corresponding Vehicle objects.
	 *  3. 	Get all available orbits and update them with user inputs (i.e. speed limit)
	 * 	4.	Now, based on weather type we can identify actual number of craters for each available orbit.
	 * 	5.	Find out optimum traverse time for each orbit/route and vehicle combination.
	 * 	6.	Compare these times and find out the optimized one.
	 * 
	 * @param pWeatherType - User input
	 * @param pAvailableOrbits - Updated list of orbit sequences with user's input (speed limit of orbits)
	 * @param pOrbitSpeedLimitMap - This map is used to hold user inputs (orbit's speed limit) corresponding to orbit name.
	 * 
	 * @return - Success or failure message after doing calculation for optimum traverse time. 
	 */
	public String calculateOptimumTimeForSingleDestination(String pWeatherType, 
			List<List<Orbit>> pAvailableOrbits, Map<String, Integer> pOrbitSpeedLimitMap) {
		
		// Validate input parameters. If something invalid, it will have invalid message
		String invalidMessage = validator.validateUserInputs(pWeatherType, pOrbitSpeedLimitMap);
		
		// If all inputs are valid, invalidMessage will be empty. Else if user inputs are not valid, return the invalid message
		if (ObjectValidationUtils.isNotBlank(invalidMessage)) {
			return "Validation failed. Error message(s): \n" + invalidMessage;
		}
		
		// Get weather by weather-type
		Weather weather = helper.getWeatherByType(pWeatherType);
		
		// Get all suitable vehicle names for the selected weather and get their corresponding Vehicle objects
		List<Vehicle> vehicles = helper.getSuitableVehicles(weather.getSuitableVehicleNames());
		
		// Iterate all available orbit-sequences and set it's max speed limit, which came as input.
		pAvailableOrbits.stream()
			.forEach(orbitSequence -> orbitSequence.stream()
				// Update the orbit-sequence with orbit's speed limit
				.forEach(orbit -> orbit.getVelocityLimit().setSpeed(pOrbitSpeedLimitMap.get(orbit.getOrbitName())))
			);

		/*
		 * Populate TraverseDetail object with the traverse time, orbit and vehicle.
		 * Get list of all populated TraverseDetail objects.
		 */
		List<TraverseDetail> traverseDetails = helper.getTraverseDetails(weather, vehicles, pAvailableOrbits);
		
		// Find out the optimum TraverseDetail object from list of traverseDetails
		TraverseDetail optimumTraverseDetail = helper.findOptimumTraverseDetail(traverseDetails);
		
		// Check optimum TraverseDetail object
		if (null == optimumTraverseDetail) {
			return "System Error: Unable to findout shortest possible time";
		} else {
			// Generate and return output message from optimized TraverseDetail object
			return helper.generateOutputmessage(optimumTraverseDetail, "Problem1");
		}
	}
	
	/**
	 * Solution for problem 2.
	 * This method is responsible to calculate optimum time to reach from source to multiple destinations.
	 * 
	 * To do this it performs following operations:
	 *  1.	Get weather by weather-type.
	 *  2. 	Based on weather type get all suitable vehicle names and get their corresponding Vehicle objects.
	 *  3. 	Get all available orbit-sequences to traverse multiple destinations and update them with user inputs (i.e. speed limit)
	 * 	4.	Now, based on weather type we can identify actual number of craters for each available orbit.
	 * 	5.	Find out optimum traverse time for each orbit/route - sequence and vehicle combination.
	 * 	6.	Compare these times and find out the optimized one.
	 * 
	 * Note: Currently it has been implemented for two destinations. 
	 * 		 Logic will have slight change in this method to make it more scalable, to support for more than two destinations.
	 * 
	 * @param pWeatherType - User input
	 * @param pAvailableOrbitSequences - Updated list of orbit sequences with user's input (speed limit of orbits)
	 * @param pOrbitSpeedLimitMap - This map is used to hold user inputs (orbit's speed limit) corresponding to orbit name.
	 * 
	 * @return - Success or failure message after doing calculation for optimum time. 
	 */
	public String calculateOptimumTimeForMultipleDestinations(String pWeatherType,
			List<List<Orbit>> pAvailableOrbitSequences, Map<String, Integer> pOrbitSpeedLimitMap) {

		// Validate input parameters. If something invalid, it will have invalid message
		String invalidMessage = validator.validateUserInputs(pWeatherType, pOrbitSpeedLimitMap);
		
		// If all inputs are valid, invalidMessage will be empty. Else if user inputs are not valid, return the invalid message
		if (ObjectValidationUtils.isNotBlank(invalidMessage)) {
			return "Validation failed. Error message(s): \n" + invalidMessage;
		}
		
		// Get weather by weather-type
		Weather weather = helper.getWeatherByType(pWeatherType);
		
		// Get all suitable vehicle names for the selected weather and get their corresponding Vehicle objects
		List<Vehicle> vehicles = helper.getSuitableVehicles(weather.getSuitableVehicleNames());
		
		// Iterate all available orbit-sequences and set it's max speed limit, which came as input.
		pAvailableOrbitSequences.stream()
			.forEach(orbitSequence -> orbitSequence.stream()
				// Update the orbit-sequence with orbit's speed limit
				.forEach(orbit -> orbit.getVelocityLimit().setSpeed(pOrbitSpeedLimitMap.get(orbit.getOrbitName())))
			);

		/*
		 * Populate TraverseDetail object with the traverse time, sequence of orbits and vehicle.
		 * Get list of all populated TraverseDetail objects.
		 */
		List<TraverseDetail> traverseDetailsWithCombinations = helper
				.getTraverseDetails(weather, vehicles, pAvailableOrbitSequences);
		
		// Find out the optimum TraverseDetail object from list of traverseDetails
		TraverseDetail optimumTraverseDetail = helper
				.findOptimumTraverseDetail(traverseDetailsWithCombinations);
		
		// Check optimum TraverseDetail object
		if (null == optimumTraverseDetail) {
			return "System Error: Unable to findout shortest possible time";
		} else {
			// Generate and return output message from optimized TraverseDetail object
			return helper.generateOutputmessage(optimumTraverseDetail, "Problem2");
		}
	}

	/**
	 * Getter method for LengaburuTrafficHelper.
	 *  
	 * @return LengaburuTrafficHelper instance
	 */
	public LengaburuTrafficHelper getLengaburuTrafficHelper() {
		return helper;
	}
}
