/**
 * 
 */
package com.alec.java8.chapter03;

import java.util.concurrent.Callable;

/**
 * @author okoto
 *
 */
public class Quiz3_3_ValidLambdas {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//
		execute(() -> {
		});

		fetch();

		/*
		 * Invalid: Predicate<Apple> p = (Apple a) -> a.getWeight();
		 */
	}

	public static void execute(Runnable r) {
		r.run();
	}

	public static Callable<String> fetch() {
		return () -> "Tricky example ;-)";
	}

}
