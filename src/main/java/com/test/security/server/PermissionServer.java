package com.test.security.server;

import com.test.security.entity.SysRole;
import com.test.security.entity.SysUser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author jin
 * @Date 2022/12/15 14:21
 * @Description 用户权限处理
 */
@Service("per")
public class PermissionServer {

    /**
     * 获取角色数据权限
     *
     * @param user
     * @return
     */
    public Set<String> getRolePermission(SysUser user) {
        Set<String> roles = new HashSet<>();
        //管理员权限
        if ("admin".equals(user.getUserName())) {
            roles.add("admin");
        } else if (true) {

        }
        return roles;
    }

    /**
     * 获取菜单数据权限
     *
     * @param user
     * @return
     */
    public Set<String> getMenuPermission(SysUser user) {
        Set<String> perms = new HashSet<>();
        //管理员权限
        if ("admin".equals(user.getUserName()) && 1 == user.getUserId()) {
            perms.add("*:*:*");
        } else {
//            List<SysRole> roles = user.getRoles();
        }
        return perms;
    }

    /**
     * 验证用户是否具有某权限
     *
     * @param permission
     * @return
     */
    public Boolean hasPermi(String permission) {
        if ("test".equals(permission)) {
            return true;
        } else {
            return false;
        }
    }


}
