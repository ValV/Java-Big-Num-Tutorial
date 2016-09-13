/*
 * @(#)BigNum.java        1.0 16/09/14
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

package org.linuxmail.valv;

import java.util.ArrayList;
import java.util.List;

/**
 * BigNum class provides basics for arbitrary precision arithmetic.
 *
 * @version 1.0 14 Sep 2016
 * @author Vladimir Valeyev
 */

public class BigNum {
	/* This class implements unsigned integer base arithmetic */
	static final byte BASE_MAX = 36;
	static final byte BASE_DEFAULT = 10;
	static final byte BASE_MIN = 2;
	static final String BASE_SYMBOL = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	protected byte base = BASE_DEFAULT;
	protected int length = 0;
	protected ArrayList<Byte> data;

	/* Constructors */

	public BigNum() {
		// Default constructor
		this.data = new ArrayList<Byte>(this.length);
	}

	public BigNum(int number) {
		// Create from int (default base is 10)
		this(number, BigNum.BASE_DEFAULT);
	}

	public BigNum(int number, byte base) {
		// Create from int with custom base
		base = (base > this.BASE_MAX) ? this.BASE_MAX : base; // trim base
		if (number < 0) number = -number; // only positive
		this.base = base;
		this.data = new ArrayList<Byte>(this.length);
		do {
			// Add digits to the array
			this.data.add((byte) (number % base));
			number /= base;
		} while (number > 0);
		this.length = this.data.size();
	}

	public BigNum(String number) {
		// Create from String (default base is 10)
		this(number, BigNum.BASE_DEFAULT);
	}

	public BigNum(String number, byte base) {
		// Create from String
		base = (base > this.BASE_MAX) ? this.BASE_MAX : base; // trim base
		number = number.toUpperCase();
		this.base = base;
		this.data = new ArrayList<Byte>(this.length);
		int digit = 0;
		for (int position = number.length() - 1; position >= 0; position --) {
			// Add digit's value to the array
			digit =  this.BASE_SYMBOL.indexOf(number.charAt(position));
			if (digit >= 0) {
				this.data.add((byte) digit);
			}
		}
		this.length = this.data.size();
	}

	public BigNum(BigNum number) {
		// Create from another BigNum (copy)
		this.base = number.base;
		this.length = number.length;
		this.data = new ArrayList<Byte>(this.length);
		for (byte digit: number.data) { // deep copy
			this.data.add(digit);
		}
	}

	/* Public methods */

	public void add(BigNum number) {
		// Addition method
		if (this.length < 1 || number.length < 1) return;
		if (number.length == 1 && number.data.get(0) <= 0) return;
		int index = 0, carry = 0;
		if (this.compare(number) > 0) { // 'this' > 'number'
			for (; index < number.length; index ++) {
				carry += number.data.get(index);
				carry += this.data.get(index);
				this.data.set(index, (byte) (carry % this.base));
				carry /= this.base;
			} // sum carry with both arrays' values
			while (carry > 0) {
				if (index < this.length) { // in the array bounds
					carry += this.data.get(index);
					this.data.set(index ++, (byte) (carry % this.base));
				} else { // out of the array bounds
					this.data.add((byte) (carry % this.base));
					this.length ++;
				}
				carry /= this.base;
			} // sum carry with the largest ('this') array's values
		} else { // 'this' <= 'number'
			for (; index < this.length; index ++) {
				carry += this.data.get(index);
				carry += number.data.get(index);
				this.data.set(index, (byte) (carry % this.base));
				carry /= this.base;
			} // sum carry with both arrays' values
			while (carry > 0 || this.length < number.length) {
				if (this.length < number.length) { // in the array bounds
					carry += number.data.get(this.length);
				}
				this.data.add((byte) (carry % this.base));
				this.length ++;
				carry /= this.base;
			} // sum carry with the largest ('number') array's values
		}
	}

	public void subtract(BigNum number) {
		// Subtraction method
		if (this.length < 1 || number.length < 1) return;
		if (number.length == 1 && number.data.get(0) <= 0) return;
		if (this.compare(number) <= 0) {
			// 'number' is equal or more so result is zero
			this.data = new ArrayList<Byte>(1);
			this.data.add((byte) ((this.length = 1) - 1));
			return;
		}
		int index = 0, carry = 0;
		for (; index < number.length; index ++) {
			carry += this.data.get(index) + this.base; // push radix
			carry -= number.data.get(index);
			this.data.set(index, (byte) (carry % this.base));
			carry /= this.base;
			carry -= 1; // pop radix
		} // carry with both arrays' values
		if (carry < 0) { // subtract the last carry (if any)
			carry += this.data.get(index);
			this.data.set(index, (byte) carry);
		}
		while (this.length > 1 && this.data.get(this.length - 1) <= 0) {
			this.data.remove(-- this.length);
		} // remove leading zeroes
		this.data.trimToSize(); // release unused memory
	}

