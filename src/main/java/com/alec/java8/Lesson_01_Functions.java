/**
 * 
 */
package com.alec.java8;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author okoto
 *
 */
public class Lesson_01_Functions {

	public enum Color {
		GREEN, YELLOW, RED
	}

	@Getter
	@AllArgsConstructor
	@ToString
	public static class Apple {
		private Color color;

		private int weight;

		public static boolean isGreenApple(Apple apple) {
			return Color.GREEN.equals(apple.getColor());
		}

		public static boolean isHeavyApple(Apple apple) {
			return apple.getWeight() > 150;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		listFiles(); // 1)

		List<Apple> apples = new ArrayList<>();
		Apple apple1 = new Apple(Color.GREEN, 151);
		Apple apple2 = new Apple(Color.YELLOW, 149);

		apples.add(apple1);
		apples.add(apple2);

		List<Apple> filteredApples = filterApples(apples, Apple::isGreenApple);
		List<Apple> filteredApples2 = filterApples(filteredApples, Apple::isHeavyApple);

		filteredApples2.stream().forEach(System.out::println);

	}

	// 1) Example 1: Function
	private static void listFiles() {
		FileFilter filter = File::isHidden;
		File[] hiddenFiles = new File(".").listFiles(filter);
		System.out.println(hiddenFiles.toString());
	}

	// 2) Example 2: Predicate
	static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
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
}
