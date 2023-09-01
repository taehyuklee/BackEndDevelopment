package com.intro.junit.writing.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*; //이 패키지 안에 assertTrue, assertEquals관련한 메소드들이 다 존재한다.

@DisplayName("applied to class")
public class MyFirstJUnitJupiterTests {

    private final Calculator calculator = new Calculator();

    private final Person person = new Person("Jane", "Doe");

    private static String greeting() {
        return "Hello, World!";
    }

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

    @Test //Test all targets which need to be tested.
    @DisplayName("GroupedAssertions")
    void groupedAssertions() {
        assertAll("person",
                //each argument's type is Executable class which consists of FunctionalInterface
                () -> assertEquals("Jane", person.getFirstName()),
                () -> assertEquals("Doe", person.getLastName())
                );
    }


    @Test
    void dependentAssertions(){
        assertAll("properties",

            //first implementation of function (call back function)
            () -> {
                String firstName = person.getFirstName();
                assertNotNull(firstName); //check whether the variable is not null or not

                //Executed only if the previous assertion is valid.
                assertAll("first name",
                        () -> assertTrue(firstName.startsWith("J")),
                        () -> assertTrue(firstName.endsWith("e"))
                );
            },

            //second implementation of function (call back function)
            () -> {
                String lastName = person.getLastName();
                assertNotNull(lastName);

                //Executed only if the previous assertion is valid.
                assertAll("last name",
                        () -> assertTrue(lastName.startsWith("D")),
                        () -> assertTrue(lastName.endsWith("e"))
                );

            }
        );
    }


    @Test
    void exceptionTesting(){
        //we have to check the exception type that we expect.
        Exception exception = assertThrows(ArithmeticException.class, () -> calculator.divide(1,0));

        assertEquals("/ by zero", exception.getMessage()); //check the exception whether I expected or not.
    }


    @Test
    void timeoutNotExceeded(){
        //The following assertion succeeds.
        assertTimeout(Duration.ofMinutes(2), ()->{
            // Perform this block task that takes less than 2 mins
        });
    }

    @Test
    void timeoutNotExceededWithResult(){
        // The following assertion succeeds, and returns the supplied object.
        String actualResult = assertTimeout(Duration.ofMinutes(2), ()->{
            return "a result";
        });

        //check the result
        assertEquals("a result", actualResult);
    }

    @Test
    void timeoutExceeded(){
        // The following assertion invokes a method reference and returns an object
        String actualGreeting = assertTimeout(Duration.ofMinutes(2), MyFirstJUnitJupiterTests::greeting);
        assertEquals("Hello, World!", actualGreeting);
    }

    @Test
    void timeoutExceededWithPreemptiveTermination(){

        //this method check whether the inner executable code block takes over 100 ms or not
        //The following assertion fails with an error message similar to:
        //execution timed out after 10 ms
        assertTimeoutPreemptively(Duration.ofMillis(100), ()->{
            //Simulate task that takes more than 10 ms. if a task takes over 10 ms, the other task will process
            System.out.println("move on the next process");
            Thread.sleep(400);
            //new CountDownLatch(1).await();
        });
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
