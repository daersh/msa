package com.ohgiraffers.userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurity{

    /*목차 1. 인가(Authorization) 메서드 */

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // JWT 로그인 처리할 것이므로 csrf는 신경쓸 필요 없다.
        http.csrf((csrf)->csrf.disable());
        http.authorizeHttpRequests((auth)-> auth.requestMatchers(new AntPathRequestMatcher("/users/**")).permitAll());
        return http.build();
    }

    /*목차 2. 인증(Authentication) 메서드 */

}
