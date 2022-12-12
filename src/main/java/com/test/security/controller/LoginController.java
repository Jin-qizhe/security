package com.test.security.controller;

import com.test.security.service.ISysUserService;
import com.test.security.util.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author jin
 * @Date 2022/12/7 16:03
 * @Description TODO
 */
@Controller
@Api(tags = "登录相关")
public class LoginController {

    @Autowired
    private ISysUserService iSysUserService;

    @GetMapping("/login")
    @ApiOperation(value = "登录页")
    public String login() {
        return "/login";
    }

    @PostMapping("/doLogin")
    @ApiOperation(value = "登录方法")
    @ResponseBody
    public AjaxResult doLogin(String username, String password) {
        String token = iSysUserService.login(username,password);
        System.out.println("token:------------->" + token);
        return AjaxResult.success(token);
    }
}
