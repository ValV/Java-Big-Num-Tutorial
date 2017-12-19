/*
 * @(#)TestRunner.java        1.1 17/11/17
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

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * TestRunner class provides JUnit (4.0 and higher) test case runner.
 *
 * @version 1.1 17 Nov 2017
 * @author Vladimir Valeyev
 */
public class TestRunner {
  public static void main(String[] args) {
    Result result = JUnitCore.runClasses(BigNumTest.class, BigSigNumTest.class);

    for (Failure failure : result.getFailures()) {
      System.console().writer().println(failure.toString());
    }

    System.console().writer().println(result.wasSuccessful());
  }
}

/* vim: set si et ts=2 sw=2 : */
