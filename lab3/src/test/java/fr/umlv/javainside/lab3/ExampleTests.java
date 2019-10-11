package fr.umlv.javainside.lab3;

import static java.lang.invoke.MethodHandles.dropArguments;
import static java.lang.invoke.MethodHandles.guardWithTest;
import static java.lang.invoke.MethodHandles.insertArguments;
import static java.lang.invoke.MethodHandles.lookup;
import static java.lang.invoke.MethodHandles.publicLookup;
import static java.lang.invoke.MethodHandles.privateLookupIn;
import static java.lang.invoke.MethodType.methodType;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;

public class ExampleTests {

	@Test
	void reflectionStatic() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		var method = Example.class.getDeclaredMethod("aStaticHello", int.class);
		method.setAccessible(true);
		assertEquals((String) method.invoke(null, 3), "question 3");
	}

	@Test
	void reflectionInstance() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		var method = Example.class.getDeclaredMethod("anInstanceHello", int.class);
		method.setAccessible(true);
		assertEquals((String) method.invoke(new Example(), 3), "question 3");
	}

	@Test
	void invocationStatic() throws Throwable {
		assertEquals(
				(String) privateLookupIn(Example.class, lookup())
						.findStatic(Example.class, "aStaticHello", methodType(String.class, int.class)).invokeExact(4),
				"question 4");
	}

	@Test
	void invocationInstance() throws Throwable {
		assertEquals((String) privateLookupIn(Example.class, lookup())
				.findVirtual(Example.class, "anInstanceHello", methodType(String.class, int.class))
				.invokeExact(new Example(), 5), "question 5");
	}

	@Test
	void invocationInseredInstance() throws Throwable {
		assertEquals(
				(String) insertArguments(privateLookupIn(Example.class, lookup()).findVirtual(Example.class,
						"anInstanceHello", methodType(String.class, int.class)), 1, 6).invokeExact(new Example()),
				"question 6");
	}

	@Test
	void invocationDroppedInstance() throws Throwable {
		assertEquals((String) dropArguments(privateLookupIn(Example.class, lookup()).findVirtual(Example.class,
				"anInstanceHello", methodType(String.class, int.class)), 1, String.class).invokeExact(new Example(),
						"hello", 7),
				"question 7");
	}

	@Test
	void invocationInstanceAsTyped() throws Throwable {
		assertEquals((String) privateLookupIn(Example.class, lookup())
				.findVirtual(Example.class, "anInstanceHello", methodType(String.class, int.class))
				.asType(methodType(String.class, Example.class, Integer.class))
				.invokeExact(new Example(), Integer.valueOf(8)), "question 8");
	}

	@Test
	void invocationInstanceConstant() throws Throwable {
		assertEquals((String) MethodHandles.constant(String.class, "question 9").invoke(), "question 9");
	}

	@Test
	void testGuardWith() throws Throwable {

		var m0 = insertArguments(
				publicLookup().findVirtual(String.class, "equals", methodType(boolean.class, Object.class)), 1, "foo");
		var m1 = dropArguments(MethodHandles.constant(int.class, 1).asType(methodType(int.class, Object.class)), 2);
		var m2 = dropArguments(MethodHandles.constant(int.class, -1).asType(methodType(int.class, Object.class)), 2);

		System.out.println(guardWithTest(m0, m1, m2).invokeExact("foo"));

		/*
		 * assertEquals( (int) guardWithTest(
		 * insertArguments(publicLookup().findVirtual(String.class, "equals",
		 * methodType(boolean.class, Object.class)), 1, "foo"),
		 * MethodHandles.constant(int.class, -1), MethodHandles.constant(int.class,
		 * 1)).invokeExact("foo"), 1);
		 * 
		 * assertEquals( (int) guardWithTest(
		 * insertArguments(publicLookup().findVirtual(String.class, "equals",
		 * methodType(boolean.class, Object.class)), 1, "foo"),
		 * MethodHandles.constant(int.class, -1), MethodHandles.constant(int.class, -1))
		 * .invokeExact("bar"), -1);
		 */
	}
}
