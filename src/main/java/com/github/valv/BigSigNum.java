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
    super((long) number, radix);
    if (number < 0) this.negative = true;
  }

  // Long constructor
  public BigSigNum(long number) {
    this(number, BigSigNum.RADIX_DEFAULT);
  }

  // Long/radix constructor
  public BigSigNum(long number, byte radix) {
    super(number, radix);
    if (number < 0) this.negative = true;
  }

  // String constructor
  public BigSigNum(String number) {
    this(number, BigSigNum.RADIX_DEFAULT);
  }

  // String/radix constructor
  public BigSigNum(String number, byte radix) {
    super(number, radix);
    if (number.charAt(0) == '-') this.negative = true;
  }

  // Object (BigNum) constructor
  public BigSigNum(BigNum number) {
    super(number); // positive
  }

  // Object (BigSigNum) constructor
  public BigSigNum(BigSigNum number) {
    super(number); // auto upcasting
    this.negative = number.negative;
  }

  @Override
  public void add(BigNum number) {
    if (this.radix != number.radix) return; // discard different radices
    if (this.negative) {
      // Negative + positive
      if (this.compare(number) < 0) {
        // - lesser + greater -> greater - lesser
        this.negative = !this.negative;
        BigNum swap = new BigNum(number);
        ArrayList thisData = this.data;
        this.data = swap.data;
        swap.data = thisData;
        super.subtract(swap);
      } else {
        // - greater + lesser -> - (greater - lesser)
        // lesser ~ lesser or equal
        super.subtract(number);
      }
    } else {
      // Positive + positive
      super.add(number);
    }
  }

  public void add(BigSigNum number) {
    // Positive + positive = positive + positive
    // Positive + negative = - (negative + positive)
    // Negative + positive = negative + positive
    // Negative + negative = - (positive + positive)
    // Change sign only if the addend is negative (double not xor)
    this.negative = !(this.negative ^ number.negative);
    this.add((BigNum) number); // upcasting
    this.negative = !(this.negative ^ number.negative);
    /*
    if (this.radix != number.radix) return; // discard different radices
    if (this.negative ^ number.negative) {
      // (positive + negative) or (negative + positive)
      if (this.compare(number) < 0) {
        // (- lesser + greater) or (lesser - greater) ->
        // (greater - lesser) or - (greater - lesser)
        this.negative = number.negative;
        BigNum swap = new BigNum(number); // automatic upcast
        ArrayList<Byte> thisData = this.data;
        this.data = swap.data;
        swap.data = thisData;
        super.subtract(swap);
      } else {
        // (- greater + lesser) or (greater - lesser) ->
        // - (greater - lesser) or (greater - lesser)
        // Lesser ~ lesser or equal
        super.subtract(number); // automatic upcast
      }
    } else {
      // (positive + positive) or (negative + negative)
      super.add(number);
    }*/
  }

  @Override
  public void subtract(BigNum number) {
    // Positive - positive = - (negative + positive)
    // Negative - positive = - (positive + positive)
    // Minuend double sign inversion
    this.negative = !this.negative;
    this.add(number);
    this.negative = !this.negative;
  }

  public void subtract(BigSigNum number) {
    // Positive - positive = - (negative + positive)
    // Negative - positive = - (positive + positive)
    // Positive - negative = - (negative + negative)
    // Negative - negative = - (positive + negative)
    // Minuend double sign inversion
    this.negative = !this.negative;
    this.add(number);
    this.negative = !this.negative;
  }

  @Override
  public void multiply(BigNum number) {
    // Positive * positive = positive * positive
    // Negative * positive = negative * positive
    // Sign does not change
    super.multiply(number);
  }

  public void multiply(BigSigNum number) {
    // Positive * positive = positive * positive
    // Positive * negative = negative * positive
    // Negative * positive = negative * positive
    // Negative * negative = positive * positive
    // Transer multiplier's sign to multiplicand if negative (single not xor)
    this.negative = !(this.negative ^ number.negative);
    super.multiply((BigNum) number);
  }

  @Override
  public BigNum divide(BigNum number) {
    // Positive * positive = positive * positive
    // Negative * positive = negative * positive
    // Sign does not change
    return super.divide(number);
  }

  public BigNum divide(BigSigNum number) {
    // Positive * positive = positive * positive
    // Positive * negative = negative * positive
    // Negative * positive = negative * positive
    // Negative * negative = positive * positive
    // Transer divisor's sign to dividend if negative (single not xor)
    this.negative = !(this.negative ^ number.negative);
    return super.divide((BigNum) number);
  }

  @Override
  public String toString() {
    String sign = "";
    if (this.negative && this.data != null)
      if (this.length > 0 && this.data.get(0) > 0) sign = "-";
    return sign + super.toString();
  }
}

/* vim: set si et ts=2 sw=2: */
