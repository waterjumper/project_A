package com.company.project.entity.cooke;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "c_user")
@NoArgsConstructor
@AllArgsConstructor
public class CUser extends CBaseEntity implements Serializable {


    @TableField
    @NotBlank(message = "手机号不能为空")
    @NotNull(message = "手机号不能为空")
    private String phone;

    @NotBlank(message = "用户名不能为空")
    @TableField
    private String username;


    @NotBlank(message = "密码不能为空")
    @TableField
    private String password;


    @TableField
    private String salt;
}