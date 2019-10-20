package fr.umlv.javainside.lab4;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LoggerTest {

	@Test
	void testLog() {
		A.reset();
		var logger = Logger.of(A.class, A.SB::append);
		logger.log("foo");
		assertEquals(A.SB.toString(), "foo");
	}

	@Test
	void testNull() {
		assertAll(() -> assertThrows(NullPointerException.class, () -> Logger.of(null, __ -> {
		})), () -> assertThrows(NullPointerException.class, () -> Logger.of(LoggerTest.class, null)));
	}

	@Test
	void logNull() {
		var logger = Logger.of(A.class, A.SB::append);
		assertThrows(NullPointerException.class, () -> logger.log(null));
	}

	@Test
	void testFastLog() {
		A.reset();
		var logger = Logger.of(A.class, A.SB::append);
		logger.log("foo");
		assertEquals(A.SB.toString(), "foo");
	}

	@Test
	void testFastNull() {
		assertAll(() -> assertThrows(NullPointerException.class, () -> Logger.of(null, __ -> {
		})), () -> assertThrows(NullPointerException.class, () -> Logger.of(LoggerTest.class, null)));
	}

	@Test
	void fastLogNull() {
		var logger = Logger.of(A.class, A.SB::append);
		assertThrows(NullPointerException.class, () -> logger.log(null));
	}

	@Test
	void testDisabled() {
		A.reset();
		var logger = Logger.of(A.class, A.SB::append);
		logger.log("bar");
		Logger.enable(A.class, false);
		logger.log("foo");
		Logger.enable(A.class, true);
		assertEquals(A.SB.toString(), "bar");
	}
}
