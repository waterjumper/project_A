package com.company.project.controller.api;

import com.company.project.common.utils.DataResult;
import com.company.project.pojo.*;
import com.company.project.service.cooke.CartService;
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
 * CartController
 */
@RestController
@Api(tags = "c端购物车管理")
@RequestMapping("/cart/api")
@Slf4j
public class CartController {

    @Resource
    private CartService cartService;

    @PostMapping("/list")
    @ApiOperation(value = "购物车列表")
    public DataResult cartList(@RequestBody @Valid CartQueryReq vo) {
        return DataResult.success(cartService.pageList(vo));
    }

    @PostMapping(value = "/add")
    @ApiOperation(value = "购物车添加")
    public DataResult add(@RequestBody @Valid CartAddReq req) {
        return DataResult.success(cartService.add(req));
    }

    @PostMapping(value = "/increment")
    @ApiOperation(value = "购物车增加数量")
    public DataResult increment(@RequestBody @Valid CartInCrementAndDecrementReq req) {
        return DataResult.success(cartService.increment(req));
    }

    @PostMapping(value = "/decrement")
    @ApiOperation(value = "购物车减少数量")
    public DataResult decrement(@RequestBody @Valid CartInCrementAndDecrementReq req) {
        return DataResult.success(cartService.decrement(req));
    }

    @PostMapping(value = "/remove")
    @ApiOperation(value = "购物项删除")
    public DataResult remove(@RequestBody @Valid CartItemRemoveReq req) {
        return DataResult.success(cartService.removeCartItem(req));
    }

    @PostMapping(value = "/addReview")
    @ApiOperation(value = "添加评论")
    public DataResult addReview(@RequestBody @Valid ReviewAddReq req) {
        return DataResult.success(cartService.addReview(req));
    }

    @PostMapping(value = "/reviewList")
    @ApiOperation(value = "评论列表")
    public DataResult reviewList(@RequestBody @Valid ReviewQueryReq req) {
        return DataResult.success(cartService.pageReviewList(req));
    }

}
