package com.company.project.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * DishesAddReq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishesAddReq {

    @NotBlank(message = "The dish name cannot be empty")
    @NotNull(message = "The dish name cannot be empty")
    private String name;

    @NotBlank(message = "The food picture cannot be empty")
    @NotNull(message = "The food picture cannot be empty")
    private String url;

    @NotBlank(message = "The price of the dishes cannot be empty")
    @NotNull(message = "The price of the dishes cannot be empty")
    private String price;

    private String description;
}
