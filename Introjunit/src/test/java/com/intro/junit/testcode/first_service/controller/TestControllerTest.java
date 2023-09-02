package com.intro.junit.testcode.first_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import lombok.RequiredArgsConstructor;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient //WebTestClient 인터페이스를 사용할수 있게 해주는 annotation
@RequiredArgsConstructor
class TestControllerTest {

    //WebClient는 웹 요청을 수행하기 위한 기본 (blocking방식 - restTemplate, non-blocking방식 - webClient)
    private WebTestClient webTestClient;

    @Mock
    private TestController testController;

    @BeforeEach //test이전에 실행하기 위한 annotation (연동 등 여기서 사용된다)
    public void beforeEach(){
        System.out.println("안녕" + this.webTestClient);
        System.out.println(testController);

        //여기서는 webTestClient객체
        this.webTestClient = WebTestClient.bindToController(this.testController).build();
    }

    @Test
    void mockTest() {
    }
}