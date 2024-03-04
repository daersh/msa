package com.ohgiraffers.firstservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FirstServiceController {


    @GetMapping("health-check")
    public String healthCheck(){
        return "I'm OK....!";
    }
    @GetMapping("message")
    public String message(@RequestHeader("first-request")String header){
        return "First Service Message";
    }


}