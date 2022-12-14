package com.test.security.entity;

import com.test.security.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色和菜单关联表
 * </p>
 *
 * @author jin
 * @since 2022-12-15
 */
@Data
@EqualsAndHashCode()
@ApiModel(value="SysRoleMenu对象", description="角色和菜单关联表")
public class SysRoleMenu{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "菜单ID")
    private Long menuId;


}
