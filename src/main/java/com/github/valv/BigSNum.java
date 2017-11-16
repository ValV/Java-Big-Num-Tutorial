/*
 * @(#)BigSNum.java        1.0 16/09/15
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
 * <valv at linuxmail dot org>, 15 September 2016
 */

package com.github.valv;

import java.util.ArrayList;
import java.util.List;

/**
 * BigNum class provides basics for signed arbitrary precision arithmetic.
 *
 * @version 1.0 15 Sep 2016
 * @author Vladimir Valeyev
 */

public class BigSNum extends BigNum {
	protected boolean negative = false;

	public BigSNum() {
		super();
	}

	public BigSNum(int number) {
		// Create from int (default base is 10)
		this(number, BigSNum.BASE_DEFAULT);
	}

	public BigSNum(int number, byte base) {
		// Create from int with custom base
		super(number, base);
		if (number < 0) this.negative = true;
	}

	public BigSNum(String number) {
		// Create from String (default base is 10)
		this(number, BigSNum.BASE_DEFAULT);
	}

	public BigSNum(String number, byte base) {
		// Create from String
		super(number, base);
		if (number.charAt(0) == '-') this.negative = true;
	}

	public BigSNum(BigNum number) {
		// Create from another BigNum (copy)
		super(number);
	}

	public BigSNum(BigSNum number) {
		// Create from another BigSNum (copy)
		super(number);
		this.negative = number.negative;
	}

	@Override
	public void add(BigNum number) {
		if (this.base != number.base) return; // discard different bases
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

	public void add(BigSNum number) {
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

	public void subtract(BigSNum number) {
	}

	@Override
	public void multiply(BigNum number) {
	}

	public void multiply(BigSNum number) {
	}

	@Override
	public BigNum divide(BigNum number) {
		return null;
	}

	public BigNum divide(BigSNum number) {
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
