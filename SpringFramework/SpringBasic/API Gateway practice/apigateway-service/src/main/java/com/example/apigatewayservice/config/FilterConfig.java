package com.example.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration //annotation빼주면 spring에서 인식할수가 없다.
public class FilterConfig {
    //@Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        return builder.routes() //그냥 반환해주는 것이 아니라 추가시키고자 하는 route정보를 넣어서 줘야한다.
            .route(r -> r.path("/first-service/**").filters(f -> f.addRequestHeader("first-request","first-request-header")
                                                                    .addResponseHeader("first-response","first-response-header"))
                    .uri("http://localhost:8081")) //addRequestHeader란 것은 옆의 ""안에 있는 것을 header이름으로 붙여라! 라고 하는 것을 요구한 것이다.
            .route(r -> r.path("/second-service/**").filters(f -> f.addRequestHeader("second-request","second-request-header")
                                        .addResponseHeader("second-response","second-response-header"))
                    .uri("http://localhost:8082")).build();
        /*error잡은 부분 path에서 /first-service/**로 해야하는데 first-service/**로 햇었다.*/
        //r이라는 값이 전달되면 패스 확인하고 filter적용해서 uri로 전달해준다.
        //filter는 크게 request filter와 response filter 두 가지를 등록할수 있다.
        //마지막에 build해줌으로써 Route에 필요했던
        //내용들을 실제 메모리에 적용시켜주게 된다.

    }
}
