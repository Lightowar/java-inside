package fr.umlv.javainside.lab4;

public class A {

	public final static StringBuilder SB = new StringBuilder();
	public final static Logger LOGGER = Logger.of(A.class, s -> SB.append(s));
}
