package com.company.project.service.impl.cooke;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.common.exception.BusinessException;
import com.company.project.common.exception.code.BaseResponseCode;
import com.company.project.common.utils.Constant;
import com.company.project.common.utils.ToolUtils;
import com.company.project.entity.cooke.Cart;
import com.company.project.entity.cooke.Dishes;
import com.company.project.entity.cooke.Review;
import com.company.project.enums.CookIngredientEnum;
import com.company.project.enums.CookSizeEnum;
import com.company.project.enums.CookTasteEnum;
import com.company.project.mapper.cooke.CartMapper;
import com.company.project.mapper.cooke.DishesMapper;
import com.company.project.mapper.cooke.ReviewMapper;
import com.company.project.pojo.*;
import com.company.project.service.HttpSessionService;
import com.company.project.service.cooke.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * CartServiceImpl
 */
@Service
@Slf4j
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Resource
    private CartMapper cartMapper;


    @Resource
    private DishesMapper dishesMapper;

    @Resource
    private ReviewMapper reviewMapper;

    @Resource
    private HttpSessionService httpSessionService;

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<CartListResponse> pageList(CartQueryReq vo) {
        Long currentUserId = httpSessionService.getCurrentUserIdAndCheck();
        log.info("cart pageList currentUserId:{}", currentUserId);

        Page<Cart> page = new Page<>(vo.getPage(), vo.getLimit());

        LambdaQueryWrapper<Cart> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Cart::getUserId, currentUserId).select(Cart::getId, Cart::getProductCode, Cart::getQuantity, Cart::getUnitPrice);
        queryWrapper.orderByDesc(Cart::getId);


        Page<Cart> cartPage = cartMapper.selectPage(page, queryWrapper);
        List<Cart> records = cartPage.getRecords();

        Page<CartListResponse> cartListResponsePage = new Page<>();
        cartListResponsePage.setTotal(cartPage.getTotal());
        cartListResponsePage.setSize(cartPage.getSize());
        cartListResponsePage.setCurrent(cartPage.getCurrent());
        cartListResponsePage.setPages(cartPage.getPages());
        if (CollectionUtils.isEmpty(records)) {
            return cartListResponsePage;
        }

        Set<String> productCodes = records.stream().map(Cart::getProductCode).collect(Collectors.toSet());
        LambdaQueryWrapper<Dishes> dishesQueryWrapper = Wrappers.lambdaQuery();
        dishesQueryWrapper.in(Dishes::getCode, productCodes)
                .select(Dishes::getCode, Dishes::getName, Dishes::getUrl);

        Map<String, Dishes> dishesMap = dishesMapper.selectList(dishesQueryWrapper).stream()
                .collect(Collectors.toMap(Dishes::getCode, Function.identity()));


        List<CartListResponse> cartListResponseList = records.stream().map(item -> {
                    CartListResponse cartListResponse = new CartListResponse();
                    cartListResponse.setId(item.getId());
                    cartListResponse.setProductCode(item.getProductCode());
                    cartListResponse.setQuantity(item.getQuantity());
                    cartListResponse.setUnitPrice(item.getUnitPrice());

                    Dishes dishes = dishesMap.get(item.getProductCode());
                    if (Objects.nonNull(dishes)) {
                        cartListResponse.setProductUrl(dishes.getUrl());
                        cartListResponse.setProductName(dishes.getName());
                    }
                    return cartListResponse;
                })
                .collect(Collectors.toList());

        cartListResponsePage.setRecords(cartListResponseList);
        return cartListResponsePage;

    }


    @Override
    public boolean add(CartAddReq vo) {
        CartAddReq.CookConfig config = vo.getConfig();
        if (Objects.isNull(config)) {
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        String size = config.getSize();
        String taste = config.getTaste();
        String ingredient = config.getIngredient();
        if (!StringUtils.hasText(size) || !CookSizeEnum.validateValue(size)) {
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        if (!StringUtils.hasText(taste) || !CookTasteEnum.validateValue(taste)) {
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        if (!StringUtils.hasText(ingredient) || !CookIngredientEnum.validateValue(ingredient)) {
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }


        Long currentUserId = httpSessionService.getCurrentUserIdAndCheck();
        log.info("cart add currentUserId:{}", currentUserId);

        LambdaQueryWrapper<Dishes> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Dishes::getCode, vo.getProductCode()).last(Constant.LIMIT_1);

        Dishes dishes = dishesMapper.selectOne(queryWrapper);
        if (Objects.isNull(dishes)) {
            throw new BusinessException(BaseResponseCode.DISHED_NOT_EXIXT);
        }

        Cart newCart = new Cart();
        newCart.setUserId(currentUserId);
        newCart.setProductCode(vo.getProductCode());
        newCart.setQuantity(vo.getQuantity());
        newCart.setUnitPrice(dishes.getPrice());
        newCart.setConfig(JSONObject.toJSONString(vo.getConfig()));
        newCart.setRemark(vo.getRemark());
        newCart.setCtime(ToolUtils.timestamp2());
        newCart.setUtime(ToolUtils.timestamp2());

        return cartMapper.insertOrUpdateAuantity(newCart) > 0;
    }


    @Override
    public boolean removeCartItem(CartItemRemoveReq req) {
        Long currentUserId = httpSessionService.getCurrentUserIdAndCheck();
        log.info("cart removeCartItem currentUserId:{}", currentUserId);

        LambdaQueryWrapper<Cart> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Cart::getId, req.getId())
                .select(Cart::getId, Cart::getUserId);

        Cart cart = cartMapper.selectOne(queryWrapper);
        if (Objects.isNull(cart) || !currentUserId.equals(cart.getUserId())) {
            throw new BusinessException(BaseResponseCode.ILLEGAL_REQUEST);
        }

        return cartMapper.deleteById(cart.getId()) > 0;
    }

    @Override
    public boolean increment(CartInCrementAndDecrementReq req) {
        return doCart(req, 1);
    }


    @Override
    public boolean decrement(CartInCrementAndDecrementReq req) {
        return doCart(req, -1);
    }


    @Override
    public boolean addReview(ReviewAddReq req) {
        Long currentUserId = httpSessionService.getCurrentUserIdAndCheck();
        log.info("cart addReview currentUserId={}", currentUserId);

        Review newReview = new Review();
        newReview.setUserId(currentUserId);
        newReview.setReview(req.getReview());
        newReview.setStar(req.getStar());
        newReview.setProductCode(req.getProductCode());


        return reviewMapper.insert(newReview) > 0;
    }

    @Override
    public IPage<Review> pageReviewList(ReviewQueryReq req) {
        Long currentUserId = httpSessionService.getCurrentUserIdAndCheck();
        log.info("cart pageList currentUserId:{}", currentUserId);

        Page<Review> page = new Page<>(1, 50000);

        LambdaQueryWrapper<Review> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Review::getProductCode, req.getProductCode())
                .select(Review::getId, Review::getReview, Review::getStar);
        queryWrapper.orderByDesc(Review::getId);

        return reviewMapper.selectPage(page, queryWrapper);
    }

    private boolean doCart(CartInCrementAndDecrementReq req, int quantity) {
        Long currentUserId = httpSessionService.getCurrentUserIdAndCheck();
        log.info("cart doCart currentUserId:{} quantity={}", currentUserId, quantity);

        LambdaQueryWrapper<Cart> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Cart::getId, req.getId())
                .select(Cart::getId, Cart::getUserId, Cart::getQuantity);

        Cart cart = cartMapper.selectOne(queryWrapper);
        if (Objects.isNull(cart) || !currentUserId.equals(cart.getUserId())) {
            throw new BusinessException(BaseResponseCode.ILLEGAL_REQUEST);
        }

        Cart newCart = new Cart();
        newCart.setId(cart.getId());
        newCart.setQuantity(cart.getQuantity() + quantity);


        return cartMapper.updateById(newCart) > 0;
    }
}
