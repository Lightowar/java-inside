package fr.umlv.java.inside.lab5;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.UndeclaredThrowableException;

import static java.lang.invoke.MethodHandles.*;
import static java.lang.invoke.MethodType.methodType;

public class StringSwitchExample {

	private static final MethodHandle MH = createMHFromStrings2("foo", "bar", "bazz");

	public static int stringSwitch(String s) {
		switch (s) {
		case "foo":
			return 0;
		case "bar":
			return 1;
		case "bazz":
			return 2;
		default:
			return -1;
		}
	}

	public static int stringSwitch2(String s) {
		try {
			return (int) MH.invokeExact(s);
		} catch (RuntimeException | Error e) {
			throw e;
		} catch (Throwable e) {
			throw new UndeclaredThrowableException(e);
		}

	}

	public static MethodHandle createMHFromStrings2(String... matches) {
		try {
			var equ = publicLookup().findVirtual(String.class, "equals", methodType(boolean.class, Object.class));
			var mh = dropArguments(constant(int.class, -1), 0, String.class);
			for (var i = 0; i < matches.length; i++) {
				mh = guardWithTest(insertArguments(equ, 1, matches[i]),
						dropArguments(constant(int.class, i), 0, String.class), mh);
			}
			return mh;
		} catch (NoSuchMethodException | IllegalAccessException e) {
			throw new AssertionError(e);

		}
	}
}
