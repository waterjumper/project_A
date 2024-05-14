package com.echo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.echo.entity.CUser;
import com.echo.service.CUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * c端用户(CUser)表控制层
 *
 * @author makejava
 * @since 2024-04-22 13:08:40
 */
@RestController
@RequestMapping("cUser")
public class CUserController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private CUserService cUserService;

    /**
     * 分页查询所有数据
     *
     * @param page  分页对象
     * @param cUser 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<CUser> page, CUser cUser) {
        return success(this.cUserService.page(page, new QueryWrapper<>(cUser)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.cUserService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param cUser 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody CUser cUser) {
        return success(this.cUserService.save(cUser));
    }

    /**
     * 修改数据
     *
     * @param cUser 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody CUser cUser) {
        return success(this.cUserService.updateById(cUser));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.cUserService.removeByIds(idList));
    }
}

