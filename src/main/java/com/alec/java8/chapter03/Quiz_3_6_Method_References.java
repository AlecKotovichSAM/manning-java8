/**
 * 
 */
package com.alec.java8.chapter03;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * @author okoto
 *
 */
public class Quiz_3_6_Method_References {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 1
		@SuppressWarnings("unused")
		ToIntFunction<String> stringToInt = (String s) -> Integer.parseInt(s);
		// 2
		@SuppressWarnings("unused")
		BiPredicate<List<String>, String> contains = (list, element) -> list.contains(element);
		// 3
		Predicate<String> startsWithNumber = (String string) -> Quiz_3_6_Method_References.startsWithNumber(string);

		// Answers:

		// 1
		stringToInt = Integer::parseInt;

		// 2
		contains = List::contains;

		// 3
		startsWithNumber = Quiz_3_6_Method_References::startsWithNumber;

		List<String> stringNumbers = Arrays.asList("1", "2", "3");

		// 1
		stringNumbers.stream() //
				.map(Integer::parseInt) //
				.collect(Collectors.toList()) //
				.stream() //
				.forEach(System.out::println);

		// 2 TODO Page 68 (101 in PDF)
		stringNumbers.stream() //
				.filter(s -> s.equals("2")) //
				.collect(Collectors.toList()) //
				.stream() //
				.forEach(System.out::println);

		// 3
		stringNumbers.stream() //
				.filter(startsWithNumber) //
				.collect(Collectors.toList()) //
				.stream() //
				.forEach(System.out::println);

	}

	private static boolean startsWithNumber(String string) {
		return Character.isDigit(string.charAt(0));
	}

}
