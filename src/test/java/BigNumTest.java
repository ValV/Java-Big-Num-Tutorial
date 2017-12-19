/*
 * @(#)BigNumTest.java        1.1 17/11/17
 *
 * Copyright (c) 2017 Vladimir Valeyev
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
 * <valv> at <linuxmail dot org>, 17 November 2017
 */

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.github.valv.BigNum;

/**
 * BigNumTest class provides JUnit (4.0 and higher) test case for BigNum class.
 *
 * @version 1.1 17 Nov 2017
 * @author Vladimir Valeyev
 */
public class BigNumTest {
  @Test
  public void testConstructCompare() throws Exception {
    // Create numbers
    byte hex = 16;
    // BigNum class has several constructors:
    // BigNum(), BigNum(int), BigNum(int, byte),
    // BigNum(String), BigNum(String, byte), BigNum(BigNum)
    // Unsigned long integer max value is 18446744073709551615 (20 characters),
    // but maximum numeric value for the class constructor is 2147483647
    BigNum testNumber1 = new BigNum(Integer.MAX_VALUE);
    assertNotNull("Integer constructor failed:", testNumber1);
    BigNum testNumber2 = new BigNum(Integer.MAX_VALUE, hex);
    assertNotNull("Integer/radix constructor failed:", testNumber2);
    BigNum testNumber3 = new BigNum(Long.toString(Long.MAX_VALUE));
    assertNotNull("String constructor failed:", testNumber3);
    BigNum testNumber4 = new BigNum(Long.toHexString(Long.MAX_VALUE), hex);
    assertNotNull("String/radix constructor failed:", testNumber4);
    BigNum testNumber5 = new BigNum(testNumber3);
    assertNotNull("Object constructor failed:", testNumber5);
    BigNum testNumber6 = new BigNum();
    assertNotNull("Default (null) constructor failed:", testNumber6);
    // Compare values
    assertEquals("Integer comparison failed:",
      Integer.toString(Integer.MAX_VALUE), testNumber1.toString());
    assertEquals("Integer/radix comparison failed:",
      Integer.toHexString(Integer.MAX_VALUE), testNumber2.toString());
    assertEquals("String comparison failed:",
      Long.toString(Long.MAX_VALUE), testNumber3.toString());
    assertEquals("String/radix comparison failed:",
      Long.toHexString(Long.MAX_VALUE), testNumber4.toString());
    assertEquals("Object comparison failed:",
      testNumber3.toString(), testNumber5.toString());
    assertEquals("Default comparison failed:",
      "null", testNumber6.toString());
    // Display values
    System.out.println("\n...:::Initial data:::..."
      + "\nNumber1 (Integer) = " + testNumber1
      + "\nNumber2 (Integer/hex) = " + testNumber2
      + "\nNumber3 (String) = " + testNumber3
      + "\nNumber4 (String/hex) = " + testNumber4
      + "\nNumber5 (Object) = " + testNumber5 + " (Number3)"
      + "\nNumber6 (Default) = " + testNumber6);
  }

  @Test
  public void testAddition() throws Exception {
    // Create numbers
    byte hex = 16;
    // Addition is implemented for BigNum arguments only (.add(BigNum))
    // Addition is possible only with arguments of the same radix
    // Addition with null does not affect a value
    BigNum testNumber1 = new BigNum("99999999999999999999"); // 20 dec
    assertNotNull("String constructor failed:", testNumber1);
    BigNum testNumber2 = new BigNum(1); // one
    assertNotNull("Integer constructor failed:", testNumber2);
    BigNum testNumber3 = new BigNum(0); // zero
    assertNotNull("Integer constructor failed:", testNumber3);
    BigNum testNumber4 = new BigNum("7fffffffffffffffffff", hex); // 20 hex
    assertNotNull("Integer/radix constructor failed:", testNumber4);
    BigNum testNumber5 = new BigNum(1, hex); // 0x1
    assertNotNull("Integer/radix constructor failed:", testNumber5);
    BigNum testNumber6 = new BigNum(0, hex); // 0x0
    assertNotNull("Integer/radix constructor failed:", testNumber6);
    BigNum testNumber7 = new BigNum(); // null
    assertNotNull("Default constructor failed:", testNumber7);
    // Add values
    testNumber1.add(testNumber2);
    testNumber1.add(testNumber3);
    assertEquals("Default (decimal) digit-shift and zero addition failed:",
      "100000000000000000000", testNumber1.toString());
    testNumber4.add(testNumber5);
    testNumber4.add(testNumber6);
    assertEquals("Hexadecimal non-shift and zero addition failed:",
      "80000000000000000000", testNumber4.toString());
    testNumber5.add(testNumber4);
    testNumber5.add(testNumber4);
    assertEquals("Hexadecimal digit-shift addition failed:",
      "100000000000000000001", testNumber5.toString());
    testNumber3.add(testNumber7);
    assertEquals("Default (decimal) value-null addition failed:",
      "0", testNumber3.toString());
    testNumber7.add(testNumber2);
    assertEquals("Default (decimal) null-value addition failed:",
      "null", testNumber7.toString());
    // Display values
    System.out.println("\n...:::Addition test:::..."
      + "\n99999999999999999999 + 1 + 0 = " + testNumber1
      + "\n0x7fffffffffffffffffff + 0x1 + 0x0 = " + testNumber4
      + "\n0x1 + 0x80000000000000000000 + 0x80000000000000000000 = "
      + testNumber5
      + "\n0 + null = " + testNumber3
      + "\nnull + 1 = " + testNumber7);
  }

