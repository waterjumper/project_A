package com.company.project.controller;


import com.company.project.common.utils.DataResult;
import com.company.project.pojo.OrderQueryReq;
import com.company.project.service.cooke.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * SysOrderController
 */
@RestController
@Api(tags = "SysOrderController")
@RequestMapping("/sys/order")
@Slf4j
public class SysOrderController {


    @Resource
    private OrderService orderService;


    @PostMapping("/pageList")
    @ApiOperation(value = "分页列表接口")
    // @RequiresPermissions("sys:user:list")
    public DataResult pageList(@RequestBody OrderQueryReq vo) {
        return DataResult.success(orderService.pageList(vo));
    }

}
