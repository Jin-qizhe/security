package com.test.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.test.security.constant.Constants;
import com.test.security.entity.LoginUser;
import com.test.security.entity.SysUser;
import com.test.security.mapper.SysUserMapper;
import com.test.security.server.TokenServer;
import com.test.security.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author jin
 * @since 2022-12-08
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private TokenServer tokenServer;

    @Override
    public String login(String username, String password) {
        //用户验证
        Authentication authentication = null;
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
        authentication = authenticationManager.authenticate(authenticationToken);
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return tokenServer.createToken(loginUser);
    }

    @Override
    public SysUser getUserByName(String username) {
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.eq("user_name", username);
        SysUser one = getOne(qw);
        return one;
    }
}
