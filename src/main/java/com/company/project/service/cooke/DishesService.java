package com.company.project.service.cooke;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.entity.cooke.Dishes;
import com.company.project.pojo.DishesAddReq;
import com.company.project.pojo.DishesEditReq;
import com.company.project.pojo.DishesQueryReq;


/**
 * DishesService
 */
public interface DishesService extends IService<Dishes> {

    IPage<Dishes> pageList(DishesQueryReq vo);

    boolean add(DishesAddReq vo);

    boolean edit(DishesEditReq vo);
}
