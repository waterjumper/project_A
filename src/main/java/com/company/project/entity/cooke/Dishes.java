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

/**
 * 菜品
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "dishes")
@NoArgsConstructor
@AllArgsConstructor
public class Dishes extends CBaseEntity implements Serializable {



    @TableField
    private String code;

    @TableField
    private String name;

    @TableField
    private String url;

    @TableField
    private String description;

    @TableField
    private Integer price;

    @TableField
    private Integer rating;
}