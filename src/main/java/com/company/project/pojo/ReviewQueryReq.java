package com.company.project.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * ReviewQueryReq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewQueryReq {

    @NotBlank(message = "productCode can't be empty!")
    @NotNull(message = "productCode can't be empty!")
    private String productCode;

}
