package com.company.project.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * OrderCarItem
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCarItem {

    @NotNull(message = "id can't be empty")
    private Long id;

    @NotBlank(message = "productCode can't be empty")
    @NotNull(message = "productCode can't be empty")
    private String productCode;

    @NotNull(message = "quantity can't be empty")
    @Min(value = 1, message = "quantity cannot be less than ")
    private Integer quantity;

}
