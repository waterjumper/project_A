package com.company.project.entity.cooke;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * Review
 *
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@TableName(value = "review")
@NoArgsConstructor
@AllArgsConstructor
public class Review extends CBaseEntity implements Serializable {


    @TableField
    private Long userId;


    @TableField
    private String productCode;


    @TableField
    private Integer star;



    @TableField
    private String review;

}