package ua.util;

public interface Reflection {

	public static Object getInstance(Class<?> clazz){
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
