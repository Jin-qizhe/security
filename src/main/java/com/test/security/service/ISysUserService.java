package com.test.security.service;

import com.test.security.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author jin
 * @since 2022-12-08
 */
public interface ISysUserService extends IService<SysUser> {

    String login(String username, String password);

    SysUser getUserByName(String username);
}
