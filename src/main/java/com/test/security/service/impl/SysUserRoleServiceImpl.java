package com.test.security.service.impl;

import com.test.security.entity.SysUserRole;
import com.test.security.mapper.SysUserRoleMapper;
import com.test.security.service.ISysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author jin
 * @since 2022-12-15
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

}
