package fr.umlv.javainside.lab2;

import java.util.Objects;

public class Main {

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

	public static void main(String[] args) {
		var person = new Person("John", "Doe");
		var start = System.currentTimeMillis();
		var n = 1;
		for (int i = 0; i < n; i++)
			Json.toJSONOlder(person);
		System.out.println(System.currentTimeMillis() - start);
		start = System.currentTimeMillis();
		for (int i = 0; i < n; i++)
			Json.toJSONOld(person);
		System.out.println(System.currentTimeMillis() - start);
		start = System.currentTimeMillis();
		for (int i = 0; i < n; i++)
			Json.toJSON(person);
		System.out.println(System.currentTimeMillis() - start);

	}
}