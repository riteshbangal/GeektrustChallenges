/*
* Copyright (c) 2017, Ritesh. All rights reserved.
*
*/
package in.geektrust.lengaburu.traffic_v2;

import java.util.Scanner;

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
		
		// Create an instance of LengaburuTrafficCalculator, which will calculate optimum time.
		LengaburuTrafficFinder trafficFinder = new LengaburuTrafficFinder();

		Scanner scanner = new Scanner(System.in);

		// Flag to continue the program in a loop
		boolean continueExecution = true;
		do {
			// Input for weather
			System.out.println("Enter weather in Lengaburu. Options ['Sunny', 'Rainy', 'Windy'] :");
			String weather = scanner.nextLine();
			
			// Input for source
			System.out.println("Enter source. Options ['Hallitharam', 'Silk Drob', 'RK Puram', 'Bark'] :");
			String source = scanner.nextLine();

			// Input for destination 1
			System.out.println("Enter destination. Options ['Hallitharam', 'Silk Drob', 'RK Puram', 'Bark'] :");
			String firstDestination = scanner.nextLine();
			
			// Input for destination 2
			System.out.println("Enter another destination. Options ['Hallitharam', 'Silk Drob', 'RK Puram', 'Bark'] :");
			String secondDestination = scanner.nextLine();
			
			// Display output
			System.out.println("Expected Output: " + trafficFinder.calculateOptimumTime(weather, source, firstDestination, secondDestination));
			
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