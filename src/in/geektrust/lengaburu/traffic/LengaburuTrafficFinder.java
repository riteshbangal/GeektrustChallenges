/*
* Copyright (c) 2017, Ritesh. All rights reserved.
*
*/
package in.geektrust.lengaburu.traffic;

import java.util.List;

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
 *  1.	Goal: To go from Source(e.g. Silk Dorb) to  destination (e.g. Hallitharam) in the shortest possible time.
 * 	2.	
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
	 * This method is responsible to calculate optimum time to reach from source to destination.
	 * 
	 * To do this it performs following operations:
	 *  1.	Get weather by weather-type.
	 *  2. 	Get all suitable vehicle names from the weather and get their corresponding Vehicle objects
	 *  3. 	Get all the available orbits between any source and destination 
	 * 	4.	Get all possible routes between any source and destination. 
	 * 	5.	Now, based on weather type we can identify actual number of craters and possible vehicles for each orbit.
	 * 	6.	Find out optimum traverse time for each orbit/route and vehicle combination.
	 * 	7.	Compare these times and find out the optimized one.
	 * 
	 * @param pWeatherType - User input
	 * @param pSource - User input
	 * @param pDestination - User input
	 * @return - Success or failure message after doing calculation for optimum time. 
	 */
	public String calculateOptimumTime(String pWeatherType, String pSource, String pDestination) {
		
		// Validate input parameters. If something invalid, it will have invalid message
		String invalidMessage = validator.validateUserInputs(pWeatherType, pSource, pDestination);
		
		// If user inputs are not valid, return the invalid message
		if (ObjectValidationUtils.isNotBlank(invalidMessage)) {
			return invalidMessage;
		}
		
		// Get weather by weather-type
		Weather weather = helper.getWeatherByType(pWeatherType);
		
		// Get all suitable vehicle names for the selected weather and get their corresponding Vehicle objects
		List<Vehicle> vehicles = helper.getSuitableVehicles(weather.getSuitableVehicleNames());
		
		// Get all the available orbits between any source and destination 
		List<Orbit> availableOrbits = helper.getAvailableOrbits(pSource, pDestination);
		
		/*
		 * Populate TraverseDetail object with the traverse time, orbit and vehicle.
		 * Get list of all populated TraverseDetail objects.
		 */
		List<TraverseDetail> traverseDetails = helper.getTraverseDetails(weather, vehicles, availableOrbits);
		
		// Find out the optimum TraverseDetail object from list of traverseDetails
		TraverseDetail optimumTraverseDetail = helper.findOptimumTraverseDetail(traverseDetails);
		
		// Check optimum TraverseDetail object
		if (null == optimumTraverseDetail) {
			return "System Error: Unable to findout shortest possible time";
		} else {
			// Generate and return output message from optimized TraverseDetail object
			return helper.generateOutputmessage(optimumTraverseDetail);
		}
	}
}