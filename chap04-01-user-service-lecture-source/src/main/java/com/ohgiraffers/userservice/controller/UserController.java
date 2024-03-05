package com.ohgiraffers.userservice.controller;

import com.ohgiraffers.userservice.dto.UserDTO;
import com.ohgiraffers.userservice.service.UserService;
import com.ohgiraffers.userservice.vo.HelloVO;
import com.ohgiraffers.userservice.vo.RequestUser;
import com.ohgiraffers.userservice.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {
    Environment env;
    final private HelloVO helloVO;
    private ModelMapper modelMapper;
    final private UserService userService;
    @Autowired
    public UserController(Environment env, HelloVO helloVO, ModelMapper modelMapper, UserService userService) {
        this.env = env;
        this.helloVO = helloVO;
        this.modelMapper = modelMapper;
        this.userService=userService;
    }

    /*application.yml 파일로부터 설정 값을 불러오기 위해 두가지 방법이 제공된다.
    * 1. Environments 를 의존성 주입 받아 getProperty로 설정 키 값을 작성하여 불러오기
    * 2. @Value 를 통해 필드로 주입받고 활용하는 방법
    * */

    /* 1. Environments 를 의존성 주입 받아 getProperty로 설정 키 값을 작성하여 불러오기*/
    @GetMapping("/health_check")
    public String status(){
        return "Server at "+env.getProperty("local.server.port")
                + ", swcamp.message: "+ env.getProperty("swcamp.message");

    }
    /*2. @Value 를 통해 필드로 주입받고 활용하는 방법 - (getter)*/
    @GetMapping("/do_msa")
    public String doMsa(){
        return helloVO.getMessage();
    }

    /*회원가입 (post - /users)*/
    @PostMapping("/users")
    public ResponseEntity<ResponseUser> registUser(@RequestBody RequestUser user){
        /*requestUser -> UserDOT*/
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        /*회원가입 비즈니스 로직 시작*/
        userService.registUser(userDTO);
        /*userDTO->ResponseUser*/
        ResponseUser responseUser = modelMapper.map(userDTO,ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }


}
