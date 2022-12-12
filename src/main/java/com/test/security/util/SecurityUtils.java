package com.test.security.util;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author jin
 * @Date 2022/12/8 16:04
 * @Description 安全服务工具类
 */
public class SecurityUtils implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return null;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return false;
    }
}
