package com.ohgiraffers.userservice.vo;


import lombok.Data;

import java.util.List;

/**
 * <h1>ResponseOrder</h1>
 * 각 도메인끼리 통신을 하고 조회된 결과가 있다면 이 VO(도메인 별 중간 객체)에 담는다.
 * */
@Data
public class ResponseOrder {
    private String orderDate;
    private String orderTime;
    private List<OrderMenuVO> orderMenus;

}
