package com.company.project.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * CartInCrementAndDecrementReq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartInCrementAndDecrementReq {

    @NotNull(message = "id can't be empty")
    private Long id;

}
