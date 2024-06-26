package com.ohgiraffers.gateway.filter;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Iterator;
import java.util.Set;

@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    Environment env;

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    public static class Config{


    }
    // 토큰을 Authorization 키 값으로 가지고 오는지 판별, 해당 토큰이 유효한지 반펼
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) ->
        {
            /*ServerHttpRequest는 HttpServletRequest와 달리 스프링의 WebFlux기술을 활용(비동기 통신)하기  위한 request*/
            ServerHttpRequest request= exchange.getRequest();

            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
            {
                return onError(exchange,"Authorization 헤더 부재", HttpStatus.UNAUTHORIZED);
            }
            HttpHeaders headers = request.getHeaders();
            Set<String> keys = headers.keySet();
            Iterator<String>iter = keys.iterator();
            while (iter.hasNext())
            {
                log.info(iter.next());
            }
            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer","");
            if(!isJwtValid(jwt))
            {
                return onError(exchange,"토큰 유효하지 않음",HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
            });
    }


    private boolean isJwtValid(String jwt) {
        //유효한 토큰인지 확인 후 참 또는 거짓 반환

        boolean flag= true;
        String subject = null;
        try {
            subject = Jwts.parser().setSigningKey(env.getProperty("token.secret")).parseClaimsJws(jwt).getBody().getSubject();
        }catch (Exception e){
            e.printStackTrace();
            flag=false;
        }
        if(subject==null || subject.isEmpty())
        {
            flag=false;
        }

        return flag;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
}
