package com.ohgiraffers.userservice.client;

import com.ohgiraffers.userservice.vo.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


/**
 * <h1>FeignClient</h1>
 * 마이크로 서비스의 이름(유레카 서버에 뜨는 이름이자 spring.application.name 에 해당하는 이름)으로 통신하는 방식이다.<br>
 * 어노텡이션의 url은 gateway의 주소를 쓰고, name은 마이크로 서비스의 이름을 사용한다.
 * */
@FeignClient(name = "swcamp-order-service", url = "localhost:8000") // gateway에 갈 클라이언트의 이름, gateway 주소
public interface OrderServiceClient {

    // gateway가 알고 있는 마이크로 서비스의 접두사(라우팅 시 설정한 요청 경로)를 추가하여 요청 경로를 작성한다.
    @GetMapping("/order-service/orders/users/{userId}")
    List<ResponseOrder> getUserOrders(@PathVariable String userId);

}
