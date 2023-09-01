package com.intro.junit.writing.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*; //이 패키지 안에 assertTrue, assertEquals관련한 메소드들이 다 존재한다.

@DisplayName("applied to class")
public class MyFirstJUnitJupiterTests {

    private final Calculator calculator = new Calculator();

    private final Person person = new Person("Jane", "Doe");

    @Test
    @Tag("calculator")
    @DisplayName("StandardAssertions") //display name
    void standardAssertions(){
        assertEquals(2, calculator.add(1,1));

        //failure일때 message를 따로 표시한다 맨 오른쪽에 "The optional failure message is now the last parameter"
        assertEquals(4, calculator.multiply(2,2), "your expected num is not equal to the result of calculator");

        //assert True (구현 클래스)
        assertTrue(3 < 5, () -> "your condition is false");

    }

    @Test
    @DisplayName("GroupedAssertions")
    void groupedAssertions() {
        assertAll("person",
                //each argument's type is Executable class which consists of FunctionalInterface
                () -> assertEquals("Jane", person.getFirstName()),
                () -> assertEquals("Doe", person.getLastName())
                );
    }

    @Test
    @DisplayName("╯°□°）╯")
    void testWithDisplayNameContainingSpecialCharacters() {
    }
}

class Calculator{
    public int add(int a, int b){
        return a+b;
    }

    public int multiply(int a, int b){
        return a*b;
    }

    public long divide(int a, int b){
        return (long) a/b;
    }
}

class Person{
    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }
}
