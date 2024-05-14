package com.company.project.entity.cooke;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * Cart
 *
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@TableName(value = "cart")
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends CBaseEntity implements Serializable {


    @TableField
    private Long userId;


    @TableField
    private String productCode;


    @TableField
    private Integer quantity;


    @TableField
    private Integer unitPrice;


    @TableField
    private String config;


    @TableField
    private String remark;

}