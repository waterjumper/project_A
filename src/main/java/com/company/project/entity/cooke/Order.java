package com.company.project.entity.cooke;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * Order
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@TableName(value = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order extends CBaseEntity implements Serializable {


    @TableField
    private Long userId;

    @TableField
    private String code;


    // 订单状态（0:待支付；1:已支付；2:已发货；3:已完成；4:已取消）
    @TableField
    private Integer orderStatus;


    @TableField
    private Integer totalAmount;


    @TableField
    private Integer paymentAmount;

}