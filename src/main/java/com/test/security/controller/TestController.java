package com.test.security.controller;

import com.test.security.entity.SysUser;
import com.test.security.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author jin
 * @Date 2022/12/7 14:12
 * @Description TODO
 */
@Controller
public class TestController {

    @Autowired
    private ISysUserService iSysUserService;

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        SysUser admin = iSysUserService.getUserByName("admin");
        if (admin != null) {
            return admin.getUserName();
        } else {
            return "test";
        }
    }

    @GetMapping("/test1")
    @ResponseBody
//    @PreAuthorize("hasAuthority('test1')")
    @PreAuthorize("@per.hasPermi('test1')")
    public String test1() {
        return "test1";
    }
}
