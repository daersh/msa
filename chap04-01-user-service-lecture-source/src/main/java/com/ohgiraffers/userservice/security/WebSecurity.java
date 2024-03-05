package com.ohgiraffers.userservice.security;

import com.ohgiraffers.userservice.service.UserService;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurity{
    UserService userService;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    Environment env;
    @Autowired
    public WebSecurity(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, Environment env) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.env=env;
    }

    /*목차 1. 인가(Authorization) 메서드 */
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        //login 시 추가할 내용
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        // JWT 로그인 처리할 것이므로 csrf는 신경쓸 필요 없다.
        http.csrf((csrf)->csrf.disable());
        http.authorizeHttpRequests(
                (auth)-> auth.requestMatchers(new AntPathRequestMatcher("/users/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/health_check")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
        ).authenticationManager(authenticationManager);

        http.addFilter(getAuthenticationFilter(authenticationManager));

        return http.build();
    }

    private Filter getAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new AuthenticationFilter(authenticationManager,userService,env);
    }

    /*목차 2. 인증(Authentication) 메서드 */

}
