package com.intro.junit.writing.test.thirdparty;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat; //gradle build할때 third party matcher들에 대한건 같이 딸려 온다.

import com.intro.junit.writing.test.util.Calculator;
import org.junit.jupiter.api.Test;

public class HamcrestAssertionsDemo {

    private final Calculator calculator = new Calculator();

    @Test
    void assertWithHamcrestMatcher(){
        assertThat(calculator.subtract(4,1), is(equalTo(3)));
    }
}
