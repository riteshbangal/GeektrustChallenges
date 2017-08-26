/*
* Copyright (c) 2017, Ritesh. All rights reserved.
*
*/
package in.geektrust.lengaburu.traffic.beans;

import java.io.Serializable;

/**
 * Orbit Bean, which contains route/orbit related details.
 * 
 * @author - Ritesh Bangal
 * @version 1.0
 * @since <22-August-2017>
 */
public class Orbit implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String source;
	private String destination;

	// Default unit is megamiles.
	private Integer distance;
	private Integer numberOfCraters;
	
	private Velocity velocityLimit;
	
	public Orbit() {
		// Default constructor
	}
	
	public Orbit(String source, String destination, Integer distance, Integer numberOfCraters, Velocity velocityLimit) {
		super();
		this.source = source;
		this.destination = destination;
		this.distance = distance; // Default unit is megamile.
		this.numberOfCraters = numberOfCraters;
		this.velocityLimit = velocityLimit;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String pSource) {
		source = pSource;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String pDestination) {
		destination = pDestination;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer pDistance) {
		distance = pDistance;
	}

	public Integer getNumberOfCraters() {
		return numberOfCraters;
	}

	public void setNumberOfCraters(Integer pNumberOfCraters) {
		numberOfCraters = pNumberOfCraters;
	}

	public Velocity getVelocityLimit() {
		return velocityLimit;
	}

	public void setVelocityLimit(Velocity pVelocityLimit) {
		velocityLimit = pVelocityLimit;
	}
	
	@Override
	public String toString() {
		StringBuilder orbit = new StringBuilder("Orbit");
		orbit.append(": [")
			.append("source=").append(source)
			.append(", destination=").append(destination)
			.append(", distance=").append(distance)
			.append(", numberOfCraters=").append(numberOfCraters)
			.append(", velocityLimit=").append(velocityLimit)
			.append("]");
		return orbit.toString();
	}
}
