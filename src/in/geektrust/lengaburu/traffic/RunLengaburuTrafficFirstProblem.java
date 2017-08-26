/*
* Copyright (c) 2017, Ritesh. All rights reserved.
*
*/
package in.geektrust.lengaburu.traffic;

import java.util.Scanner;

/**
 * DESCRIPTION - This class is responsible to test Lengaburu Traffic problem.
 * 
 * This class will take input from the user, and will give the output accordingly.
 * Inputs: 	Weather, Source, Destination;
 * Output: 	Shortest possible time to reach to destination from the source;
 * 		    Output contains vehicle and orbit/route details along with traverse time (Unit: Minutes)   
 * 
 * @author - Ritesh
 * @version 1.0
 * @since <22-August-2017>
 */
public class RunLengaburuTrafficFirstProblem {

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
			System.out.println("Enter source. Options ['Hallitharam', 'Silk Drob', 'RK Puram'] :");
			String source = scanner.nextLine();

			// Input for destination
			System.out.println("Enter destination. Options ['Hallitharam', 'Silk Drob', 'RK Puram'] :");
			String destination = scanner.nextLine();
			
			// Display output
			System.out.println("Expected Output: " + trafficFinder.calculateOptimumTime(weather, source, destination));
			
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