/*
* Copyright (c) 2017, Ritesh. All rights reserved.
*
*/
package in.geektrust.lengaburu.traffic.beans;

import java.io.Serializable;
import java.util.List;

/**
 * TraverseDetail Bean. 
 * 
 * Here list of orbits that contains sequence of routes/orbits between source and final destinations, 
 * to visit via other destinations/suburbs.
 * 
 * Note: When there is single destination, each orbit-sequence contains only one orbit, i.e. orbit between any source and destination.
 * 
 * @author - Ritesh Bangal
 * @version 1.0
 * @since <26-August-2017>
 */
public class TraverseDetail implements Serializable {
	
	private static final long serialVersionUID = 1L;

	// Contains traverse time for all the orbits. Default unit is minutes.
	private int traverseTime; 
	
	// Contains sequence of routes/orbits between source and final destinations, to visit via other suburbs
	private List<Orbit> orbits; 
	private Vehicle vehicle;
	
	public TraverseDetail() {
		// Default constructor
	}
	
	public TraverseDetail(int traverseTime, List<Orbit> orbits, Vehicle vehicle) {
		super();
		this.traverseTime = traverseTime; // Default unit is minutes.
		this.orbits = orbits;
		this.vehicle = vehicle; 
	}
	
	public int getTraverseTime() {
		return traverseTime;
	}

	public void setTraverseTime(int pTraverseTime) {
		traverseTime = pTraverseTime;
	}

	public List<Orbit> getOrbits() {
		return orbits;
	}

	public void setOrbit(List<Orbit> pOrbits) {
		orbits = pOrbits;
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
		traverseDetail.append(": {")
			.append("traverseTime=").append(traverseTime)
			.append(", orbits=").append(orbits)
			.append(", vehicle=").append(vehicle)
			.append("}");
		return traverseDetail.toString();
	}
}
