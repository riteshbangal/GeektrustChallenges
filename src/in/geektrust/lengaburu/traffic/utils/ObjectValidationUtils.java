/*
* Copyright (c) 2017, Ritesh. All rights reserved.
*
*/
package in.geektrust.lengaburu.traffic.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * DESCRIPTION - This class is a utility class for this traffic app.
 * 
 * @author - Ritesh
 * @version 1.0
 * @since <22-August-2017>
 */
public class ObjectValidationUtils {
	
	public static final String SPACE	= " ";
	public static final String EMPTY 	= "";
	
	public static final int INDEX_NOT_FOUND = -1;

	// Restrict instantiation
	private ObjectValidationUtils() {
		super();
	}
	
	public static boolean isEmpty(Object pObject) {
		return pObject == null;
	}

	public static boolean isNotEmpty(Object pObject) {
		return pObject != null;
	}

	public static boolean isEmpty(String pStr) {
		return pStr == null || pStr.length() == 0;
	}

	public static boolean isNotEmpty(String pStr) {
		return pStr != null && pStr.length() > 0;
	}

	public static boolean isBlank(String pStr) {
		return (pStr == null) || (pStr.length() == 0) || (pStr.trim().length() == 0);
	}

	public static boolean isNotBlank(String pStr) {
		return pStr != null && pStr.length() > 0 && pStr.trim().length() > 0;
	}
	
	public static <E> boolean isEmpty(Collection<E> pCollectionObj) {
		return pCollectionObj == null || pCollectionObj.isEmpty();
	}

	public static <E> boolean isNotEmpty(Collection<E> pCollectionObj) {
		return pCollectionObj != null && !pCollectionObj.isEmpty();
	}

	/**
	 * Convert array inputs into a list, where inputs are array of objects
	 * 
	 * @param pInputs - User inputs as array of objects
	 * @return - Converted input object list
	 */
	public static List<Object> getList(Object... pInputs) {
		return Arrays.asList(pInputs);
	}
	
	/**
	 * Convert array inputs into a list, where inputs are array of string
	 * 
	 * @param pInputs - User inputs as array of string
	 * @return - Converted input list
	 */
	public static List<String> getList(String... pInputs) {
		return Arrays.asList(pInputs);
	}
}
