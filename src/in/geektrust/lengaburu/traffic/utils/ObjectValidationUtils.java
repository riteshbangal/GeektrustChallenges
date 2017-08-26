package in.geektrust.lengaburu.traffic.utils;

import java.util.Collection;

public class ObjectValidationUtils {
	
	public static final String SPACE	= " ";
	public static final String EMPTY 	= "";
	
	public static final int INDEX_NOT_FOUND = -1;

	// Restrict instantiation
	private ObjectValidationUtils() {
		super();
	}
	
	public static boolean isEmpty(Object pObject) {
		return (pObject == null);
	}

	public static boolean isNotEmpty(Object pObject) {
		return (pObject != null);
	}

	public static boolean isEmpty(String pStr) {
		return (pStr == null || pStr.length() == 0);
	}

	public static boolean isNotEmpty(String pStr) {
		return (pStr != null && pStr.length() > 0);
	}

	public static boolean isBlank(String pStr) {
		return ((pStr == null) || (pStr.length() == 0) || (pStr.trim().length() == 0));
	}

	public static boolean isNotBlank(String pStr) {
		return (pStr != null && pStr.length() > 0 && pStr.trim().length() > 0);
	}
	
	public static <E> boolean isEmpty(Collection<E> pCollectionObj) {
		return (pCollectionObj == null || pCollectionObj.isEmpty());
	}

	public static <E> boolean isNotEmpty(Collection<E> pCollectionObj) {
		return (pCollectionObj != null && pCollectionObj.isEmpty());
	}
}