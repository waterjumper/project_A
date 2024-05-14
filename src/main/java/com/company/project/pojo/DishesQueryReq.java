package com.company.project.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DishesQueryReq
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishesQueryReq extends QueryBase{

    private String code;

    private String name;

}
