/*
 * @(#)BigSigNum.java     1.1 20/11/17
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
 * <valv> at <linuxmail dot org>, 20 November 2017
 */

package com.github.valv;

import java.util.ArrayList;
import java.util.List;

/**
 * BigSigNum class provides basics for signed arbitrary precision arithmetic.
 * This class implements signed integer radix arithmetic.
 *
 * @version 1.1 20 Nov 2017
 * @author Vladimir Valeyev
 */
public class BigSigNum extends BigNum {
  protected boolean negative = false;

  // Default constructor
  public BigSigNum() {
    super();
  }

  // Integer constructor
  public BigSigNum(int number) {
    this(number, BigSigNum.RADIX_DEFAULT);
  }

  // Integer/radix constructor
  public BigSigNum(int number, byte radix) {
    super(number, radix);
    if (number < 0) this.negative = true;
  }

  // String constructor
  public BigSigNum(String number) {
    this(number, BigSigNum.RADIX_DEFAULT);
  }

  // String/radix constructor
  public BigSigNum(String number, byte radix) {
    super(number, radix); // TODO: handle leading sign
    if (number.charAt(0) == '-') this.negative = true;
  }

  // Object (BigNum) constructor
  public BigSigNum(BigNum number) {
    super(number); // copy
  }

  // Object (BigSigNum) constructor
  public BigSigNum(BigSigNum number) {
    super(number); // copy
    this.negative = number.negative;
  }

  @Override
  public void add(BigNum number) {
    if (this.radix != number.radix) return; // discard different radixs
    if (this.negative) { // 'this' is negative
      if (this.compare(number) < 0) {
        // 'this' absolute value is less than 'number' so
        // swap the sign, swap values and subtract
        this.negative = !this.negative;
        BigNum swap = new BigNum(number);
        ArrayList thisData = this.data;
        this.data = swap.data;
        swap.data = thisData;
        super.subtract(swap);
      } else {
        // 'this' absolute value is more than 'number' so
        // just subtract
        super.subtract(number);
      }
    } else { // 'this' and 'number' are positive so do add
      super.add(number);
    }
  }

  public void add(BigSigNum number) {
    if (this.negative ^ number.negative) { // different signs
      if (this.compare(number) < 0) {
        // 'this' absolute value is less than 'number' so
        // set the sign of 'number', swap values and subtract
        this.negative = number.negative;
        BigNum swap = new BigNum(number); // automatic upcast
        ArrayList thisData = this.data;
        this.data = swap.data;
        swap.data = thisData;
        super.subtract(swap);
      } else {
        // 'this' absolute value is more than 'number' so
        // just subtract (the sign will remain the same)
        super.subtract(number); // automatic upcast
      }
    } else { // 'this' and 'number' have the same signs so do add
      super.add(number);
    }
  }

  @Override
  public void subtract(BigNum number) {
  }

  public void subtract(BigSigNum number) {
  }

  @Override
  public void multiply(BigNum number) {
  }

  public void multiply(BigSigNum number) {
  }

  @Override
  public BigNum divide(BigNum number) {
    return null;
  }

  public BigNum divide(BigSigNum number) {
    return null;
  }

  @Override
  public String toString() {
    String sign = "";
    if (this.negative && this.data != null)
      if (!(this.length == 1 && this.data.get(0) == 0)) sign = "-";
    return sign + super.toString();
  }
}

/* vim: set si et ts=2 sw=2: */
