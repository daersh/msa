package com.ohgiraffers.userservice.service;

import com.ohgiraffers.userservice.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

public interface UserService extends UserDetailsService {
    //타입 은닉 기술 - PSA
    void registUser(UserDTO userDTO);

}
