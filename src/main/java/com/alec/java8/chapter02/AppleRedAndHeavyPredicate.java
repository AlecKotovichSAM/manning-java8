/**
 * 
 */
package com.alec.java8.chapter02;

import com.alec.java8.chapter01.Lesson_01_FunctionsIntro.Apple;
import com.alec.java8.chapter01.Lesson_01_FunctionsIntro.Color;

/**
 * @author okoto
 *
 */
public class AppleRedAndHeavyPredicate implements ApplePredicate {

	@Override
	public boolean test(Apple apple) {
		return apple.getWeight() >= 150 && Color.RED.equals(apple.getColor());
	}

}
