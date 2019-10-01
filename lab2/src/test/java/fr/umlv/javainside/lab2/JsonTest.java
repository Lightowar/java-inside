package fr.umlv.javainside.lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Objects;

import org.junit.jupiter.api.Test;

public class JsonTest {

	public static class Alien {
		private final String planet;
		private final int age;

		public Alien(String planet, int age) {
			if (age <= 0) {
				throw new IllegalArgumentException("Too young...");
			}
			this.planet = Objects.requireNonNull(planet);
			this.age = age;
		}

		@JSONProperty
		public String getPlanet() {
			return planet;
		}

		@JSONProperty
		public int getAge() {
			return age;
		}
	}

	public static class Person {
		private final String firstName;
		private final String lastName;

		public Person(String firstName, String lastName) {
			this.firstName = Objects.requireNonNull(firstName);
			this.lastName = Objects.requireNonNull(lastName);
		}

		@JSONProperty
		public String getFirstName() {
			return firstName;
		}

		@JSONProperty
		public String getLastName() {
			return lastName;
		}
	}

	private static class Error1 {

		public int getNumber() {
			return 0;
		}
	}

	public static class Error2 {

		public int getNumber() {
			return 1 / 0;
		}
	}

	public static String toJSON(Person person) {
		return "{\n" + "  \"firstName\": \"" + person.getFirstName() + "\"\n" + "  \"lastName\": \""
				+ person.getLastName() + "\"\n" + "}\n";
	}

	public static String toJSON(Alien alien) {
		return "{\n" + "  \"age\": \"" + alien.getAge() + "\"\n" + "  \"planet\": \"" + alien.getPlanet() + "\"\n"
				+ "}\n";
	}

	@Test
	void person() {
		var person = new Person("John", "Doe");
		assertEquals(toJSON(person), Json.toJSON(person));
	}

	@Test
	void alien() {
		var alien = new Alien("E.T.", 100);
		assertEquals(toJSON(alien), Json.toJSON(alien));
	}

	/*
	 * @Test void shouldGetIllegalStateExceptionOnPrivateClass() { var error = new
	 * Error1(); assertThrows(IllegalStateException.class, () ->
	 * Json.toJSON(error)); }
	 * 
	 * @Test void shouldGetIllegalStateExceptionOnTODO() { var error = new Error2();
	 * assertThrows(Exception.class, () -> { Json.toJSON(error); }); }
	 */
}
