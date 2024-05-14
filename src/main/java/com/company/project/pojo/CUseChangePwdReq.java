package com.company.project.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * CUseChangePwdReq
 */
@Data
public class CUseChangePwdReq {

    @NotBlank(message = "password can't be empty")
    @NotNull(message = "password can't be empty")
    private String password;

    @NotBlank(message = "new password can't be empty")
    @NotNull(message = "new password can't be empty")
    private String newPassword;

    @NotBlank(message = "confirm password can't be empty")
    @NotNull(message = "confirm password can't be empt")
    private String confirmNewPassword;

}
