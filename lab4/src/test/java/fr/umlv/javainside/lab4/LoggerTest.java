package fr.umlv.javainside.lab4;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LoggerTest {

	@Test
	void testLog() {
		A.LOGGER.log("foo");
		assertEquals(A.SB.toString(), "foo");
	}

	@Test
	void testNull() {
		assertAll(() -> assertThrows(NullPointerException.class, () -> Logger.of(null, __ -> {
		})), () -> assertThrows(NullPointerException.class, () -> Logger.of(LoggerTest.class, null)));
	}

	@Test
	void logNull() {
		var logger = A.LOGGER;
		assertThrows(NullPointerException.class, () -> logger.log(null));
	}
}