  @Test
  public void testSubtraction() throws Exception {
    // Create numbers
    byte hex = 16;
    // Subtraction is not performed if a number has less than one digit (null)
    // If subtrahend is more than minuend, then zero is returned
    // Operations on numbers with different radix are not supported yet
    BigNum testNumber1 = new BigNum("100000000000000000000"); // 21 dec
    assertNotNull("String constructor failed:", testNumber1);
    BigNum testNumber2 = new BigNum(1); // one
    assertNotNull("Integer constructor failed:", testNumber2);
    BigNum testNumber3 = new BigNum(0); // zero
    assertNotNull("Integer constructor failed:", testNumber3);
    BigNum testNumber4 = new BigNum("100000000000000000000", hex); // 21 hex
    assertNotNull("String/radix constructor failed:", testNumber4);
    BigNum testNumber5 = new BigNum("80000000000000000000", hex); // 20 hex
    assertNotNull("String/radix constructor failed:", testNumber5);
    BigNum testNumber6 = new BigNum(1, hex); // 0x1
    assertNotNull("Integer/radix constructor failed:", testNumber6);
    BigNum testNumber7 = new BigNum(0, hex); // 0x0
    assertNotNull("Integer/radix constructor failed:", testNumber7);
    BigNum testNumber8 = new BigNum(); // null
    assertNotNull("Default constructor failed:", testNumber8);
    // Subtract values
    testNumber1.subtract(testNumber2);
    testNumber1.subtract(testNumber3);
    assertEquals("Default (decimal) digit-shift and zero subtraction failed:",
      "99999999999999999999", testNumber1.toString());
    testNumber4.subtract(testNumber5);
    testNumber4.subtract(testNumber7);
    assertEquals("Hexadecimal digit-shift and zero subtraction failed:",
      "80000000000000000000", testNumber4.toString());
    testNumber5.subtract(testNumber6);
    assertEquals("Hexadecimal non-shift subtraction failed:",
      "7fffffffffffffffffff", testNumber5.toString());
    testNumber2.subtract(testNumber1);
    assertEquals("Underflow subtraction failed:",
      "0", testNumber2.toString());
    testNumber3.subtract(testNumber8);
    assertEquals("Default (decimal) zero-null subtraction failed:",
      "0", testNumber3.toString());
    testNumber8.subtract(testNumber3);
    assertEquals("Default (decimal) null-zero subtraction failed:",
      "null", testNumber8.toString());
    // Display values
    System.out.println("\n...:::Subtraction test:::..."
      + "\n100000000000000000000 - 1 - 0 = " + testNumber1
      + "\n0x100000000000000000000 - 0x80000000000000000000 - 0x0 = 0x"
      + testNumber4
      + "\n0x80000000000000000000 - 0x1 = 0x" + testNumber5
      + "\n1 - 99999999999999999999 = " + testNumber2
      + "\n0 - null = " + testNumber3
      + "\nnull - 0 = " + testNumber8);
  }

  @Test
  public void testMultiplication() throws Exception {
    // Create numbers
    byte hex = 16;
    // Multiplication is supported only by object (BigNum)
    // Multiplication of numbers with different radix is not supported yet
    // Multiplication by zero or infinity will result respectively
    BigNum testNumber1 = new BigNum("55555555555555555555"); // 20 dec
    assertNotNull("String constructor failed:", testNumber1);
    BigNum testNumber2 = new BigNum(2); // two
    assertNotNull("Integer constructor failed:", testNumber2);
    BigNum testNumber3 = new BigNum(1); // one
    assertNotNull("Integer constructor failed:", testNumber3);
    BigNum testNumber4 = new BigNum(0); // zero
    assertNotNull("Integer constructor failed:", testNumber4);
    BigNum testNumber5 = new BigNum("88888888888888888888", hex); // 20 hex
    assertNotNull("String/radix constructor failed:", testNumber5);
    BigNum testNumber6 = new BigNum(2, hex); // 0x2
    assertNotNull("Integer/radix constructor failed:", testNumber6);
    BigNum testNumber7 = new BigNum(1, hex); // 0x1
    assertNotNull("Integer/radix constructor failed:", testNumber7);
    BigNum testNumber8 = new BigNum(0, hex); // 0x0
    assertNotNull("Integer/radix constructor failed:", testNumber8);
    BigNum testNumber9 = new BigNum(); // null
    assertNotNull("Default constructor failed:", testNumber9);
    // Multiply values
    testNumber1.multiply(testNumber2);
    testNumber1.multiply(testNumber3);
    assertEquals("Default (decimal) digit-shift and one multiplication failed:",
      "111111111111111111110", testNumber1.toString());
    testNumber5.multiply(testNumber6);
    testNumber5.multiply(testNumber7);
    assertEquals("Hexadecimal digit-shift and one multiplication failed:",
      "111111111111111111110", testNumber5.toString());
    testNumber2.multiply(testNumber4);
    assertEquals("Default (decimal) number-zero multiplication failed:",
      "0", testNumber2.toString());
    testNumber8.multiply(testNumber6);
    assertEquals("Hexadecimal zero-number multiplication failed:",
      "0", testNumber8.toString());
    testNumber4.multiply(testNumber9);
    assertEquals("Default (decimal) zero-null multiplication failed:",
      "0", testNumber4.toString());
    testNumber9.multiply(testNumber4);
    assertEquals("Default (decimal) null-zero multiplication failed:",
      "null", testNumber9.toString());
    // Display values
    System.out.println("\n...:::Multiplication test:::..."
      + "\n55555555555555555555 * 2 * 1 = " + testNumber1
      + "\n0x88888888888888888888 * 0x2 * 0x1 = 0x" + testNumber5
      + "\n2 * 0 = " + testNumber2
      + "\n0x0 * 0x2 = 0x" + testNumber8
      + "\n0 * null = " + testNumber4
      + "\nnull * 0 = " + testNumber9);
  }

