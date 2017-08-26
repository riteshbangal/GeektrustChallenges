/*
* Copyright (c) 2017, Ritesh. All rights reserved.
*
*/
package in.geektrust.lengaburu.traffic.validator;

import in.geektrust.lengaburu.traffic.beans.WeatherType;
import in.geektrust.lengaburu.traffic.initializer.LengaburuTrafficInitializer;

/**
 * DESCRIPTION - This is a validator class injected in LengaburuTrafficCalculator.
 * 
 * It performs following operations:
 * 	1.	Validate all input parameters
 * 	2.  For invalid input or any error, generate a meaningful error message
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
	 * 	-	Input's should exist in the recoreds.
	 * 	-	Source and destination can't be same.
	 * 
	 * @param pWeatherType - User input
	 * @param pSource - User input
	 * @param pDestination - User input
	 * @return - If something invalid, it will have invalid message
	 */
	public String validateUserInputs(String pWeatherType, String pSource, String pDestination) {
		StringBuilder invalidMessage = new StringBuilder();
		// Validate weather type
		if (!WeatherType.contains(pWeatherType)) {
			invalidMessage.append("Please enter a valid weather type. This weather type doesn't exist.");
		}
		
		if (!isValidSuburbs(pSource, pDestination)) {
			invalidMessage.append("\nPlease enter a valid source/destination type. Either any or both of these suburbs doesn't exist.");
		} else if (pSource.equalsIgnoreCase(pDestination)) {
			invalidMessage.append("\nSource and destination can't be same.");
		}
		
		return invalidMessage.toString();
	}

	/**
	 * Checks if source and destination are existing records.
	 * 
	 * Note: Here source and destination, can't be interchanged. As road could be two ways. 
	 * 
	 * @param pSource - User input
	 * @param pDestination - User input
	 * @return - If exists true, else false.
	 */
	private boolean isValidSuburbs(String pSource, String pDestination) {
		return initializer.getAllOrbits().stream()
			   .anyMatch(orbit -> orbit.getSource().equalsIgnoreCase(pSource)
							   || orbit.getDestination().equalsIgnoreCase(pDestination));
	}
	
}