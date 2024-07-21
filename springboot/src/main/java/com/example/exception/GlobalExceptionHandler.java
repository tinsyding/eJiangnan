package com.example.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.example.common.Result;

@ControllerAdvice(basePackages = "com.example.controller")
public class GlobalExceptionHandler {

    private static final Log log = LogFactory.get();

    // 统一异常处理 @ExceptionHandler，主要用于 Exception
    @ExceptionHandler(Exception.class)
    @ResponseBody // 返回 JSON 串
    public Result error(HttpServletRequest request, Exception e) {
        log.error("异常信息：", e);
        return Result.error();
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody // 返回 JSON 串
    public Result customError(HttpServletRequest request, CustomException e) {
        return Result.error(e.getMsg());
    }
}
