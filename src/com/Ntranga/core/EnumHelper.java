package com.Ntranga.core;

public class EnumHelper {

	public static boolean contains(Class<? extends Enum<?>> clazz, String val) {
		Object[] arr = clazz.getEnumConstants();
		for (Object e : arr) {
			if (((Enum<?>) e).name().equals(val)) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	public static Enum getAsEnum(Class<? extends Enum<?>> clazz, String val){
		Object[] arr = clazz.getEnumConstants();
		for (Object e : arr) {
			if (((Enum<?>) e).name().equals(val)) {
				return (Enum<?>) e;
			}
		}
		return null;
	}
}
