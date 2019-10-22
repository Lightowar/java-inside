package fr.umlv.java.inside.lab5;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import static fr.umlv.java.inside.lab5.StringSwitchExample.*;

public class StringSwitchExampleTests {

	@Test
	public void testStringSwitch() {
		assertAll(
				()->assertEquals(0, stringSwitch("foo")),
				()->assertEquals(1, stringSwitch("bar")),
				()->assertEquals(2, stringSwitch("bazz")),
				()->assertEquals(-1, stringSwitch("fooz"))
		);
	}
}