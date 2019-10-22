package fr.umlv.java.inside.lab5;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static fr.umlv.java.inside.lab5.StringSwitchExample.*;

public class StringSwitchExampleTests {

	@ParameterizedTest
	@MethodSource("allFunct")
	public void testStringSwitch(ToIntFunction<String> arg) {
		assertAll(() -> assertEquals(0, arg.applyAsInt("foo")), () -> assertEquals(1, arg.applyAsInt("bar")),
				() -> assertEquals(2, arg.applyAsInt("bazz")), () -> assertEquals(-1, arg.applyAsInt("fooz")));
	}

	private static Stream<ToIntFunction<String>> allFunct() {
		List<ToIntFunction<String>> lst = Arrays.asList(s -> stringSwitch(s), s -> stringSwitch2(s));
		return lst.stream();
	}
}