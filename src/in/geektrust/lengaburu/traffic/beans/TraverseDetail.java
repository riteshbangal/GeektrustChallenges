/*
* Copyright (c) 2017, Ritesh. All rights reserved.
*
*/
package in.geektrust.lengaburu.traffic.beans;

import java.io.Serializable;

/**
 * Orbit Bean.
 * 
 * @author - Ritesh Bangal
 * @version 1.0
 * @since <22-August-2017>
 */
public class TraverseDetail implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	private int traverseTime; // Default unit is minutes.
	private Orbit orbit;
	private Vehicle vehicle;
	
	public TraverseDetail() {
		// Default constructor
	}
	
	public TraverseDetail(int traverseTime, Orbit orbit, Vehicle vehicle) {
		super();
		this.traverseTime = traverseTime; // Default unit is minutes.
		this.orbit = orbit;
		this.vehicle = vehicle; 
	}
	
	public int getTraverseTime() {
		return traverseTime;
	}

	public void setTraverseTime(int pTraverseTime) {
		traverseTime = pTraverseTime;
	}

	public Orbit getOrbit() {
		return orbit;
	}

	public void setOrbit(Orbit pOrbit) {
		orbit = pOrbit;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle pVehicle) {
		vehicle = pVehicle;
	}

	@Override
	public String toString() {
		StringBuilder traverseDetail = new StringBuilder("TraverseDetail");
		traverseDetail.append(": [")
			.append("traverseTime=").append(traverseTime)
			.append(", orbit=").append(orbit)
			.append(", vehicle=").append(vehicle)
			.append("]");
		return traverseDetail.toString();
	}
}
