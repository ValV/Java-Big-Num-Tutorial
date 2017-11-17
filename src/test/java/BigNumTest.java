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

import com.github.valv.BigNum;

/**
 * BigNumTest class provides JUnit (4.0 and higher) test case for BigNum class.
 *
 * @version 1.1 17 Nov 2017
 * @author Vladimir Valeyev
 */
public class BigNumTest {
  BigNum testNumber1 = new BigNum("999999999999999999999999");
  BigNum testNumber2 = new BigNum("888888888888888888888888");
  BigNum testNumber3 = new BigNum("111111111111111111111111");
  BigNum testNumber4 = new BigNum("222222222222");
  BigNum testNumber5 = new BigNum(0);
  BigNum testNumber6 = new BigNum();

  BigNum testResult1;
  BigNum testResult2;

  @Test
  public void testNumber1() {
    String result = testNumber1.toString();
    assertEquals("999999999999999999999999", result);
    //System.console().writer().println("Number1 = " + result);
    System.out.println("Number1 = " + result);
  }

  @Test
  public void testNumber2() {
    String result = testNumber2.toString();
    assertEquals("888888888888888888888888", result);
    //System.console().writer().println("Number2 = " + result);
    System.out.println("Number2 = " + result);
  }

  @Test
  public void testNumber3() {
    String result = testNumber3.toString();
    assertEquals("111111111111111111111111", result);
    //System.console().writer().println("Number3 = " + result);
    System.out.println("Number3 = " + result);
  }

  @Test
  public void testNumber4() {
    String result = testNumber4.toString();
    assertEquals("222222222222", result);
    //System.console().writer().println("Number4 = " + result);
    System.out.println("Number4 = " + result);
  }

  @Test
  public void testNumber5() {
    String result = testNumber5.toString();
    assertEquals("0", result);
    //System.console().writer().println("Number5 = " + result);
    System.out.println("Number5 = " + result);
  }

  @Test
  public void testNumber6() {
    String result = testNumber6.toString();
    assertEquals("null", result);
    //System.console().writer().println("Number6 = " + result);
    System.out.println("Number6 = " + result);
  }

  @Test
  public void testStub () {
    assertEquals(true, true);
  }
}

// vim: set si et ts=2 sw=2 :
