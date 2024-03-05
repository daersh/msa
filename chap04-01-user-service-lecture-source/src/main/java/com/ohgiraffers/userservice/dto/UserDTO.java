package com.ohgiraffers.userservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private String name;
    private String email;
    private String pwd;
    private String userId;
    private Date enrollDate;
}
