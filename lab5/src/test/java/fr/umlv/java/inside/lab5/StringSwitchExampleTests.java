package fr.umlv.java.inside.lab5;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StringSwitchExampleTests {

	@Test
	void testStringSwitch() {
		assertAll(
				()->assertEquals(stringSwitch("foo"), 0),
				()->assertEquals(stringSwitch("bar"), 1),
				()->assertEquals(stringSwitch("bazz"), 2),
				()->assertEquals(stringSwitch("fooz"), -1)
		);
	}
}