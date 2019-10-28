package fr.umlv.java.inside.lab5;

import static fr.umlv.java.inside.lab5.StringSwitchExample.stringSwitch;
import static fr.umlv.java.inside.lab5.StringSwitchExample.stringSwitch2;
import static fr.umlv.java.inside.lab5.StringSwitchExample.stringSwitch3;

import java.util.concurrent.TimeUnit;
import java.util.function.ToIntFunction;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)

public class StringSwitchBenchMark {

	private void test(ToIntFunction<String> f) {
		for (var i = 0; i < 166_666; i++) {
			f.applyAsInt("foo");
			f.applyAsInt("bar");
			f.applyAsInt("bazz");
			f.applyAsInt("boo");
			f.applyAsInt("far");
			f.applyAsInt("fazz");
		}
	}

	@Benchmark
	public void v1() {
		ToIntFunction<String> mh = s -> stringSwitch(s);
		test(mh);
	}

	@Benchmark
	public void v2() {
		ToIntFunction<String> mh = s -> stringSwitch2(s);
		test(mh);
	}

	@Benchmark
	public void v3() {
		ToIntFunction<String> mh = s -> stringSwitch3(s);
		test(mh);
	}
}
