package com.company.project.controller;


import com.company.project.common.utils.DataResult;
import com.company.project.pojo.DishesAddReq;
import com.company.project.pojo.DishesEditReq;
import com.company.project.pojo.DishesQueryReq;
import com.company.project.service.cooke.DishesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * DishesController
 */
@RestController
@Api(tags = "DishesController")
@RequestMapping("/sys/dishes")
@Slf4j
public class SysDishesController {


    @Resource
    private DishesService dishesService;


    @PostMapping("/pageList")
    @ApiOperation(value = "分页列表接口")
    // @RequiresPermissions("sys:user:list")
    public DataResult pageList(@RequestBody DishesQueryReq vo) {
        return DataResult.success(dishesService.pageList(vo));
    }

    @PostMapping("/add")
    @ApiOperation(value = "菜品录入")
    public DataResult add(@RequestBody @Valid DishesAddReq vo) {
        return DataResult.success(dishesService.add(vo));
    }



    @PutMapping("/edit")
    @ApiOperation(value = "菜品编辑")
    public DataResult edit(@RequestBody @Valid DishesEditReq vo) {
        return DataResult.success(dishesService.edit(vo));
    }
}
