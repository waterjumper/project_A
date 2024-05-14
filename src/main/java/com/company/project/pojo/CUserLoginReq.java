package com.company.project.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * CUserLoginReq
 *
 */
@Data
public class CUserLoginReq {

    @NotBlank(message = "phone number can't be empty")
    @NotNull(message = "phone number can't be empty")
    private String phone;

    @NotBlank(message = "password can't be empty")
    @NotNull(message = "password can't be empty")
    private String password;

}
