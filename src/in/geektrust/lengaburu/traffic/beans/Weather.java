/*
* Copyright (c) 2017, Ritesh. All rights reserved.
*
*/
package in.geektrust.lengaburu.traffic.beans;

import java.util.List;

/**
 * Weather Bean.
 * 
 * @author - Ritesh Bangal
 * @version 1.0
 * @since <22-August-2017>
 */
public class Weather {
			
	private WeatherType weatherType;

	// It will be either +ve (if increase) or -ve (if reduce) percentage
	private int craterChangeRate;
	private List<String> suitableVehicleNames;

	public Weather() {
		// Default constructor
	}
	
	public Weather(WeatherType weatherType, int craterChangeRate, List<String> sustainableVehicleNames) {
		super();
		this.weatherType = weatherType;
		this.craterChangeRate = craterChangeRate;
		this.suitableVehicleNames = sustainableVehicleNames;
	}

	public WeatherType getWeatherType() {
		return weatherType;
	}

	public void setWeatherType(WeatherType pWeatherType) {
		weatherType = pWeatherType;
	}

	public int getCraterChangeRate() {
		return craterChangeRate;
	}

	public void setCraterChangeRate(int pCraterChangeRate) {
		craterChangeRate = pCraterChangeRate;
	}

	public List<String> getSuitableVehicleNames() {
		return suitableVehicleNames;
	}

	public void setSuitableVehicleNames(List<String> pSustainableVehicleNames) {
		suitableVehicleNames = pSustainableVehicleNames;
	}
	
	@Override
	public String toString() {
		StringBuilder weather = new StringBuilder("Weather");
		weather.append(": {")
			.append("weatherType=").append(weatherType)
			.append(", craterChangeRate=").append(craterChangeRate)
			.append(", suitableVehicleNames=").append(suitableVehicleNames)
			.append("}");
		return weather.toString();
	}
}
