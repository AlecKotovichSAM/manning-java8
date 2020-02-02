/**
 * 
 */
package com.alec.java8.chapter02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import com.alec.java8.chapter01.Lesson_01_FunctionsIntro.Apple;
import com.alec.java8.chapter01.Lesson_01_FunctionsIntro.AppleInventory;
import com.alec.java8.chapter01.Lesson_01_FunctionsIntro.Color;

/**
 * @author okoto
 *
 */
public class Lesson_03_BehaviorParameterization {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Apple> apples = AppleInventory.getApples();

		System.out.printf("Green apples: %s\n",
				filterApples(apples, new AppleGreenColorPredicate()) //
						.stream() //
						.map((Apple apple) -> apple.toString()) //
						.collect(Collectors.joining(", ")));

		System.out.printf("Heavy apples: %s\n",
				filterApples(apples, new AppleHeavyWeightPredicate()) //
						.stream() //
						.map((Apple apple) -> apple.toString()) //
						.collect(Collectors.joining(", ")));

		System.out.printf("Red and heavy apples: %s\n",
				filterApples(apples, new AppleRedAndHeavyPredicate()) //
						.stream() //
						.map((Apple apple) -> apple.toString()) //
						.collect(Collectors.joining(", ")));

		// Using anonymous class
		List<Apple> redApples = filterApples(apples, new ApplePredicate() {
			public boolean test(Apple apple) {
				return Color.RED.equals(apple.getColor());
			}
		});

		System.out.printf("Red apples: %s\n", redApples//
				.stream() //
				.map((Apple apple) -> apple.toString()) //
				.collect(Collectors.joining(", ")));

		// Using lambdas instead of custom predicate or anonymous class - result is the same!
		System.out.printf("Red apples: %s\n", apples//
				.stream() //
				.filter(apple -> Color.RED.equals(apple.getColor())) //
				.map((Apple apple) -> apple.toString()) //
				.collect(Collectors.joining(", ")));

		// Seventh attempt: abstracting over List type

		System.out.printf("Red apples: %s\n",
				filter(apples, apple -> Color.RED.equals(apple.getColor())) //
						.stream() //
						.map(a -> a.toString()) //
						.collect(Collectors.joining(", ")));
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		System.out.printf("Even numbers: %s\n",
				filter(numbers, number -> number % 2 == 0) //
						.stream() //
						.map(a -> a.toString()) //
						.collect(Collectors.joining(", ")));

		// Sorting with a Comparator
		apples.sort(new Comparator<Apple>() {
			@Override
			public int compare(Apple a1, Apple a2) {
				return a1.getWeight() - a2.getWeight();
			}
		});

		System.out.println("Sorted by weight asc");
		apples.forEach(System.out::println);

		System.out.println("Sorted by weight via lambda desc");
		apples //
				.sort((a1, a2) -> a2.getWeight() - a1.getWeight());
		apples.forEach(System.out::println);

		// Threads
		Thread t = new Thread(
				() -> System.out.println("Hello world from Lambda thread!"));
		t.start();

		// Returning a result using Callable
		ExecutorService executorService = Executors.newCachedThreadPool();
		Future<String> threadName = executorService
				.submit(new Callable<String>() {
					@Override
					public String call() throws Exception {
						return "Thread name from thread: "
								+ Thread.currentThread().getName();
					}
				});
		System.out.println("Future<String> threadName = " + threadName);
		
		Future<String> threadName2 = executorService.submit(
				() -> Thread.currentThread().getName());
		
		System.out.println("Future<String> threadName2 = " + threadName2);

	}

	public static List<Apple> filterApples(List<Apple> inventory,
			ApplePredicate p) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (p.test(apple)) {
				result.add(apple);
			}
		}
		return result;
	}

	public interface Predicate<T> {
		boolean test(T t);
	}

	public static <T> List<T> filter(List<T> list, Predicate<T> p) {
		List<T> result = new ArrayList<>();
		for (T e : list) {
			if (p.test(e)) {
				result.add(e);
			}
		}
		return result;
	}

}
