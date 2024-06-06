package com.intro.junit.writing.test.disable;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Slf4j
public class DisabledDemo {
    //if we set disable annotation on the test units
    @Disabled("Disabled until but #42 has been resolved")
    @Test
    void testWillBeSkipped(){
        log.info("unit test is skipped");
    }

    @Test
    void testWillBeExecuted(){
        log.info("unit test is executed");
    }
}

@Slf4j
@Disabled("Disabled until bug #99 has been fixed")
class DisabledTestDemo{
    //if we set disable annotation on the class
    @Test
    void testWillBeSkipped(){
        log.info("class test is skipped");
    }
}