	public void multiply(BigNum number) {
		// Multiplication method
		if (this.length < 1 || number.length < 1) return;
		if (number.length == 1) {
			if (number.data.get(0) <= 0) {
				// Zero is returned when 'this' is multiplied by
				// zero or infinity ('cause there may be the only one
				// infinity in the universe)
				this.data = new ArrayList<Byte>(1);
				this.data.set(0, (byte) 0);
				return;
			} else {
				// Multiply by one-digit number
				this.multiplyByDigit(number.data.get(0));
				return;
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
			product.add((byte) (total % this.base));
			total /= this.base;
		}
		product.add((byte) (total % this.base));
		while (ip > 0 && product.get(ip) <= 0) {
			product.remove(ip --);
		} // remove leading zeroes
		product.trimToSize(); // release unused memory
		this.data = product;
		this.length = this.data.size();
	}

	public BigNum divide(BigNum number) {
		// Division method
		// returns: remainder; quotient is stored as 'this' data
		if (this.length < 1 || number.length < 1) return (number);
		if (number.length == 1) {
			if (number.data.get(0) <= 0) {
			// Division by zero (or infinity - doesn't matter)
			// causes infinity
			this.data = new ArrayList<Byte>(1);
			this.data.add((byte) -(this.length = 1)); // set to infinity
			return (number); // return divisor (zero)
			} else {
				// Call division by a digit
				byte remainder = this.divideByDigit(number.data.get(0));
				return (new BigNum(remainder));
			}
		}
		int comparison = this.compare(number);
		if (comparison < 0) {
			// Divisor is more than the divident ->
			// quotient = 0, remainder = divisor
			this.data = new ArrayList<Byte>(1);
			this.data.add((byte) ((this.length = 1) - 1)); // set to zero
			return (number); // return divisor
		}
		if (comparison == 0) {
			// Divisor is equal to the divident ->
			// quitient = 1, remainder = 0
			this.data = new ArrayList<Byte>(1);
			this.data.add((byte) (this.length = 1)); // set to one
			return (new BigNum(0)); // return zero
		}
		// At this time 'number' is at least 2 digits long
		// (as well as 'this' is)
		int n = number.length;
		int m = this.length - number.length;
		BigNum divident = this;
		BigNum divisor = new BigNum(number);
		BigNum remainder = new BigNum();
		remainder.base = this.base;
		ArrayList<Byte> quotient = new ArrayList<Byte>(0);
		// D1
		int normalizer = (divident.base - 1) / divisor.data.get(n - 1);
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
			subDivident = divident.data.get(j + n) * divident.base;
			subDivident += divident.data.get(j + n - 1);
			subSubDivident = divident.data.get(j + n - 2);
			testQ = subDivident / subDivisor;
			testR = subDivident % subDivisor;
			if (testQ == divident.base || (testQ * subSubDivisor
						> testR * divident.base + subSubDivident)) {
				do {
					testQ --;
					testR += subDivisor;
				} while (testR < divident.base);
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
				total = divident.base + divident.data.get(j + i) + carry;
				total -= product % divident.base;
				divident.data.set(j + i,
						(byte) Math.abs(total % divident.base));
				carry = total / divident.base - 1 - (product / divident.base);
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

	protected int compare(BigNum number) {
		// Comparison method
		if (this.length > number.length)
			return 1; // more by digit
		else if (this.length < number.length) {
			return -1; // less by digit
		} else { // equal by digit
			byte internal = 0, external = 0;
			for (int index = this.length - 1; index >= 0; index --) {
				internal = this.data.get(index);
				external = number.data.get(index);
				if (internal > external) return 1; // more by value
				else if (internal < external) return -1; // less by value
			}
			return 0; // equal by value
		}
	}

	protected void multiplyByDigit(byte multiplier) {
		int carry = 0;
		for (int i = 0; i < this.length || carry > 0; i ++) {
			if (i >= this.length) this.data.add((byte) 0);
			carry += this.data.get(i) * multiplier;
			this.data.set(i, (byte) (carry % this.base));
			carry /= this.base;
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
			quotient = this.base * remainder + this.data.get(j);
			remainder = quotient % divisor;
			quotient /= divisor;
			this.data.set(j, (byte) quotient);
		}
		while (this.length > 1 && this.data.get(this.length - 1) == 0) {
			this.data.remove(-- this.length);
		}
		return ((byte) remainder);
	}

	protected void increment() {
		// Increase this by 1
		int index = 0;
		int carry = 1; // actually increment
		do {
			if (index < this.length) {
				carry += this.data.get(index);
				this.data.set(index ++, (byte) (carry % this.base));
			} else {
				this.data.add((byte) 1); // always 1!
				this.length ++;
			}
			carry /= this.base; // transfer increment
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
			output.append(this.BASE_SYMBOL.charAt(digit));
		}
		return output.toString();
	}

}
