package com.intro.junit.mockito.test;

import com.intro.junit.testcode.first_service.utility.GoodUtility;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MockitoIntro {

    //Stubbing & verfiy test
    @Test
    public void mockTest(){

        List mockedList = mock(List.class);
        List mockedList2 = mock(List.class);

        //stubbing here using by when & then methods
        Mockito.when(mockedList.get(0)).thenReturn("first");
        Mockito.when(mockedList.get(1)).thenThrow(new RuntimeException());

        //following prints "first"
        System.out.println(mockedList.get(0));

        //following throws runtime exception
        //System.out.println(mockedList.get(1));

        //I was wondering how it save above data in mockedList structure (want to know principle of it)

        //following prints "null". because get(100) was not stubbed anywhere
        System.out.println(mockedList.get(100));

        Mockito.verify(mockedList).get(0); //basically, verify method check how many mock object was called? (default is one time call)

    }

    @Test
    public void verfiyTest(){
        System.out.println(anyInt());
    }

    @Test
    public void staticMocktio(){
        /*static method is belong to class, not in instance. so Mockito library need to make something like proxy class with dynamic proxy technique
        that is mockStatic()*/

        MockedStatic<GoodUtility> staticMocked = mockStatic(GoodUtility.class);

        String[] result = new String[] {"a", "b", "c"};
        when(GoodUtility.parsingMethod("a b c")).thenReturn(result);

        assertEquals(result, GoodUtility.parsingMethod("a b c"));

        /**
         * The used MockMaker SubclassByteBuddyMockMaker does not support the creation of static mocks
         * If you do not apply dependency of mockito-inline, you will meet above error message
         */
    }


}
