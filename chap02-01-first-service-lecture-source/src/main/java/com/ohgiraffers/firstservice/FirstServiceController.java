package com.ohgiraffers.firstservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j

//@RequestMapping("/first-service")     /*Gateway 추가 후 Routes 경로 추가 후 작성한 부분*/
@RequestMapping("/")            /*Gateway에서 RewritePath 필터 추가 후 뒤에 segment만 넘어오게 한 이후 */
public class FirstServiceController {
    private Environment env;        //@Value와 같이 application.yml 또는 환경 설정 값을 불러오기 위함!!@!@!

    @Autowired
    public FirstServiceController(Environment env) {
        this.env = env;
    }

    @GetMapping("health-check")
    public String healthCheck(){
//        log.info("요청 경로 확인: {}",env.getProperty("local.server.port"));
//        log.info("요청 경로 확인: {}",env.getProperty("server.port"));
        /*first-service application 여러 개 실행시(스케일 아웃) RR 방식으로 돌아가는 것을 포트 번호를 통해 확인할 수 있다.*/
        /*Gateway의 lb(로드벨런싱)를 통해 RR 방식으로 스위칭 된다!!!!!*/
        return "I'm OK....!\n port:"+env.getProperty("local.server.port");
    }
    /*gateway를 거치고 넘어오는 요청 시 RequestHeader에 값이 추가 됨(Gateway의 필터에 의해)*/
    @GetMapping("message")
    public String message(@RequestHeader("first-request")String header){
        log.info("넘어온 헤더값: {}",header);
        return "First Service Message";
    }


}
