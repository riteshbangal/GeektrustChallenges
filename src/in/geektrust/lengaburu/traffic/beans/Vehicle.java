/*
* Copyright (c) 2017, Ritesh. All rights reserved.
*
*/
package in.geektrust.lengaburu.traffic.beans;

/**
 * Vehicle Bean.
 * 
 * @author - Ritesh Bangal
 * @version 1.0
 * @since <22-August-2017>
 */
public class Vehicle {

	private String name;
	
	private Velocity velocity;
	
	/**
	 * Time to cross a cracker (default unit is in minutes)
	 */
	private Integer timeToCrossCrater;

	public Vehicle() {
		// Default constructor
	}
	
	public Vehicle(String name, Velocity velocity, Integer timeToCrossCrater) {
		super();
		this.name = name;
		this.velocity = velocity;
		this.timeToCrossCrater = timeToCrossCrater;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Velocity getVelocity() {
		return this.velocity;
	}

	public void setVelocity(Velocity velocity) {
		this.velocity = velocity;
	}

	public Integer getTimeToCrossCrater() {
		return this.timeToCrossCrater;
	}

	public void setTimeToCrossCrater(Integer timeToCrossCrater) {
		this.timeToCrossCrater = timeToCrossCrater;
	}
	
	@Override
	public String toString() {
		StringBuilder vehicle = new StringBuilder("Vehicle");
		vehicle.append(": [")
			.append("name=").append(name)
			.append(", velocity=").append(velocity)
			.append(", timeToCrossCrater=").append(timeToCrossCrater)
			.append("]");
		return vehicle.toString();
	}
}
