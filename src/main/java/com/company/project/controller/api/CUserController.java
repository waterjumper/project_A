package com.company.project.controller.api;

import com.company.project.common.utils.DataResult;
import com.company.project.entity.cooke.CUser;
import com.company.project.pojo.CUseChangePwdReq;
import com.company.project.pojo.CUserLoginReq;
import com.company.project.service.HttpSessionService;
import com.company.project.service.cooke.CUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * c端用户
 */
@RestController
@Api(tags = "c端用户用户管理")
@RequestMapping("/user/api")
@Slf4j
public class CUserController {

    @Resource
    private CUserService cUserService;

    @Resource
    private HttpSessionService httpSessionService;

    @PostMapping("/register")
    @ApiOperation(value = "用户注册接口")
    public DataResult register(@RequestBody @Valid CUser vo) {
        return DataResult.success(cUserService.register(vo));
    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "用户登录接口")
    public DataResult login(@RequestBody @Valid CUserLoginReq req, HttpServletRequest request) {
        return DataResult.success(cUserService.login(req));
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出接口")
    public DataResult logout() {
        return DataResult.success(cUserService.logout());
    }

    @PostMapping("/pwd")
    @ApiOperation(value = "修改密码接口")
    public DataResult updatePwd(@RequestBody CUseChangePwdReq vo) {
        return DataResult.success(cUserService.updatePwd(vo));
    }

}
