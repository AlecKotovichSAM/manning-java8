/**
 * 
 */
package com.alec.java8.chapter03;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.Supplier;

/**
 * @author okoto
 *
 */
public class Quiz_3_4_Functions {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 1) T -> R
		Function<String, Boolean> blankFunc = (String s) -> s.isBlank();

		Boolean blank = isBlank("", blankFunc);
		System.out.println("Is blank? " + blank);

		// 2 (int, int) -> int
		IntBinaryOperator sumOfNumbers = (int i, int j) -> i + j;
		System.out.println("Sum of 2 and 3 equals " + sumOfNumbers.applyAsInt(2, 3));

		// 3 T -> void
		Consumer<String> printer = (s) -> System.out.println(s);
		printer.accept("Hello, Printer!");

		// 4 () -> T
		Supplier<String> getRandomString = () -> Long.valueOf(System.currentTimeMillis()).toString();
		printer.accept(getRandomString.get());

		// 5 (T, U) -> R
		BiFunction<String, Long, Integer> getInteger = (s, l) -> Integer.valueOf(s) + l.intValue();
		printer.accept("" + getInteger.apply("122", 1L));
	}

	private static Boolean isBlank(String string, Function<String, Boolean> blankFunc) {
		return blankFunc.apply(string);

	}

}
