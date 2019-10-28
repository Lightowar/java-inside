package fr.umlv.java.inside.lab5;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

import static java.lang.invoke.MethodHandles.*;
import static java.lang.invoke.MethodType.methodType;

public class StringSwitchExample {

	private static final MethodHandle MH2, MH3;

	private static final MethodHandle STRING_EQUAL;

	static {
		try {
			STRING_EQUAL = publicLookup().findVirtual(String.class, "equals", methodType(boolean.class, Object.class));
		} catch (NoSuchMethodException | IllegalAccessException e) {
			throw new AssertionError();
		}
		MH2 = createMHFromStrings2("foo", "bar", "bazz");
		MH3 = createMHFromStrings3("foo", "bar", "bazz");
	}

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
			return (int) MH2.invokeExact(s);
		} catch (RuntimeException | Error e) {
			throw e;
		} catch (Throwable e) {
			throw new UndeclaredThrowableException(e);
		}

	}

	public static MethodHandle createMHFromStrings2(String... matches) {
		var mh = dropArguments(constant(int.class, -1), 0, String.class);
		for (var i = 0; i < matches.length; i++) {
			mh = guardWithTest(insertArguments(STRING_EQUAL, 1, matches[i]),
					dropArguments(constant(int.class, i), 0, String.class), mh);
		}
		return mh;
	}

	public static int stringSwitch3(String s) {
		try {
			return (int) MH3.invokeExact(s);
		} catch (RuntimeException | Error e) {
			throw e;
		} catch (Throwable e) {
			throw new UndeclaredThrowableException(e);
		}
	}

	public static MethodHandle createMHFromStrings3(String... matches) {
		return new InliningCache(matches).dynamicInvoker();
	}

	static class InliningCache extends MutableCallSite {
		private static final MethodHandle SLOW_PATH;

		static {
			try {
				SLOW_PATH = lookup().findVirtual(InliningCache.class, "slowPath", methodType(int.class, String.class));
			} catch (NoSuchMethodException | IllegalAccessException e) {
				throw new AssertionError(e);
			}
		}

		private final List<String> matches;

		public InliningCache(String... matches) {
			super(MethodType.methodType(int.class, String.class));
			this.matches = List.of(matches);
			setTarget(insertArguments(SLOW_PATH, 0, this));
		}

		@SuppressWarnings("unused")
		private int slowPath(String value) {
			var ret = matches.indexOf(value);
			setTarget(guardWithTest(insertArguments(STRING_EQUAL, 1, value),
					dropArguments(constant(int.class, ret), 0, String.class), getTarget()));
			return ret;
		}
	}
}
