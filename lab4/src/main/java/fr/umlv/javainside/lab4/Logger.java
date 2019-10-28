package fr.umlv.javainside.lab4;

import static java.lang.invoke.MethodHandles.publicLookup;
import static java.lang.invoke.MethodHandles.insertArguments;
import static java.lang.invoke.MethodHandles.guardWithTest;
import static java.lang.invoke.MethodType.methodType;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MutableCallSite;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Objects;
import java.util.function.Consumer;

public interface Logger {
	public void log(String message);

	public static Logger of(Class<?> declaringClass, Consumer<? super String> consumer) {
		Objects.requireNonNull(declaringClass);
		Objects.requireNonNull(consumer);
		var mh = createLoggingMethodHandle(declaringClass, consumer);
		return new Logger() {
			@Override
			public void log(String message) {
				Objects.requireNonNull(message);
				try {
					mh.invokeExact(message);
				} catch (Throwable t) {
					if (t instanceof RuntimeException) {
						throw (RuntimeException) t;
					}
					if (t instanceof Error) {
						throw (Error) t;
					}
					throw new UndeclaredThrowableException(t);
				}
			}
		};
	}

	public static Logger fastOf(Class<?> declaringClass, Consumer<? super String> consumer) {
		Objects.requireNonNull(declaringClass);
		Objects.requireNonNull(consumer);
		var mh = createLoggingMethodHandle(declaringClass, consumer);
		return message -> {
			Objects.requireNonNull(message);
			try {
				mh.invokeExact(message);
			} catch (Throwable t) {
				if (t instanceof RuntimeException) {
					throw (RuntimeException) t;
				}
				if (t instanceof Error) {
					throw (Error) t;
				}
				throw new UndeclaredThrowableException(t);
			}
		};
	}

	private static MethodHandle createLoggingMethodHandle(Class<?> declaringClass, Consumer<? super String> consumer) {
		MethodHandle mh;
		try {
			mh = publicLookup().findVirtual(Consumer.class, "accept", methodType(void.class, Object.class));
		} catch (NoSuchMethodException | IllegalAccessException e) {
			throw new AssertionError(e);
		}
		return guardWithTest(ENABLE_CALLSITES.get(declaringClass).dynamicInvoker(),
				insertArguments(mh, 0, consumer).asType(methodType(void.class, String.class)),
				MethodHandles.empty(methodType(void.class, String.class)));
	}

	static final ClassValue<MutableCallSite> ENABLE_CALLSITES = new ClassValue<MutableCallSite>() {
		protected MutableCallSite computeValue(Class<?> type) {
			return new MutableCallSite(MethodHandles.constant(boolean.class, true));
		}
	};

	public static void enable(Class<?> declaringClass, boolean enable) {
		var callSite = ENABLE_CALLSITES.get(declaringClass);
		callSite.setTarget(MethodHandles.constant(boolean.class, enable));
		MutableCallSite.syncAll(new MutableCallSite[] { callSite });
	}
}