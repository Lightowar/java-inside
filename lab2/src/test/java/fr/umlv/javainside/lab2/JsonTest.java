package fr.umlv.javainside.lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Objects;

import org.junit.jupiter.api.Test;

public class JsonTest {

	private static class Alien {
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

	private static class Person {
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

	public static class Error {

		@JSONProperty
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
		var json = toJSON(person);
		assertEquals(json, Json.toJSONOlder(person));
		assertEquals(json, Json.toJSONOld(person));
		assertEquals(json, Json.toJSON(person));
	}

	@Test
	void alien() {
		var alien = new Alien("E.T.", 100);
		assertEquals(toJSON(alien), Json.toJSON(alien));
	}

	@Test
	void shouldGetArithmeticExceptionOnError() {
		var error = new Error();
		assertThrows(ArithmeticException.class, () -> {
			Json.toJSON(error);
		});
	}

}
