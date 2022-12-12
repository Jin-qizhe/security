package com.test.security.service.impl;

import com.test.security.entity.LoginUser;
import com.test.security.entity.SysUser;
import com.test.security.exception.ServiceException;
import com.test.security.service.ISysUserService;
import com.test.security.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * @Author jin
 * @Date 2022/12/8 11:57
 * @Description TODO
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ISysUserService iSysUserService;
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = iSysUserService.getUserByName(username);
        //判断用户是否存在
        if (user == null) {
            log.info("登录用户：" + username + " 不存在");
            throw new ServiceException("登录用户：" + username + " 不存在");
        } else {
            if (!passwordValidate(user)) {
                log.info("密码错误");
                throw new ServiceException("密码错误");
            }
        }
        return new LoginUser(user, new HashSet<>());
    }

    private boolean passwordValidate(SysUser user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String password = authentication.getCredentials().toString();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }
}
