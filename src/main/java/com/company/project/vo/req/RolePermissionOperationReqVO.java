package com.company.project.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * RolePermissionOperationReqVO
 */
@Data
public class RolePermissionOperationReqVO {
    @ApiModelProperty(value = "Role ID")
    @NotBlank(message = "Role ID cannot be empty")
    private String roleId;

    @ApiModelProperty(value = "Menu permissions collection")
    @NotEmpty(message = "Menu permissions collection cannot be empty")
    private List<String> permissionIds;
}
