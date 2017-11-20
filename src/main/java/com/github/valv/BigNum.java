/*
 * @(#)BigNum.java        1.1 20/11/17
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
 * BigNum class provides basics for arbitrary precision arithmetic.
 * This class implements unsigned integer radix arithmetic.
 *
 * @version 1.1 20 Nov 2017
 * @author Vladimir Valeyev
 */
public class BigNum {
  static final byte RADIX_MAX = 36;
  static final byte RADIX_DEFAULT = 10;
  static final byte RADIX_MIN = 2;
  static final String RADIX_SYMBOL = "0123456789abcdefghijklmnopqrstuvwxyz";

  protected byte radix = RADIX_DEFAULT;
  protected int length = 0;
  protected ArrayList<Byte> data;

  /* Constructors */

  // Default constructor
  public BigNum() {
    this.data = new ArrayList<Byte>(this.length);
  }

  // Integer constructor
  public BigNum(int number) {
    this(number, BigNum.RADIX_DEFAULT); // default radix is 10
  }

  // Integer/radix constructor
  public BigNum(int number, byte radix) {
    radix = (radix > this.RADIX_MAX) ? this.RADIX_MAX : radix; // trim radix
    radix = (radix < this.RADIX_MIN) ? this.RADIX_MIN : radix; // trim radix
    if (number < 0) number = -number; // only positive
    this.radix = radix;
    this.data = new ArrayList<Byte>(this.length);
    do {
      // Add digits to the array
      this.data.add((byte) (number % radix));
      number /= radix;
    } while (number > 0);
    this.length = this.data.size();
  }

  // String constructor
  public BigNum(String number) {
    this(number, BigNum.RADIX_DEFAULT); // default radix is 10
  }

  // String/radix constructor
  public BigNum(String number, byte radix) {
    radix = (radix > this.RADIX_MAX) ? this.RADIX_MAX : radix; // trim radix
    radix = (radix < this.RADIX_MIN) ? this.RADIX_MIN : radix; // trim radix
    number = number.toLowerCase();
    this.radix = radix;
    this.data = new ArrayList<Byte>(this.length);
    int digit = 0;
    for (int position = number.length() - 1; position >= 0; position --) {
      // Add digit's value to the array
      digit =  this.RADIX_SYMBOL.indexOf(number.charAt(position));
      if (digit >= 0) {
        this.data.add((byte) digit);
      }
    }
    this.length = this.data.size();
  }

  // Object (BigNum) constructor
  public BigNum(BigNum number) {
    this.radix = number.radix;
    this.length = number.length;
    this.data = new ArrayList<Byte>(this.length);
    for (byte digit: number.data) { // deep copy
      this.data.add(digit);
    }
  }

  /* Public methods */

  // Addition method
  public void add(BigNum number) {
    if (this.length < 1 || number.length < 1) return;
    if (number.length == 1 && number.data.get(0) <= 0) return;
    int index = 0, carry = 0;
    if (this.compare(number) > 0) { // 'this' > 'number'
      for (; index < number.length; index ++) {
        carry += number.data.get(index);
        carry += this.data.get(index);
        this.data.set(index, (byte) (carry % this.radix));
        carry /= this.radix;
      } // sum carry with both arrays' values
      while (carry > 0) {
        if (index < this.length) { // in the array bounds
          carry += this.data.get(index);
          this.data.set(index ++, (byte) (carry % this.radix));
        } else { // out of the array bounds
          this.data.add((byte) (carry % this.radix));
          this.length ++;
        }
        carry /= this.radix;
      } // sum carry with the largest ('this') array's values
    } else { // 'this' <= 'number'
      for (; index < this.length; index ++) {
        carry += this.data.get(index);
        carry += number.data.get(index);
        this.data.set(index, (byte) (carry % this.radix));
        carry /= this.radix;
      } // sum carry with both arrays' values
      while (carry > 0 || this.length < number.length) {
        if (this.length < number.length) { // in the array bounds
          carry += number.data.get(this.length);
        }
        this.data.add((byte) (carry % this.radix));
        this.length ++;
        carry /= this.radix;
      } // sum carry with the largest ('number') array's values
    }
  }

  // Subtraction method
  public void subtract(BigNum number) {
    // Filter numbers that can not be processed (yet)
    if (this.length < 1 || number.length < 1 || this.radix != number.radix) {
      return;
    }
    // Subtract zero - axiom: x - 0 = x
    if (number.length == 1 && number.data.get(0) <= 0) {
      return;
    }
    // Return zero if subtrahend explicitly larger than minuend
    if (this.compare(number) <= 0) {
      this.data = new ArrayList<Byte>(); // old ArrayList will be GCed
      this.data.add((byte) ((this.length = 1) - 1));
      return;
    }
    int index = 0, carry = 0;
    // Cycle through subtrahend number (it has less digits)
    for (; index < number.length; index ++) {
      carry += this.data.get(index) + this.radix; // push radix
      carry -= number.data.get(index);
      this.data.set(index, (byte) (carry % this.radix));
      carry /= this.radix;
      carry -= 1; // pop radix
    }
    // Cycle through the rest of minuend number (it has more digits)
    for (; index < this.length && carry != 0; index ++) {
      carry += this.data.get(index) + this.radix; // push radix
      this.data.set(index, (byte) (carry % this.radix));
      carry /= this.radix;
      carry -= 1; // pop radix
    }
    // Remove leading zeroes
    while (this.length > 1 && this.data.get(this.length - 1) <= 0) {
      this.data.remove(-- this.length);
    }
    // Trim list of digits (assume any garbage)
    this.data.trimToSize();
  }

