package fr.umlv.javainside.lab2;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {

	public static String toJSON(Person person) {
		return "{\n" + "  \"firstName\": \"" + person.getFirstName() + "\"\n" + "  \"lastName\": \""
				+ person.getLastName() + "\"\n" + "}\n";
	}

	public static String toJSON(Alien alien) {
		return "{\n" + "  \"planet\": \"" + alien.getPlanet() + "\"\n" + "  \"members\": \"" + alien.getAge() + "\"\n"
				+ "}\n";
	}

	private static String propertyName(String name) {
		return Character.toLowerCase(name.charAt(3)) + name.substring(4);
	}

	public static String toJSON(Object object) {
		return Arrays.stream(object.getClass().getMethods()).map(m -> m.getName()).filter(s -> s.startsWith("get"))
				.map(s -> propertyName(s)).collect(Collectors.joining("; "));
	}

	public static void main(String[] args) {
		var person = new Person("John", "Doe");
		System.out.println(toJSON(person));
		var alien = new Alien("E.T.", 100);
		System.out.println(toJSON(alien));
		
		System.out.println(toJSON((Object)alien));
	}
}