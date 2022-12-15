package com.test.security.filter;

import cn.hutool.core.util.StrUtil;
import com.test.security.exception.ServiceException;
import com.test.security.util.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @Author jin
 * @Date 2022/12/15 10:47
 * @Description 认证失败处理类 返回未授权
 */
@Component
@Slf4j
public class AuthenticationEntryPointFilter implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        int code = HttpStatus.UNAUTHORIZED;
        String msg = StrUtil.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
        log.error(msg);
        throw new ServiceException(msg, code);
    }
}
