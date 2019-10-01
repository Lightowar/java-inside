package fr.umlv.javainside.lab2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Json {

	private static ClassValue<Method[]> classvalue = new ClassValue<Method[]>() {

		@Override
		protected Method[] computeValue(Class<?> type) {
			return type.getMethods();
		}
	};

	public static String toJSON(Object object) {
		return Arrays.stream(classvalue.get(object.getClass())).filter(m -> m.getName().startsWith("get"))
				.filter(m -> m.isAnnotationPresent(JSONProperty.class)).sorted(Comparator.comparing(Method::getName))
				.map(m -> getterToString(m, object)).collect(Collectors.joining("\n", "{\n", "\n}\n"));
	}

	private static String propertyName(String name) {
		return Character.toLowerCase(name.charAt(3)) + name.substring(4);
	}

	private static String getAttributName(Method method) {
		var ret = method.getAnnotation(JSONProperty.class).value();
		if (!ret.equals(""))
			return ret;
		return propertyName(method.getName());
	}

	private static String getterToString(Method method, Object object) {
		try {
			return "  \"" + getAttributName(method) + "\": \"" + method.invoke(object) + "\"";
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