package com.company.project.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * CartAddReq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartAddReq {

    @NotBlank(message = "productCode can't be empty")
    @NotNull(message = "productCode can't be empty")
    private String productCode;

    @NotNull(message = "quantity can't be empty")
    private Integer quantity;

    private CookConfig config;

    private String remark;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CookConfig {

        /**
         * 大份 中份 小份
         */
        private String size;

        /**
         * 口味
         */
        private String taste;

        /**
         * 配料
         */
        private String ingredient;
    }
}
