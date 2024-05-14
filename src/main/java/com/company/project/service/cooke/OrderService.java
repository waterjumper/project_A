package com.company.project.service.cooke;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.entity.cooke.Order;
import com.company.project.pojo.OrderCreateReq;
import com.company.project.pojo.OrderCreateResp;
import com.company.project.pojo.OrderQueryReq;


/**
 * OrderService
 */
public interface OrderService extends IService<Order> {

    IPage<Order> pageList(OrderQueryReq vo);


    OrderCreateResp createOrder(OrderCreateReq vo);
}
