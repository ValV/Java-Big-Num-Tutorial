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
  public void testConstructCompare() {
    // Create numbers
    byte hex = 16;
    // BigNum class has several constructors:
    // BigNum(), BigNum(int), BigNum(int, byte),
    // BigNum(String), BigNum(String, byte), BigNum(BigNum)
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
  public void testAddition() {
    // Create numbers
    byte hex = 16;
    // Unsigned long integer max value is 18446744073709551615 (20 characters),
    // but maximum numeric value for the class constructor is 2147483647
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
  public void testSubtraction() {
    System.out.println("\n...:::Subtraction test:::...");
  }

  @Test
  public void testMultiplication() {
    System.out.println("\n...:::Multiplication test:::...");
  }

  @Test
  public void testDivision() {
    System.out.println("\n...:::Division test:::...");
  }

  @Test
  public void testStub () {
    assertEquals(true, true);
  }
}

// vim: set si et ts=2 sw=2 :
