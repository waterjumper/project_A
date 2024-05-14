package com.company.project.service.cooke;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.entity.cooke.Cart;
import com.company.project.entity.cooke.Review;
import com.company.project.pojo.*;


/**
 * CartService
 */
public interface CartService extends IService<Cart> {

    IPage<CartListResponse> pageList(CartQueryReq vo);


    boolean add(CartAddReq vo);

    boolean removeCartItem(CartItemRemoveReq req);

    boolean increment(CartInCrementAndDecrementReq req);

    boolean decrement(CartInCrementAndDecrementReq req);

    boolean addReview(ReviewAddReq req);

    IPage<Review> pageReviewList(ReviewQueryReq req);
}
