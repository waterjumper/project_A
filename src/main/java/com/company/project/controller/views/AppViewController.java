package com.company.project.controller.views;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Api(tags = "视图")
@Controller
@RequestMapping("/")
public class AppViewController {


    @GetMapping("")
    @ApiOperation(value = "首页")
    public String register() {
        return "index";
    }

}
