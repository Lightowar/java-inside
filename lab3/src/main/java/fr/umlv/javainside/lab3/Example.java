package fr.umlv.javainside.lab3;

@SuppressWarnings("unused")
public class Example {
	private static String aStaticHello(int value) {
		return "question " + value;
	}

	private String anInstanceHello(int value) {
		return "question " + value;
	}
}