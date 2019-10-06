package fr.umlv.javainside.lab2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Json {

	private static ClassValue<Method[]> classvalue = new ClassValue<Method[]>() {

		@Override
		protected Method[] computeValue(Class<?> type) {
			return type.getMethods();
		}
	};

	private static ClassValue<Function<Object, String>> classvalue2 = new ClassValue<Function<Object, String>>() {

		@Override
		protected Function<Object, String> computeValue(Class<?> type) {
			var methods = FiltreAndSorteMethodStream(Arrays.stream(type.getMethods())).collect(Collectors.toList());
			return object -> methods.stream().map(m -> getterToString(m, object))
					.collect(Collectors.joining("\n", "{\n", "\n}\n"));
		}
	};

	private static Stream<Method> FiltreAndSorteMethodStream(Stream<Method> methodsStream) {
		return methodsStream.filter(m -> m.getName().startsWith("get") && m.isAnnotationPresent(JSONProperty.class))
				.sorted(Comparator.comparing(Method::getName));
	}

	public static String toJSONOlder(Object object) {
		return FiltreAndSorteMethodStream(Arrays.stream(object.getClass().getMethods()))
				.map(m -> getterToString(m, object)).collect(Collectors.joining("\n", "{\n", "\n}\n"));
	}

	public static String toJSONOld(Object object) {
		return FiltreAndSorteMethodStream(Arrays.stream(classvalue.get(object.getClass())))
				.map(m -> getterToString(m, object)).collect(Collectors.joining("\n", "{\n", "\n}\n"));
	}

	public static String toJSON(Object object) {
		return classvalue2.get(object.getClass()).apply(object);
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
