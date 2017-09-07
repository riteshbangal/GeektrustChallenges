/*
* Copyright (c) 2017, Ritesh. All rights reserved.
*
*/
package in.geektrust.lengaburu.traffic;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import in.geektrust.lengaburu.traffic.beans.Orbit;
import in.geektrust.lengaburu.traffic.utils.ObjectValidationUtils;

/**
 * DESCRIPTION - This class is responsible to test Lengaburu Traffic problem 2.
 * 
 * Problem 2: Lengaburu Traffic
 * King Shan now would like to visit Hallitharam and RK Puram on the same day. 
 * Write code to determine which orbits & vehicle he should take to visit both destinations in the quickest possible time.
 * 
 * Note: You can choose only 1 vehicle for the entire trip. You cannot change vehicle after reaching the first destination. 
 * 
 * This class will take input from the user, and will give the output accordingly.
 * Inputs: 	Weather, Source, Destination 1, Destination 2;
 * Output: 	Shortest possible time to reach to visit both destinations from the source;
 * 		    Output contains vehicle and orbits/routes details for both destination.
 * 
 * @author - Ritesh
 * @version 1.0
 * @since <26-August-2017>
 */
public class RunLengaburuTrafficSecondProblem {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		// Create an instance of LengaburuTrafficFinder, which will calculate optimum time.
		LengaburuTrafficFinder trafficFinder = new LengaburuTrafficFinder();

		// Create an instance of Scanner, to scan user inputs
		Scanner scanner = new Scanner(System.in);

		// Flag to continue the program in a loop
		boolean continueExecution = true;
		do {
			// Input for weather
			System.out.print("Input: Enter weather in Lengaburu. Options ['Sunny', 'Rainy', 'Windy']: ");
			String weather = scanner.nextLine();
			
			/*
			 * Input for source. For this problem, source and destinations are always same.
			 * i.e. King Shan now would like to visit Hallitharam and RK Puram on the same day.
			 * So source is 'Silk Drob' and destinations are  'Hallitharam' and 'RK Puram'
			 */
			String source = "Silk Drob";
			String firstDestination = "Hallitharam";
			String secondDestination = "RK Puram";
			
			/*
			 * Get a list of destinations, to be traversed.
			 * Get all possible orbit-sequences to traverse multiple destination. 
			 */
			List<List<Orbit>> availableOrbitSequences = trafficFinder.getLengaburuTrafficHelper()
					.getAvailableOrbitSequences(source, ObjectValidationUtils.getList(firstDestination, secondDestination));
		
			// Get all available orbit names and arrange them in sequence. Will be used while getting from user input.
			Set<String> availableOrbitNames = new TreeSet<>();
			availableOrbitSequences.stream().forEach(orbitSequence -> orbitSequence.stream()
					.forEach(orbit -> availableOrbitNames.add(orbit.getOrbitName())));

			/*
			 * This map is used to hold user inputs (orbit's speed limit) corresponding to orbit name.
			 * Will be used while doing input validation.
			 */
			Map<String, Integer> orbitSpeedLimitMap = new LinkedHashMap<>();
			for (String orbitName : availableOrbitNames) {
				System.out.print("Input: Enter the  max traffic speed of " + orbitName + ": ");
				// Input for speed limit of the orbit
				String input = scanner.nextLine();
				
				// Parse input speed from string to integer. Put into the map, to be used in coming iteration
				orbitSpeedLimitMap.put(orbitName, trafficFinder.getLengaburuTrafficHelper().parseOrbitSpeed(input));
			}
			
			// Display output
			System.out.println("\nOutput: " + trafficFinder.calculateOptimumTimeForMultipleDestinations(weather, availableOrbitSequences, orbitSpeedLimitMap));
			
			System.out.println("\n***********************************************************");
			// Control execution loop
			System.out.println("Press Enter to continue or Enter 'q/Q' to stop the program...");
			System.out.println("***********************************************************");
			String quitCmd = scanner.nextLine();
			if ("q".equalsIgnoreCase(quitCmd)) {
				continueExecution = false;
			}
		} while (continueExecution);
	}
}