  @Test
  public void testDivision() throws Exception {
    // Create numbers
    byte hex = 16;
    BigNum testNumber1 = new BigNum("44444444444444444444"); // 20 dec
    assertNotNull("String constructor failed:", testNumber1);
    BigNum testNumber2 = new BigNum(20); // twenty
    assertNotNull("Integer constructor failed:", testNumber2);
    BigNum testNumber3 = new BigNum(1); // one
    assertNotNull("Integer constructor failed:", testNumber3);
    BigNum testNumber4 = new BigNum(0); // zero
    assertNotNull("Integer constructor failed:", testNumber4);
    BigNum testNumber5 = new BigNum("cccccccccccccccccccc", hex); // 20 hex
    assertNotNull("String/radix constructor failed:", testNumber5);
    BigNum testNumber6 = new BigNum(32, hex); // 0x20
    assertNotNull("Integer/radix constructor failed:", testNumber6);
    BigNum testNumber7 = new BigNum(1, hex); // 0x1
    assertNotNull("Integer/radix constructor failed:", testNumber7);
    BigNum testNumber8 = new BigNum(0, hex); // 0x0
    assertNotNull("Integer/radix constructor failed:", testNumber8);
    BigNum testNumber9 = new BigNum(); // null
    assertNotNull("Default constructor failed:", testNumber9);
    // Divide values
    BigNum testRemainder1 = new BigNum(testNumber1.divide(testNumber2));
    BigNum testRemainder2 = new BigNum(testNumber1.divide(testNumber3));
    assertEquals("Default (decimal) digit-shift and one division failed:",
      "2222222222222222222", testNumber1.toString());
    BigNum testRemainder3 = new BigNum(testNumber5.divide(testNumber6));
    BigNum testRemainder4 = new BigNum(testNumber5.divide(testNumber7));
    assertEquals("Hexadecimal digit-shift and one division failed:",
      "6666666666666666666", testNumber5.toString());
    testNumber2.divide(testNumber4);
    assertEquals("Default (decimal) number-by-zero division failed:",
      "null", testNumber2.toString());
    testNumber6.divide(testNumber8);
    assertEquals("Hexadecimal number-by-zero division failed:",
      "null", testNumber6.toString());
    testNumber4.divide(testNumber9);
    assertEquals("Default (decimal) zero-by-null division failed:",
      "0", testNumber4.toString());
    testNumber9.divide(testNumber4);
    assertEquals("Default (decimal) null-by-zero division failed:",
      "null", testNumber9.toString());
    // Display values
    System.out.println("\n...:::Division test:::..."
      + "\n44444444444444444444 % 20 = " + testNumber1
      + " + " + testRemainder1 + "/20"
      + "\n44444444444444444444 % 1 = " + testNumber1
      + " + " + testRemainder2 + "/1"
      + "\n0xcccccccccccccccccccc % 0x20 = 0x" + testNumber5
      + " + " + testRemainder3 + "/0x20"
      + "\n0xcccccccccccccccccccc % 0x1 = 0x" + testNumber5
      + " + " + testRemainder4 + "/0x1"
      + "\n20 % 0 = " + testNumber2
      + "\n0x20 % 0x0 = " + testNumber6
      + "\n0 % null = " + testNumber4
      + "\nnull % 0 = " + testNumber9);
  }

  @Test
  public void testFactorial () throws Exception {
    BigNum testFactorial = new BigNum(1);
    for (int index = 1; index <= 1000; index ++) {
      testFactorial.multiply(new BigNum(index));
    }
    assertEquals("Calculation of 1000! failed:",
      2568, testFactorial.length());
    System.out.println("\n...:::Factorial of 1000:::..."
      + "\n1000! = " + testFactorial
      + " (" + Integer.toString(testFactorial.length()) + " digits)");

  }
}

/* vim: set si et ts=2 sw=2: */
