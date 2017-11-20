# Java BigNum Tutorial

This project implements arithmetic for arbitrary-precision numbers. Of course, Java already has BigInteger class for that purpose. But it's a good starting point for learning Java mechanics and tricks.

At the beginning this project showed how to create a class, fields, and methods. Further inhertance has been added (derivative from *BigNum* class *BigSNum* for signed arithmetic), then polymorphism (different types of constuctors and methods with different signatures). *BigNum* class incapsulates most of its members, publicly visible members are:

- Constructors:
  - *BigNum()*
  - *BigNum(int number)*
  - *BigNum(int number, byte radix)*
  - *BigNum(String number)*
  - *BigNum(String number, byte radix)*
  - *BigNum(BigNum number)*
- Arithmetic methods:
  - *void add(BigNum number)*
  - *void subtract(BigNum number)*
  - *void multiply(BigNum number)*
  - *BigNum divide(BigNum number)*
- Auxiliary methods:
  - *int length()* (so-called getter method, 'cause class field *length* is protected, thus can be accessed only in child classes)
  - *String toString()* (overrided method that every object should have in order to be converted into string)

Well, perhaps not most of the members are incapsulated, but many... *BigSNum* class has almost the same members:

- Constructors:
  - *BigSNum()*
  - *BigSNum(int number)*
  - *BigSNum(int number, byte radix)*
  - *BigSNum(String number)*
  - *BigSNum(String number, byte radix)*
  - *BigSNum(BigNum number)*
  - *BigSNum(BigSNum number)*
- Arithmetic methods:
  - *void add(BigNum number)*
  - *void add(BigSNum number)*
  - *void subtract(BigNum number)*
  - *void subtract(BigSNum number)*
  - *void multiply(BigNum number)*
  - *void multiply(BigSNum number)*
  - *BigNum divide(BigNum number)*
  - *BigNum divide(BigSNum number)*
- Auxiliary methods:
  - *String toString()*

These duplicated methods show polymorphism phenomenon, where methods with the same name can be fed with different types of arguments.
Ok, that is *TODO* to consider more further...

The project also includes two interesting technologies such as *Maven* and *JUnit*. First is a build system, the second - is a unit testing framework.

For *Maven* project directory tree matters (**convention over configuration** design paradigm). It looks like this:

```
-.
 +-src
 | +-main
 | | +-java
 | | | +-com
 | | |   +-github
 | | |     +-valv
 | | |       +-*.java
 | | +-resources
 | +-test
 |   +-java
 |   | +-*Test.java
 |   +-resources
 +-target
 +-pom.xml
```

It's convenient indeed, 'cause the only configuration file *pom.xml* has quite simpple and comprehensive configuration. With *Maven* installed in the system, project is built with command:

```
$ mvn package

```

Or compiled and tested with:

```
$ mvn test

```
*Maven* works with *JUnit* out-of-the-box.

*JUnit* automates and conventionalize unit testing process. *JUnit* tests - are just classes with collection of testing methods (with *@Test* annotation), and the need master class with *main()* in it. Such is the *TestRunner.java* for this project, unit tests are called after classes they are testing with suffix *Test in the filename and the name of the class.

###### TODO

- BigNum class
  - [x] Check and fix methods
  - [ ] Fix formatting and refactor ([Google Java Style Guide](https://google.github.io/styleguide/javaguide.html))
  - [ ] Optimize number storage (try to use 32 bit integer)
  - [ ] Implement comparison and arithmetics with different radix numbers
- BigSNum class
  - [ ] Implement absent methods
  - [ ] Check and fix methods
  - [ ] Fix formatting and refactor
- Testing
  - [x] Test unit for BigNum class
  - [ ] Test unit for BigSNum class
- Project
  - [ ] Remove legacy build system (bash scripts)
  - [ ] Add JavaDoc

vim: si et ts=2 sw=2:
