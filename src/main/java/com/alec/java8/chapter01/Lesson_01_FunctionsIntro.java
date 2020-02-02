/**
 * 
 */
package com.alec.java8.chapter01;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author okoto
 *
 */
public class Lesson_01_FunctionsIntro {

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

	public static class AppleInventory {
		private final List<Apple> apples;

		private static AppleInventory INSTANCE;

		private AppleInventory() {
			apples = new ArrayList<>();

			Apple apple1 = new Apple(Color.GREEN, 151);
			Apple apple2 = new Apple(Color.YELLOW, 80);
			Apple apple3 = new Apple(Color.RED, 79);
			Apple apple4 = new Apple(Color.RED, 151);

			apples.add(apple1);
			apples.add(apple2);
			apples.add(apple3);
			apples.add(apple4);
		}

		public static List<Apple> getApples() {
			if (INSTANCE == null) {
				INSTANCE = new AppleInventory();
			}
			return INSTANCE.apples;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		listFiles(); // 1)

		List<Apple> apples = AppleInventory.getApples();
		// 2
		List<Apple> filteredApples = filterApples(apples, Apple::isGreenApple);
		List<Apple> filteredApples2 = filterApples(filteredApples, Apple::isHeavyApple);

		filteredApples2.stream().forEach(System.out::println);

		// 3
		List<Apple> filteredApples3 = filterApples(apples,
				(Apple a) -> a.getWeight() < 80 || Color.RED.equals(a.getColor()));
		filteredApples3.stream().forEach(System.out::println);

		// 4
		Stream<Apple> filteredApples4 = apples.stream().filter(a -> Color.YELLOW.equals(a.getColor()));
		filteredApples4.forEach(System.out::println);

	}

	// 1) Example 1: Function
	private static void listFiles() {
		FileFilter filter = File::isHidden;
		// MyFuncI funcI = File::canRead;
		// MyFuncI funcI = File::isDirectory;
		MyFuncI funcI = File::isFile;
		File[] hiddenFiles = new File(".").listFiles(filter);
		System.out.println(Arrays.asList(hiddenFiles) //
				.stream() //
				.map(f -> f.getName()) //
				.collect(Collectors.joining(",")));

		File[] readableFiles = new MyFile(".").listFiles(funcI);
		System.out.println(Arrays.asList(readableFiles) //
				.stream() //
				.map(f -> f.toString()) //
				.collect(Collectors.joining(", ")));

	}

	// 2) Example 2: Predicate
	private static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
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

	@FunctionalInterface
	public interface MyFuncI {

		boolean test(File file);
	}

	public static class MyFile extends File {

		public MyFile(String pathname) {
			super(pathname);
		}

		public File[] listFiles(MyFuncI funcI) {
			String ss[] = list();
			if (ss == null) {
				return null;
			}
			List<File> files = new ArrayList<>();
			for (String s : ss) {
				File f = new File(s);
				if ((funcI == null) || funcI.test(f)) {
					files.add(f);
				}
			}
			return files.toArray(new File[files.size()]);
		}

	}
}
