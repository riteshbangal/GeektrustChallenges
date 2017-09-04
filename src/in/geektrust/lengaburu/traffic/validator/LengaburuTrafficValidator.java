/*
* Copyright (c) 2017, Ritesh. All rights reserved.
*
*/
package in.geektrust.lengaburu.traffic.validator;

import in.geektrust.lengaburu.traffic.beans.WeatherType;
import in.geektrust.lengaburu.traffic.initializer.LengaburuTrafficInitializer;

/**
 * DESCRIPTION - This is a validator class, injected in LengaburuTrafficCalculator and LengaburuTrafficHelper.
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
	
	private static final String SOURCE 		= "source";
	private static final String DESTINATION = "destination";

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
	 * 	-	Inputs should exist in records.
	 * 	-	Validation for Source and destination
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
			invalidMessage.append("Please enter a valid weather type. Input doesn't exist.");
		}
		
		// Validation for Source and destination
		validateSuburb(pSource, pDestination, invalidMessage);
		
		return invalidMessage.toString();
	}

	/**
	 * This method is responsible to validate input parameters. 
	 * It performs following operations:
	 * 	-	Input's should exist in the records.
	 * 	-	Source and destination can't be same.
	 *  
	 * @param pSource - User input
	 * @param pDestination - User input
	 * @param invalidMessage - If something invalid, populate invalid message
	 */
	private void validateSuburb(String pSource, String pDestination, StringBuilder invalidMessage) {
		if (!isValidSuburb(pSource, SOURCE)) {
			invalidMessage.append("\nPlease enter a valid source. Input doesn't exist.");
		} else if (!isValidSuburb(pDestination, DESTINATION)) {
			invalidMessage.append("\nPlease enter a valid destination. Input doesn't exist.");
		} else if (!isOrbitExists(pSource, pDestination)) {
			invalidMessage.append("\nNo route/orbit found for this source-destination (")
						  .append(pSource).append(" - ").append(pDestination).append(") combination.");
		}
	}

	/**
	 * This method is responsible to validate input parameters. 
	 * It performs following operations:
	 * 	-	Input weather type should exist in records.
	 * 	-	Validation for Source and different destinations, exist or not.
	 * 
	 * @param pWeatherType - User input
	 * @param pSource - User input
	 * @param pFirstDestination - User input
	 * @param pSecondDestination - User input
	 * @return - If something invalid, it will have invalid message
	 */
	public String validateUserInputs(String pWeatherType, String pSource, String pFirstDestination, String pSecondDestination) {
		StringBuilder invalidMessage = new StringBuilder();
		// Validate weather type
		if (!WeatherType.contains(pWeatherType)) {
			invalidMessage.append("Please enter a valid weather type. Input doesn't exist.");
		}
		
		// Validation for source and destinations. If they exist.
		if (!isValidSuburb(pSource, SOURCE)) {
			invalidMessage.append("\nPlease enter a valid source. Input doesn't exist.");
		} else if (!isValidSuburb(pFirstDestination, DESTINATION) || !isValidSuburb(pSecondDestination, DESTINATION)) {
			invalidMessage.append("\nPlease enter a valid destinations. Inputs don't exist.");
		} else if (pSource.equalsIgnoreCase(pFirstDestination) || pSource.equalsIgnoreCase(pSecondDestination) 
				|| pFirstDestination.equalsIgnoreCase(pSecondDestination)) {
			invalidMessage.append("\nSuburbs (Source/Destinations) can't be same. Please enter valid inputs.");
		}
		
		return invalidMessage.toString();
	}

	/**
	 * Checks if source and destination are existing records.
	 * 
	 * Note: Here source and destination, can't be interchanged. As road could be two ways. 
	 * 
	 * @param pSuburb - Source/Destination. User input
	 * @param pSuburb - SuburbType: source/destination.
	 * 
	 * @return - If exists true, else false.
	 */
	private boolean isValidSuburb(String pSuburb, String pSuburbType) {
		if (SOURCE.equalsIgnoreCase(pSuburbType)) {
			return initializer.getAllOrbits().parallelStream()
					.anyMatch(orbit -> orbit.getSource().equalsIgnoreCase(pSuburb));
		} else if (DESTINATION.equalsIgnoreCase(pSuburbType)) {
			return initializer.getAllOrbits().parallelStream()
					.anyMatch(orbit -> orbit.getDestination().equalsIgnoreCase(pSuburb));
		}
		return false;
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