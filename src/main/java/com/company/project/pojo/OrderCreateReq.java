package com.company.project.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * OrderCreateReq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateReq {

    private List<OrderCarItem> cartList;

    private Integer shippingFee;

}
