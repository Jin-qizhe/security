package com.test.security.exception;

import cn.hutool.core.util.StrUtil;
import com.test.security.util.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author jin
 * @Date 2022/12/8 17:01
 * @Description 全局异常类
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public AjaxResult handleServiceException(ServiceException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        Integer code = e.getCode();
        return StrUtil.isEmptyIfStr(code) ? AjaxResult.error(e.getMessage()) : AjaxResult.error(code, e.getMessage());
    }
}
