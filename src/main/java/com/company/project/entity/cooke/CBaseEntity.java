package com.company.project.entity.cooke;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;




/**
 * CBaseEntity
 */
@Data
public abstract class CBaseEntity {

    @TableId
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private Long ctime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long utime;

}
