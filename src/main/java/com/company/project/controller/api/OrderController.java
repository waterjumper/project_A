package com.company.project.controller.api;

import com.company.project.common.utils.DataResult;
import com.company.project.pojo.OrderCreateReq;
import com.company.project.pojo.OrderQueryReq;
import com.company.project.service.HttpSessionService;
import com.company.project.service.cooke.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * OrderController
 */
@RestController
@Api(tags = "c端订单管理")
@RequestMapping("/order/api")
@Slf4j
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private HttpSessionService httpSessionService;

    @PostMapping("/list")
    @ApiOperation(value = "创建列表")
    public DataResult list(@RequestBody @Valid OrderQueryReq vo) {
        Long currentUserId = httpSessionService.getCurrentUserIdAndCheck();
        vo.setUserId(currentUserId);
        log.info("OrderController list currentUserId:{}", currentUserId);

        return DataResult.success(orderService.pageList(vo));
    }

    @PostMapping(value = "/create")
    @ApiOperation(value = "创建订单")
    public DataResult create(@RequestBody @Valid OrderCreateReq req) {
        return DataResult.success(orderService.createOrder(req));
    }

}