  // Multiplication method
  public void multiply(BigNum number) {
    // Filter numbers that can not be processed (yet)
    if (this.length < 1 || number.length < 1 || this.radix != number.radix) {
      return;
    }
    // Handle special cases: 0, -1, x
    if (this.length == 1 || number.length == 1) {
      byte thisDigit = this.data.get(0);
      byte thatDigit = number.data.get(0);
      if ((thisDigit <= 0 && this.length == 1)
          || (thatDigit <= 0 && number.length == 1)) {
        this.data = new ArrayList<Byte>(); // old ArrayList will be GCed
        if (thisDigit == 0 || thatDigit == 0) {
          // Product is zero when multiplicand or multiplier is zero
          this.data.add((byte) 0);
        } else {
          // Product is infinity when multiplicand or multiplier is infinity
          this.data.add((byte) -1);
        }
        return; // 0 and -1 cases
      }
      if (number.length == 1) {
        // Multiply by one-digit number
        this.multiplyByDigit(thatDigit);
        return; // x case
      }
    }
    // Multiply long numbers (log-space algorithm)
    int it = 0, in = 0, ip = 0; // indicies for this, number, product
    long total = 0;
    ArrayList<Byte> product;
    product = new ArrayList<Byte>(this.length + number.length);
    for (ip = 0; ip < (this.length + number.length) - 1; ip ++) {
      for (in = Math.max(0, ip - (this.length - 1));
          in <= Math.min(ip, (number.length - 1)); in ++) {
        it = ip - in;
        total += this.data.get(it) * number.data.get(in);
      }
      product.add((byte) (total % this.radix));
      total /= this.radix;
    }
    product.add((byte) (total % this.radix));
    // Remove leading zeroes
    while (ip > 0 && product.get(ip) <= 0) {
      product.remove(ip --);
    }
    // Trim list of digits (assume any garbage)
    product.trimToSize();
    this.data = product;
    this.length = this.data.size();
  }

  // Division method
  public BigNum divide(BigNum number) {
    // Filter numbers that can not be processed (yet)
    if (this.length < 1 || number.length < 1 || this.radix != number.radix) {
      return new BigNum(0); // return zero (useless object generator)
    }
    if (this.length == 1 || number.length == 1) {
      byte thisDigit = this.data.get(0);
      byte thatDigit = number.data.get(0);
      if ((thisDigit <= 0 && this.length == 1)
          || (thatDigit <= 0 && number.length == 1)) {
        this.data = new ArrayList<Byte>(); // old ArrayList will be GCed
        this.length = 0;
        if (thisDigit != -1  && thatDigit == -1) {
          // Divide anything except infinity by infinity -> zero
          this.data.add((byte) ((this.length = 1) - 1));
        }
        if (thisDigit != 0 && thatDigit == 0) {
          // Divide anything except zero by zero -> infinity
          this.data.add((byte) -(this.length = 1));
        }
        if (thisDigit == -1 && thatDigit > -1) {
          // Divide infinity by anything except infinity -> infinity
          this.data.add((byte) -(this.length = 1));
        }
        if (thisDigit == 0 && thatDigit > 0) {
          // Divide zero by anything except zero -> zero
          this.data.add((byte) ((this.length = 1) - 1));
        }
        return new BigNum(0); // return zero
      }
      if (number.length == 1) {
        // Call division by a digit
        byte remainder = this.divideByDigit(thatDigit);
        return new BigNum(remainder);
      }
    }
    int comparison = this.compare(number);
    if (comparison < 0) {
      // Divisor > divident -> quotient = 0, remainder = divisor
      this.data = new ArrayList<Byte>();
      this.data.add((byte) ((this.length = 1) - 1)); // set to zero
      return (number); // return divisor
    }
    if (comparison == 0) {
      // Divisor is equal to the divident ->
      // quitient = 1, remainder = 0
      this.data = new ArrayList<Byte>();
      this.data.add((byte) (this.length = 1)); // set to one
      return new BigNum(0); // return zero
    }
    // At this time divisor is at least 2 digits long (as well as dividend)
    int n = number.length;
    int m = this.length - number.length;
    BigNum divident = this;
    BigNum divisor = new BigNum(number);
    BigNum remainder = new BigNum();
    remainder.radix = this.radix;
    ArrayList<Byte> quotient = new ArrayList<Byte>();
    // D1
    int normalizer = (divident.radix - 1) / divisor.data.get(n - 1);
    divident.multiplyByDigit((byte) normalizer);
    if (divident.length == m + n) { // normalizer == 1
      divident.data.add((byte) 0);
      divident.length ++;
    }
    divisor.multiplyByDigit((byte) normalizer);
    // D2
    int subDivident = 0, subSubDivident = 0;
    int subDivisor =  divisor.data.get(n - 1);
    int subSubDivisor = divisor.data.get(n - 2);
    int testQ, testR;
    for (int j = m; j >= 0; j --) {
      // D3
      subDivident = divident.data.get(j + n) * divident.radix;
      subDivident += divident.data.get(j + n - 1);
      subSubDivident = divident.data.get(j + n - 2);
      testQ = subDivident / subDivisor;
      testR = subDivident % subDivisor;
      if (testQ == divident.radix || (testQ * subSubDivisor
            > testR * divident.radix + subSubDivident)) {
        do {
          testQ --;
          testR += subDivisor;
        } while (testR < divident.radix);
      }
      // D4 (avoid D6)
      BigNum testDivident = new BigNum();
      List<Byte> dividentSubList = divident.data.subList(j, j + n + 1);
      testDivident.data = new ArrayList<Byte>(dividentSubList);
      testDivident.length = testDivident.data.size();
      BigNum testDivisor = new BigNum(divisor);
      testDivisor.multiplyByDigit((byte) testQ);
      int carry = 0, total = 0, product = 0;
      if (testDivident.compare(testDivisor) < 0) testQ --; // -D6
      for (int i = 0; i < n; i ++) {
        product = divisor.data.get(i) * testQ;
        total = divident.radix + divident.data.get(j + i) + carry;
        total -= product % divident.radix;
        divident.data.set(j + i,
            (byte) Math.abs(total % divident.radix));
        carry = total / divident.radix - 1 - (product / divident.radix);
      }
      divident.data.set(j + n, (byte) Math.abs(carry));
      // D5
      quotient.add(0, (byte) testQ);
    } // D7
    // D8
    remainder.data = new ArrayList<Byte>(this.data.subList(0, n));
    remainder.length = remainder.data.size();
    remainder.divideByDigit((byte) normalizer);
    this.data = quotient;
    this.length = quotient.size();
    return (remainder);
  }

