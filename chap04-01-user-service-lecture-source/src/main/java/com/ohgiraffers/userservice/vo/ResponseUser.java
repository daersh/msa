package com.ohgiraffers.userservice.vo;

import lombok.Data;

@Data
public class ResponseUser {
    private String name; //이름
    private String email;// 이메일
    private String userId;//사용자 id(회원번호아님)

    /*FeignClient 이후 (다른 도메인 서버와 통신해서 값을 가져온 이후 추가할 것)*/
//    private List<ResponseOrder> order;

}
