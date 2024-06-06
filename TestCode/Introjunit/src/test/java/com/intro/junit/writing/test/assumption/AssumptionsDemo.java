package com.intro.junit.writing.test.assumption;

import com.intro.junit.writing.test.util.Calculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

public class AssumptionsDemo {

    private final Calculator calculator = new Calculator();

    @Test
    void testOnlyOnCiServer(){
        assumeTrue("thlee".equals(System.getenv("USER")));
        // remainder of test
    }

    //basically, we used to use this assumeTrue function as distinguish the environments such as Servers (DEV, PRD)
    @Test
    void testOnlyOnDeveloperWorkstation(){
        System.out.println(System.getenv()); //check of what environments have
        assumeTrue("thlee".equals(System.getenv("USER")));
    }

    @Test
    void testInAllEnvironments(){
        assumingThat("thlee".equals(System.getenv("USER")),
            ()->{
                //perform these assertions only on the thlee users
                assertEquals(2, calculator.divide(4,2));
            }
        );

        //perform these assertions in all environments
        assertEquals(42, calculator.multiply(6,7));
    }

}
