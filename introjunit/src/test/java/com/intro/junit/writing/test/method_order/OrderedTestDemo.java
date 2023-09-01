package com.intro.junit.writing.test.method_order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderedTestDemo {

    @Test
    @Order(3)
    void thirdExecution(){
        log.info("third Execution");
    }

    @Test
    @Order(2)
    void secondExecution(){
        log.info("second Execution");
    }


    @Test
    @Order(1)
    void firstExecution(){
        log.info("first Execution");
    }
}
