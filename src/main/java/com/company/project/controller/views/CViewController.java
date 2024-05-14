package com.company.project.controller.views;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "视图")
@Controller
@RequestMapping("/cook")
@Slf4j
public class CViewController {

    @GetMapping("/register")
    @ApiOperation(value = "用户注册接口")
    public String register() {
        return "cook/register";
    }

    @GetMapping("/privacy")
    public String privacy() {
        return "cook/privacy";
    }

    @GetMapping("/service")
    public String service() {
        return "cook/service";
    }

    @GetMapping(value = { "/login" })
    public String login() {
        // Subject subject = SecurityUtils.getSubject();
        // boolean authenticated = subject.isAuthenticated();
        // log.info("cook login authenticated={}", authenticated);
        //
        // if (authenticated) {
        // return "cook/home";
        // }

        return "cook/login";
    }

    @GetMapping(value = { "", "/", "/home" })
    public String home() {
        log.info("cook home..........................");
        return "cook/home";
    }

    @GetMapping(value = { "cart" })
    public String cart() {
        return "cook/cart";
    }

    @GetMapping(value = { "order" })
    public String order() {
        return "cook/order";
    }

    @GetMapping(value = { "profile" })
    public String profile() {
        return "cook/profile";
    }

    @GetMapping(value = { "detail" })
    public String cookDetail() {
        return "cook/detail";
    }
}
