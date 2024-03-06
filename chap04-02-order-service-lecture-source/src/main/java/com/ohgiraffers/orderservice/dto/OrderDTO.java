package com.ohgiraffers.orderservice.dto;

import com.ohgiraffers.orderservice.aggregate.OrderMenu;
import org.springframework.core.annotation.Order;

import java.util.List;

@Order
public class OrderDTO {
    private int orderCode;
    private int userId;
    private String orderDate;
    private String orderTime;
    private int totalOrderPrice;
    private List<OrderMenu> orderMenus;
}
