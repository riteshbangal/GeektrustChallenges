/*
* Copyright (c) 2017, Ritesh. All rights reserved.
*
*/
package in.geektrust.lengaburu.traffic.beans;

import java.util.Arrays;

import in.geektrust.lengaburu.traffic.exception.BusinessException;

/**
 * WeatherType Enum.
 * 
 * @author - Ritesh Bangal
 * @version 1.0
 * @since <22-August-2017>
 */
public enum WeatherType {

	/**
	 * The singleton instance for weather type: Sunny.
	 * This has the numeric value of {@code 0}.
	 */
	SUNNY("Sunny"),
	
	/**
     * The singleton instance for weather type: Rainy.
     * This has the numeric value of {@code 1}.
     */
	RAINY("Rainy"),
	
    /**
     * The singleton instance for weather type: Windy.
     * This has the numeric value of {@code 2}.
     */
	WINDY("Windy");

	private static final WeatherType[] ENUMS = WeatherType.values();

	private WeatherType() {
		// Restrict instantiation
	}

	 /**
     * Obtains an instance of {@code WeatherType} from an {@code int} value.
     *
     * @param weatherType  the weather type to represent, from 0 (Sunday) to 6 (Saturday)
     * @return the day-of-week singleton, not null
     * @throws BusinessException, if the day-of-week is invalid
     */
	public static WeatherType of(int weatherType) throws BusinessException {
		if (weatherType < 0) {
			throw new BusinessException("Invalid value for WeatherType: " + weatherType);
		}
		return ENUMS[weatherType];
	}
	
	public static boolean contains(String pWeatherType) {
		return Arrays.stream(ENUMS	)
				.anyMatch(weatherType -> weatherType.toString().equalsIgnoreCase(pWeatherType));
	}
	
	private String type;

    private WeatherType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
