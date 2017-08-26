/*
* Copyright (c) 2017, Ritesh. All rights reserved.
*
*/
package in.geektrust.lengaburu.traffic.beans;

/**
 * Velocity Bean.
 * 
 * @author - Ritesh Bangal
 * @version 1.0
 * @since <22-August-2017>
 */
public class Velocity {

	private Integer speed; // Default unit is megamiles/hour.
	private String  unit;

	public Velocity() {
		// Default constructor
	}
	
	public Velocity(Integer speed, String unit) {
		super();
		this.speed = speed;
		this.unit = unit;
	}
	
	public Integer getSpeed() {
		return this.speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Override
	public String toString() {
		StringBuilder velocity = new StringBuilder("Velocity");
		velocity.append(": [")
			.append("speed=").append(speed)
			.append(", unit=").append(unit)
			.append("]");
		return velocity.toString();
	}
}
