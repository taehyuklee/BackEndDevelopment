package com.intro.junit.testcode;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
class IntrojunitApplicationTests {

    @Mock
    private MockMvc mockMvc; //mockMvc 생성

    @Test
    public void testController() throws Exception{

    }

    @Test
    void contextLoads() {
    }

}
