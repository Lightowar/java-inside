package fr.umlv.javainside.lab2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Json {

	private static String propertyName(String name) {
		return Character.toLowerCase(name.charAt(3)) + name.substring(4);
	}

	public static String toJSON(Object object) {
		return Arrays.stream(object.getClass().getMethods()).filter(m -> m.getDeclaringClass() != Object.class)
				.filter(m -> m.getName().startsWith("get")).sorted(Comparator.comparing(Method::getName))
				.map(m -> getterToString(m, object)).collect(Collectors.joining("\n", "{\n", "\n}\n"));
	}

	private static String getterToString(Method method, Object object) {
		try {
			return "  \"" + propertyName(method.getName()) + "\": \"" + method.invoke(object) + "\"";
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		} catch (InvocationTargetException e) {
			var cause = e.getCause();
			if (cause instanceof RuntimeException) {
				throw (RuntimeException) cause;
			}
			if (cause instanceof Error) {
				throw (Error) cause;
			}
			throw new UndeclaredThrowableException(e);
		}
	}
}
