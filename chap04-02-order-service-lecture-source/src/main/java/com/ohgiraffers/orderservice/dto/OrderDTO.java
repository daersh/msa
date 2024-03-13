package com.ohgiraffers.orderservice.dto;

import com.ohgiraffers.orderservice.aggregate.OrderMenu;
import lombok.Data;
import org.springframework.core.annotation.Order;

import java.util.List;

@Order
@Data
public class OrderDTO {
    private int orderCode;
    private int userId;
    private String orderDate;
    private String orderTime;
    private int totalOrderPrice;
    private List<OrderMenuDTO> orderMenus;
}