  public int length() {
    // Show the length of this big number
    return this.length;
  }

  /* Protected methods */

  // Comparison method
  protected int compare(BigNum number) {
    if (this.length > number.length)
      return 1; // greater by digit
    else if (this.length < number.length) {
      return -1; // lesser by digit
    } else { // equal by digit
      byte internal = 0, external = 0;
      for (int index = this.length - 1; index >= 0; index --) {
        internal = this.data.get(index);
        external = number.data.get(index);
        if (internal > external) return 1; // greater by value
        else if (internal < external) return -1; // lesser by value
      }
      return 0; // equal by value
    }
  }

  protected void multiplyByDigit(byte multiplier) {
    int carry = 0;
    for (int i = 0; i < this.length || carry > 0; i ++) {
      if (i >= this.length) this.data.add((byte) 0);
      carry += this.data.get(i) * multiplier;
      this.data.set(i, (byte) (carry % this.radix));
      carry /= this.radix;
    }
    this.length = this.data.size();
    while (this.length > 1 && this.data.get(this.length - 1) <= 0) {
      this.data.remove(-- this.length);
    }
  }

  protected byte divideByDigit(byte divisor) {
    int quotient = 0;
    int remainder = 0;
    for (int j = this.length - 1; j >= 0; j --) {
      quotient = this.radix * remainder + this.data.get(j);
      remainder = quotient % divisor;
      quotient /= divisor;
      this.data.set(j, (byte) quotient);
    }
    while (this.length > 1 && this.data.get(this.length - 1) == 0) {
      this.data.remove(-- this.length);
    }
    return ((byte) remainder);
  }

  // Increase this by 1
  protected void increment() {
    int index = 0;
    int carry = 1; // actually increment
    do {
      if (index < this.length) {
        carry += this.data.get(index);
        this.data.set(index ++, (byte) (carry % this.radix));
      } else {
        this.data.add((byte) 1); // always 1!
        this.length ++;
      }
      carry /= this.radix; // transfer increment
    } while (carry > 0);
  }

  /* Overrided methods */

  @Override
  public String toString() {
    // Override standard toString for string representation
    // of the value of the class
    if (this.data == null || this.data.size() <= 0) return "null";
    if (this.data.get(0) < 0) return "infinity";
    StringBuilder output = new StringBuilder("");
    byte digit = 0;
    for (int position = this.length; position > 0; position --) {
      // Append fron the end of the array list
      digit = this.data.get(position - 1);
      if (digit < 0) {
        output.append('-');
        digit = (byte) Math.abs(digit);
      }
      output.append(this.RADIX_SYMBOL.charAt(digit));
    }
    return output.toString();
  }

}

/* vim: set si et ts=2 sw=2: */
