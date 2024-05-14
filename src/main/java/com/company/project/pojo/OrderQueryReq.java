package com.company.project.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OrderQueryReq
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderQueryReq extends QueryBase{

    private Long userId;

    private Integer statue;


    private String code;

}
