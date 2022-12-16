package com.test.security.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户和角色关联表
 * </p>
 *
 * @author jin
 * @since 2022-12-15
 */
@Data
@EqualsAndHashCode()
@ApiModel(value="SysUserRole对象", description="用户和角色关联表")
public class SysUserRole{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;


}
