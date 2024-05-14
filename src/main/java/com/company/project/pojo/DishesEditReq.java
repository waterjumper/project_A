package com.company.project.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * DishesEditReq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishesEditReq {

    @NotBlank(message = "The menu code cannot be empty")
    @NotNull(message = "The menu code cannot be empty")
    private String code;

    @NotBlank(message = "The menu item name cannot be empty")
    @NotNull(message = "The menu item name cannot be empty")
    private String name;

    @NotBlank(message = "The menu pictures cannot be empty")
    @NotNull(message = "The menu pictures cannot be empty")
    private String url;

    @NotBlank(message = "The price of the dishes cannot be empty")
    @NotNull(message = "The price of the dishes cannot be empty")
    private String price;

    private String description;
}
