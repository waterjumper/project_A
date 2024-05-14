package com.company.project.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * ReviewAddReq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewAddReq {

    @NotBlank(message = "productCode can't be empty!")
    @NotNull(message = "productCode can't be empty!")
    private String productCode;

    @NotNull(message = "star can't be empty!")
    private Integer star;

    @NotBlank(message = "review can't be empty!")
    @NotNull(message = "review can't be empty!")
    private String review;

}
