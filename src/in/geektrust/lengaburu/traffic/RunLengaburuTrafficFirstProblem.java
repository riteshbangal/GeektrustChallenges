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

/**
 * DESCRIPTION - This class is responsible to test Lengaburu Traffic problem 1.
 * 
 * Problem 1: Lengaburu Traffic
 * King Shan wants to visit the suburb of Hallitharam, and has 2 possible orbits and 3 possible vehicles to chose from.
 * Your coding challenge is to determine which orbit and vehicle King Shan should take to reach Hallitharam the fastest.
 * 
 * This class will take input from the user, and will give the output accordingly.
 * Inputs: 	Weather, Source, Destination;
 * Output: 	Shortest possible time to reach to destination from the source;
 * 		    Output contains vehicle and orbit/route details.   
 * 
 * @author - Ritesh
 * @version 1.0
 * @since <22-August-2017>
 */
public class RunLengaburuTrafficFirstProblem {

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
			 * Input for source. For this problem, source and destination is always same.
			 * i.e. King Shan wants to visit the suburb of Hallitharam from Silk Drob.
			 * So source is 'Silk Drob' and destination is 'Hallitharam'
			 */
			String source = "Silk Drob";
			String destination = "Hallitharam";
			
			/*
			 * Get all available sequence of routes/orbits between the source and destination.
			 * Here each orbit-sequence contains only one orbit, i.e. orbit between any source and destination.
			 */
			List<List<Orbit>> availableOrbits = trafficFinder.getLengaburuTrafficHelper()
					.getAvailableOrbits(source, destination);
			
			// Get all available orbit names and arrange them in sequence. Will be used while taking inputs from user.
			Set<String> availableOrbitNames = new TreeSet<>();
			availableOrbits.stream().forEach(orbitSequence -> orbitSequence.stream()
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
			System.out.println("\nOutput: " + trafficFinder.calculateOptimumTimeForSingleDestination(weather, availableOrbits, orbitSpeedLimitMap));
			
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
