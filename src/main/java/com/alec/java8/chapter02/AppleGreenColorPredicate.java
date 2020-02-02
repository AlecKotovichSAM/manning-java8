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
public class AppleGreenColorPredicate implements ApplePredicate {

	@Override
	public boolean test(Apple apple) {
		return Color.GREEN.equals(apple.getColor());
	}

}
