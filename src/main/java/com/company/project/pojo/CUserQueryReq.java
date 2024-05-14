package com.company.project.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * CUserQueryReq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CUserQueryReq {


    private String phone;

    private String username;

    private int page = 1;

    private int limit = 10;

}
