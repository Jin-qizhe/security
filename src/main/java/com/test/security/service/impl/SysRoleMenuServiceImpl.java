package com.test.security.service.impl;

import com.test.security.entity.SysRoleMenu;
import com.test.security.mapper.SysRoleMenuMapper;
import com.test.security.service.ISysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和菜单关联表 服务实现类
 * </p>
 *
 * @author jin
 * @since 2022-12-15
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

}
