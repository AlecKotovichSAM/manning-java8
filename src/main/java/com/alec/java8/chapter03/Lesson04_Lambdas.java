/**
 * 
 */
package com.alec.java8.chapter03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import com.alec.java8.chapter01.Lesson_01_FunctionsIntro;
import com.alec.java8.chapter01.Lesson_01_FunctionsIntro.Apple;
import com.alec.java8.chapter01.Lesson_01_FunctionsIntro.AppleInventory;
import com.alec.java8.chapter01.Lesson_01_FunctionsIntro.Color;

/**
 * @author okoto
 *
 */
public class Lesson04_Lambdas {

	/**
	 * @param  args
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {

		List<Apple> apples = AppleInventory.getApples();

		// Before:
		Comparator<Apple> byWeightComparator = new Comparator<Apple>() {
			public int compare(Apple a1, Apple a2) {
				return a2.getWeight() - a1.getWeight();
			}
		};

		// After (with lambda expressions):
		Comparator<Apple> byWeightLambdaComparator = (Apple a1, Apple a2) -> a1.getWeight() - a2.getWeight();

		apples.sort(byWeightComparator);
		apples.forEach(System.out::println);

		apples.sort(byWeightLambdaComparator);
		apples.forEach(System.out::println);

		//////////////// Use case Examples of lambdas ////////////////////////////////
		// A boolean expression
		Function<List<String>, Boolean> f = (List<String> list) -> list.isEmpty();
		// Creating objects
		Supplier<Apple> f2 = () -> new Apple(Color.GREEN, 10);
		// Consuming from an object
		Consumer<Apple> f3 = (Apple a) -> {
			System.out.println(a.getWeight());
		};
		// Select/extract from an object
		Function<String, Integer> f4 = (String s) -> s.length();
		// Combine two values
		BiFunction<Integer, Integer, Integer> f5 = (Integer a, Integer b) -> a * b;
		// Compare two objects
		BiFunction<Apple, Apple, Integer> f6 = (Apple a1, Apple a2) -> a1.getWeight() - a2.getWeight();

		// Functional interfaces
		Runnable r1 = () -> System.out.println("Hello World 1");
		Runnable r2 = new Runnable() {
			public void run() {
				System.out.println("Hello World 2");
			}
		};
		process(r1);
		process(r2);
		process(() -> System.out.println("Hello World 3"));

		new Thread(r1).start();
		new Thread(r2).start();
		new Thread(() -> System.out.println("Hello World 3")).start();

		//
		File[] listFiles = Lesson_01_FunctionsIntro.listFiles();
		File first = listFiles[0];
		try (BufferedReader r = new BufferedReader(new FileReader(first))) {
			String result = processFile(r, (BufferedReader br) -> {
				try {
					return br.readLine() + System.lineSeparator() + br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			});
			System.out.println(result);
		}

		// Second variant
		String result = processFile2(first, (r) -> {
			// Skipping 2 first lines
			r.readLine();
			r.readLine();

			return r.readLine().trim() + System.lineSeparator() + r.readLine().trim();
		});
		System.out.println(result);

		// Passing lambdas as parameters
		String oneLine = processFile2(first, (BufferedReader br) -> br.readLine());
		String twoLines = processFile2(first, (BufferedReader br) -> br.readLine() + br.readLine());

		System.out.println(oneLine);
		System.out.println(twoLines);

		// Predicates
		Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
		List<String> listOfStrings = Arrays.asList("", "string 2", "", "string 4");
		List<String> nonEmpty = filter(listOfStrings, nonEmptyStringPredicate);
		nonEmpty.forEach(System.out::println);

		// Consumers
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
		forEach(list, (i) -> System.out.println(i));

		// Functions
		List<Integer> map = map(nonEmpty, (s) -> Integer.valueOf(s.substring(s.indexOf(" ") + 1)));
		map.forEach(System.out::println);

		BiConsumer<? super String, ? super List<String>> action = (k, v) -> System.out.println(k + " = " + v.toString());
		//
		nonEmpty.stream() //
				.filter(s -> !s.isEmpty()) //
				.collect(Collectors.groupingBy(s -> s.substring(s.indexOf(" ") + 1))) //
				.forEach(action);

		// Primitives
		IntPredicate evenNumbers = (int i) -> i % 2 == 0;
		System.out.println(evenNumbers.test(1000)); // no boxing
		Predicate<Integer> oddNumbers = (Integer i) -> i % 2 != 0;
		System.out.println(oddNumbers.test(1000)); // autoboxing

		////////// Examples of lambdas with functional interfaces //////////

		// A boolean expression
		Predicate<List<String>> p = (List<String> lst) -> lst.isEmpty();

		// Creating objects
		Supplier<Apple> sp = () -> new Apple(Color.GREEN, 10);

		// Consuming from an object
		Consumer<Apple> c = (Apple a) -> System.out.println(a.getWeight());

		// Select/extract from an object
		ToIntFunction<String> tif = (String s) -> s.length();

		// Combine two values
		IntBinaryOperator ibo = (int a, int b) -> a * b;

		// Compare two objects
		ToIntBiFunction<Apple, Apple> tibf = (Apple a1, Apple a2) -> a1.getWeight() - a2.getWeight();

		// Same lambda, different functional interfaces
		Callable<Integer> callable = () -> 42;
		PrivilegedAction<Integer> privilegedAction = () -> 42;

		callable.call();
		privilegedAction.run();

		Comparator<Apple> c1 = (Apple a1, Apple a2) -> a1.getWeight() - a2.getWeight();
		ToIntBiFunction<Apple, Apple> c2 = (Apple a1, Apple a2) -> a1.getWeight() - a2.getWeight();
		BiFunction<Apple, Apple, Integer> c3 = (Apple a1, Apple a2) -> a1.getWeight() - a2.getWeight();

		// Predicate has a boolean return
		IntPredicate ipr = (int i) -> list.add(i);

		// Consumer has a void return
		IntConsumer ics = (int i) -> list.add(i);

		// Quiz 3.5: Type checking—why won’t the following code compile?
		Runnable /* Object */ o = () -> {
			System.out.println("Tricky example");
		};

