package com.intro.junit.writing.test.annotation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

@Slf4j
//@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class TestLifecyclePerMethod {

    private static String contents;

    @BeforeAll
    static void beforeAllTests(){
        //It will operate before all tests (It could be applied to connection things)
        //If you use the default TestLifeCycle, you must set this method as static method. not to be gotten rid of settings at here.
        log.info("Before all tests");
        contents = "before settings";
    }

    @AfterAll
    static void afterAllTests(){
        //It will operate after all tests (It could be applied ...)
        log.info("After all tests");
        System.out.println(contents);
    }

    @BeforeEach
    void beforeEachTest(){
        log.info("Before each tests");
    }

    @AfterEach
    void afterEachTest(){
        log.info("After each tests");
    }

    @Test
    void test1(){
        log.info("test1");
        System.out.println(Thread.currentThread());
    }

    @Test
    void test2(){
        log.info("test2");
        System.out.println(Thread.currentThread());
    }



}
