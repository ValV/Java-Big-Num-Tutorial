/*
 * @(#)BigNumTest.java        1.0 16/09/14
 *
 * Copyright (c) 2016 Vladimir Valeyev
 *
 * This file is part of Java Big Num Tutorial.
 *
 * Java Big Num Tutorial is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * Java Big Num Tutorial is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Java Big Num Tutorial. If not, see
 * <http://www.gnu.org/licenses/>
 *
 * <valv at linuxmail dot org>, 14 September 2016
 */

import java.lang.Integer;
import com.github.valv.BigNum;

/**
 * BigNumTest class provides test suit for BigNum class.
 *
 * @version 1.0 14 Sep 2016
 * @author Vladimir Valeyev
 */

class BigNumTest {
	/* This class tests base arithmetic and calculates factorial */
	public static void main(String[] args) {
		BigNum testNumber1 = new BigNum("999999999999999999999999");
		BigNum testNumber2 = new BigNum("888888888888888888888888");
		BigNum testNumber3 = new BigNum("111111111111111111111111");
		BigNum testNumber4 = new BigNum("222222222222");
		BigNum testNumber5 = new BigNum(0);
		BigNum testNumber6 = new BigNum();
		BigNum testResult1;
		BigNum testResult2;
		System.console().writer().println("...:::Initial data:::...");
		System.console().writer().println("testNumber1 = " + testNumber1);
		System.console().writer().println("testNumber2 = " + testNumber2);
		System.console().writer().println("testNumber3 = " + testNumber3);
		System.console().writer().println("testNumber4 = " + testNumber4);
		System.console().writer().println("testNumber5 = " + testNumber5);
		System.console().writer().println("testNumber6 = " + testNumber6);

		System.console().writer().println("\n...:::Addition trial:::...");
		testResult1 = new BigNum(testNumber1); testResult1.add(testNumber3);
		System.console().writer().println(testNumber1 + " + " + testNumber3 + " = " + testResult1);
		testResult1 = new BigNum(testNumber2); testResult1.add(testNumber3);
		System.console().writer().println(testNumber2 + " + " + testNumber3 + " = " + testResult1);
		testResult1 = new BigNum(testNumber2); testResult1.add(testNumber5);
		System.console().writer().println(testNumber2 + " + " + testNumber5 + " = " + testResult1);
		testResult1 = new BigNum(testNumber5); testResult1.add(testNumber4);
		System.console().writer().println(testNumber5 + " + " + testNumber4 + " = " + testResult1);
		testResult1 = new BigNum(testNumber6); testResult1.add(testNumber3);
		System.console().writer().println(testNumber6 + " + " + testNumber3 + " = " + testResult1);
		testResult1 = new BigNum(testNumber5); testResult1.add(testNumber6);
		System.console().writer().println(testNumber5 + " + " + testNumber6 + " = " + testResult1);

		System.console().writer().println("\n...:::Subtraction trial:::...");
		testResult1 = new BigNum(testNumber2); testResult1.subtract(testNumber3);
		System.console().writer().println(testNumber2 + " - " + testNumber3 + " = " + testResult1);
		testResult1 = new BigNum(testNumber2); testResult1.subtract(testNumber1);
		System.console().writer().println(testNumber2 + " - " + testNumber1 + " = " + testResult1);
		testResult1 = new BigNum(testNumber1); testResult1.subtract(testNumber4);
		System.console().writer().println(testNumber1 + " - " + testNumber4 + " = " + testResult1);
		testResult1 = new BigNum(testNumber5); testResult1.subtract(testNumber3);
		System.console().writer().println(testNumber5 + " - " + testNumber3 + " = " + testResult1);
		testResult1 = new BigNum(testNumber6); testResult1.subtract(testNumber5);
		System.console().writer().println(testNumber6 + " - " + testNumber5 + " = " + testResult1);
		testResult1 = new BigNum(testNumber5); testResult1.subtract(testNumber6);
		System.console().writer().println(testNumber5 + " - " + testNumber6 + " = " + testResult1);

		System.console().writer().println("\n...:::Multiplication trial:::...");
		testResult1 = new BigNum(testNumber3); testResult1.multiply(testNumber4);
		System.console().writer().println(testNumber3 + " * " + testNumber4 + " = " + testResult1);
		testResult1 = new BigNum(testNumber4); testResult1.multiply(testNumber3);
		System.console().writer().println(testNumber4 + " * " + testNumber3 + " = " + testResult1);
		testResult1 = new BigNum(testNumber5); testResult1.multiply(testNumber1);
		System.console().writer().println(testNumber5 + " * " + testNumber1 + " = " + testResult1);
		testResult1 = new BigNum(testNumber6); testResult1.multiply(testNumber5);
		System.console().writer().println(testNumber6 + " * " + testNumber5 + " = " + testResult1);

		System.console().writer().println("\n...:::Division trial:::...");
		testResult1 = new BigNum(testNumber1); testResult2 = testResult1.divide(testNumber3);
		System.console().writer().println(testNumber1 + " = " + testNumber3 + " * " + testResult1 + " + " + testResult2);
		testResult1 = new BigNum(testNumber2); testResult2 = testResult1.divide(testNumber1);
		System.console().writer().println(testNumber2 + " = " + testNumber1 + " * " + testResult1 + " + " + testResult2);
		testResult1 = new BigNum(testNumber2); testResult2 = testResult1.divide(testNumber4);
		System.console().writer().println(testNumber2 + " = " + testNumber4 + " * " + testResult1 + " + " + testResult2);
		testResult1 = new BigNum(testNumber1); testResult2 = testResult1.divide(testNumber4);
		System.console().writer().println(testNumber1 + " = " + testNumber4 + " * " + testResult1 + " + " + testResult2);
		testResult1 = new BigNum(testNumber4); testResult2 = testResult1.divide(testNumber5);
		System.console().writer().println(testNumber4 + " = " + testNumber5 + " * " + testResult1 + " + " + testResult2);
		testResult1 = new BigNum(testNumber6); testResult2 = testResult1.divide(testNumber4);
		System.console().writer().println(testNumber6 + " = " + testNumber4 + " * " + testResult1 + " + " + testResult2);

		System.console().writer().println("\n...:::Factorial of 1000:::...");
		testResult1 = new BigNum(1);
		for (int index = 1; index <= 1000; index ++) {
			testResult1.multiply(new BigNum(index));
		}
		System.console().writer().println("1000! = " + testResult1 + " (" + (new Integer(testResult1.length())) + " digits)");
	}
}
