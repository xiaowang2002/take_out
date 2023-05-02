package com.atwzs.reggie.dto;

import com.atwzs.reggie.domain.OrderDetail;
import com.atwzs.reggie.domain.Orders;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;

    private int sumNum;
}
