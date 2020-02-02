/**
 * 
 */
package com.alec.java8.chapter02;

import com.alec.java8.chapter01.Lesson_01_FunctionsIntro.Apple;

/**
 * @author okoto
 *
 */
public class AppleHeavyWeightPredicate implements ApplePredicate {

	@Override
	public boolean test(Apple apple) {
		return apple.getWeight() >= 150;
	}

}
