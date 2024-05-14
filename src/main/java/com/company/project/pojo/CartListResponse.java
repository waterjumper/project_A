package com.company.project.pojo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CartListResponse
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartListResponse {

    private Long id;

    private String productCode;

    private String productName;

    private String productUrl;

    private Integer quantity;

    private Integer unitPrice;

}
