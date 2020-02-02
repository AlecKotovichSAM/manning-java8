/**
 * 
 */
package com.alec.java8.chapter02;

import java.util.List;

import com.alec.java8.chapter01.Lesson_01_FunctionsIntro.Apple;
import com.alec.java8.chapter01.Lesson_01_FunctionsIntro.AppleInventory;

/**
 * @author okoto
 *
 */
public class Quiz2_1_PrettyPrint {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Apple> apples = AppleInventory.getApples();

		prettyPrintApple(apples, new WeightPrettyAppleFormatter());

		prettyPrintApple(apples, new HeavyOrLightPrettyAppleFormatter());
		
		prettyPrintApple(apples, new AppleFancyFormatter());

	}

	public static void prettyPrintApple(List<Apple> inventory, PrettyAppleFormatter applePrinter) {
		for (Apple apple : inventory) {
			String output = applePrinter.format(apple);
			System.out.println(output);
		}
	}

	private interface PrettyAppleFormatter {
		String format(Apple apple);
	}

	private static class WeightPrettyAppleFormatter implements PrettyAppleFormatter {

		@Override
		public String format(Apple apple) {
			return "Apple weight: " + apple.getWeight();
		}

	}

	private static class HeavyOrLightPrettyAppleFormatter implements PrettyAppleFormatter {

		@Override
		public String format(Apple apple) {
			return apple.getWeight() > 150 ? "Apple is heavy!" : "Apple is light...";
		}

	}

	private static class AppleFancyFormatter implements PrettyAppleFormatter {
		public String format(Apple apple) {
			String characteristic = apple.getWeight() > 150 ? "heavy" : "light";
			return "A " + characteristic + " " + apple.getColor() + " apple";
		}
	}

}