		// Valid
		int portNumber = 1337;
		Runnable r = () -> System.out.println(portNumber);

		// Local variable portNumber2 defined in an enclosing scope must be final or effectively final
		/*
		 * int portNumber2 = 1337; Runnable r3 = () -> System.out.println(portNumber2); portNumber2 = 31337;
		 */

		// Method references
		apples.sort(Comparator.comparing(Apple::getColor));

		apples.forEach(System.out::println);

		apples.stream() //
				.map(a -> a.getColor().name()) //
				.collect(Collectors.toList()) //
				.stream() //
				.distinct() //
				.map(s -> s.substring(0, 1)) //
				.collect(Collectors.toList()) //
				.forEach(System.out::println);

		// BiFunction<String, Integer, String> substrF = (str, i) -> str.substring(0, i + 1);
		List<String> collectedUpperCased = nonEmpty.stream() //
				.map(String::toUpperCase) //
				.collect(Collectors.toList());
		collectedUpperCased //
				.stream() //
				.forEach(System.out::println);

		collectedUpperCased.stream() //
				.filter(Lesson04_Lambdas::isValidName) //
				.collect(Collectors.toList()) //
				.stream() //
				.forEach(System.out::println);

		List<String> strings = Arrays.asList("a", "b", "A", "B");
		strings.sort(String::compareToIgnoreCase);
		strings //
				.stream() //
				.forEach(System.out::println);

		// 3.6.2 Constructor references
		// TODO

	}

	private static void process(Runnable r) {
		r.run();
	}

	private static String processFile(BufferedReader bufferedReader, Function<BufferedReader, String> function) throws Exception {
		return function.apply(bufferedReader);
	}

	@FunctionalInterface
	public interface BufferedReaderProcessor {
		String process(BufferedReader b) throws IOException;
	}

	private static String processFile2(File f, BufferedReaderProcessor bufferedReaderProcessor) throws Exception {
		try (BufferedReader r = new BufferedReader(new FileReader(f))) {
			return bufferedReaderProcessor.process(r);
		}
	}

	public static <T> List<T> filter(List<T> list, Predicate<T> p) {
		List<T> results = new ArrayList<>();
		for (T t : list) {
			if (p.test(t)) {
				results.add(t);
			}
		}
		return results;
	}

	public static <T> void forEach(List<T> list, Consumer<T> c) {
		for (T t : list) {
			c.accept(t);
		}
	}

	public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
		List<R> result = new ArrayList<>();
		for (T t : list) {
			result.add(f.apply(t));
		}
		return result;
	}

	private static boolean isValidName(String string) {
		return Character.isUpperCase(string.charAt(0));
	}

}
