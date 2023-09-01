package com.intro.junit.writing.test.thirdparty;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat; //gradle build할때 third party matcher들에 대한건 같이 딸려 온다.

import org.junit.jupiter.api.Test;

public class HamcrestAssertionsDemo {

    private final Calculator calculator = new Calculator();

    @Test
    void assertWithHamcrestMatcher(){
        assertThat(calculator.subtract(4,1), is(equalTo(3)));
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

    public int subtract(int a, int b){
        return a - b;
    }
}