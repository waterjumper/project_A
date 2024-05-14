package com.company.project.mapper.cooke;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.project.entity.cooke.Cart;
import org.apache.ibatis.annotations.Param;


public interface CartMapper extends BaseMapper<Cart> {

    int insertOrUpdateAuantity(@Param("item") Cart item);

}