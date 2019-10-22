package fr.umlv.java.inside.lab5;

public class StringSwitchExample {

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
}
