package com.ohgiraffers.userservice.service;

import com.ohgiraffers.userservice.dto.UserDTO;
import org.springframework.stereotype.Service;

public interface UserService {
    //타입 은닉 기술 - PSA
    void registUser(UserDTO userDTO);

}
