package com.company.project.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * CartItemRemoveReq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRemoveReq {

    @NotNull(message = "id can't be empty")
    private Long id;

}
