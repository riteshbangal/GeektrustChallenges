/*
* Copyright (c) 2017, Ritesh. All rights reserved.
*
*/
package in.geektrust.lengaburu.traffic.validator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import in.geektrust.lengaburu.traffic.beans.WeatherType;
import in.geektrust.lengaburu.traffic.initializer.LengaburuTrafficInitializer;
import in.geektrust.lengaburu.traffic.utils.ObjectValidationUtils;

/**
 * DESCRIPTION - This is a validator class, injected in LengaburuTrafficFinder and LengaburuTrafficHelper.
 * 
 * It performs following operations:
 * 	1.	Validate all input parameters
 * 	2.  For invalid input or any error, generate a meaningful error message.
 * 
 * @author - Ritesh Bangal
 * @version 1.0
 * @since <22-August-2017>
 */
public class LengaburuTrafficValidator {

	// Create an object of SingleObject
	private static LengaburuTrafficValidator validatorInstance = new LengaburuTrafficValidator();
	
	// Get the only object available for LengaburuTrafficInitializer
	private static LengaburuTrafficInitializer initializer = LengaburuTrafficInitializer.getInstance();

	private LengaburuTrafficValidator() {
		// Make the constructor private so that this class cannot be instantiated
	}

	// Get the only object available
	public static LengaburuTrafficValidator getInstance(){
		return validatorInstance;
	}

	/**
	 * This method is responsible to validate input parameters. 
	 * It performs following operations:
	 * 	-	Validation for user inputs (i.e. weather type and orbit's speed limit)
	 * 
	 * @param pWeatherType - User input
	 * @param pOrbitSpeedLimitMap - Map for user's input (speed limit) and corresponding orbit name 
	 * 
	 * @return - If something invalid, it will have invalid message
	 */
	public String validateUserInputs(String pWeatherType, Map<String, Integer> pOrbitSpeedLimitMap) {
		
		StringBuilder invalidMessage = new StringBuilder();
		// Validate weather type
		if (!WeatherType.contains(pWeatherType)) {
			invalidMessage.append("Please enter a valid weather type. Input doesn't exist.\n");
		} 
		
		// Validation for available orbits for the given source and destination.
		if (null == pOrbitSpeedLimitMap || pOrbitSpeedLimitMap.isEmpty()) {
			invalidMessage.append("No route/orbit found for the given source-destination.");
		} else {
			List<String> invalidOrbitNames = pOrbitSpeedLimitMap.keySet().stream()
				.filter(orbitName -> pOrbitSpeedLimitMap.get(orbitName) < 1).collect(Collectors.toList());
			if (ObjectValidationUtils.isNotEmpty(invalidOrbitNames)) {
				invalidMessage.append("Invalid input(s) (speed limit) for ")
					.append(invalidOrbitNames).append(". It should be +ve integer.");
			}
		}
		
		return invalidMessage.toString();
	}

	/**
	 * Checks if any orbit exists for given source and destination combination.
	 * 
	 * Note: Here source and destination, can't be interchanged. As road could be two ways. 
	 * 
	 * @param pSource - User input
	 * @param pDestination - User input
	 * @return - If exists true, else false.
	 */
	public boolean isOrbitExists(String pSource, String pDestination) {
		return initializer.getAllOrbits().parallelStream().anyMatch(orbit -> orbit.getSource().equalsIgnoreCase(pSource)
				&& orbit.getDestination().equalsIgnoreCase(pDestination));
	}
}
