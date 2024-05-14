package com.company.project.entity.cooke;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * OrderDetail
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@TableName(value = "order_details")
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail extends CBaseEntity implements Serializable {


    @TableField
    private Long orderId;


    @TableField
    private String productCode;


    @TableField
    private String productName;


    @TableField
    private Integer quantity;


    @TableField
    private Integer unitPrice;


}