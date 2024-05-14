package com.company.project.service.impl.cooke;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.common.exception.BusinessException;
import com.company.project.common.exception.code.BaseResponseCode;
import com.company.project.common.utils.Constant;
import com.company.project.common.utils.PasswordUtils;
import com.company.project.entity.cooke.CUser;
import com.company.project.entity.cooke.Dishes;
import com.company.project.mapper.cooke.CUserMapper;
import com.company.project.mapper.cooke.DishesMapper;
import com.company.project.pojo.*;
import com.company.project.service.HttpSessionService;
import com.company.project.service.cooke.CUserService;
import com.company.project.service.cooke.DishesService;
import com.company.project.vo.resp.LoginRespVO;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * DishesServiceImpl
 */
@Service
@Slf4j
public class DishesServiceImpl extends ServiceImpl<DishesMapper, Dishes> implements DishesService {

    @Resource
    private DishesMapper dishesMapper;


    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<Dishes> pageList(DishesQueryReq vo) {
        Page<Dishes> page = new Page<>(vo.getPage(), vo.getLimit());

        LambdaQueryWrapper<Dishes> queryWrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(vo.getName())) {
            queryWrapper.like(Dishes::getName, vo.getName());
        }
        if (!StringUtils.isEmpty(vo.getCode())) {
            queryWrapper.eq(Dishes::getCode, vo.getCode());
        }

        queryWrapper.orderByDesc(Dishes::getId);
        return dishesMapper.selectPage(page, queryWrapper);
    }


    private String genCode() {
        String code = RandomStringUtils.randomAlphanumeric(8) + System.currentTimeMillis()
                + RandomStringUtils.randomAlphanumeric(8);

        LambdaQueryWrapper<Dishes> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Dishes::getCode, code).last(Constant.LIMIT_1);

        Dishes dishes = dishesMapper.selectOne(queryWrapper);
        while (Objects.nonNull(dishes)) {
            code = RandomStringUtils.randomAlphanumeric(8) + System.currentTimeMillis()
                    + RandomStringUtils.randomAlphanumeric(8);
            queryWrapper.eq(Dishes::getCode, code).last(Constant.LIMIT_1);
            dishes = dishesMapper.selectOne(queryWrapper);
        }

        return code;
    }

    @Override
    public boolean add(DishesAddReq vo) {
        String code = genCode();
        Dishes newDishes = new Dishes();
        newDishes.setName(vo.getName());
        newDishes.setCode(code);
        newDishes.setUrl(vo.getUrl());
        newDishes.setPrice(Integer.valueOf(vo.getPrice()));
        newDishes.setDescription(vo.getDescription());

        // newDishes.setCtime(System.currentTimeMillis());
        // newDishes.setUtime(System.currentTimeMillis());

        return dishesMapper.insert(newDishes) > 0;
    }


    @Override
    public boolean edit(DishesEditReq vo) {
        LambdaQueryWrapper<Dishes> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Dishes::getCode, vo.getCode()).last(Constant.LIMIT_1);

        Dishes dishes = dishesMapper.selectOne(queryWrapper);
        if (Objects.isNull(dishes)) {
            throw new BusinessException(BaseResponseCode.DISHED_NOT_EXIXT);
        }

        Dishes updateDishes = new Dishes();
        updateDishes.setId(dishes.getId());
        updateDishes.setName(vo.getName());
        updateDishes.setUrl(vo.getUrl());
        updateDishes.setPrice(Integer.valueOf(vo.getPrice()));
        updateDishes.setDescription(vo.getDescription());


        return dishesMapper.updateById(updateDishes) > 0;
    }
}
