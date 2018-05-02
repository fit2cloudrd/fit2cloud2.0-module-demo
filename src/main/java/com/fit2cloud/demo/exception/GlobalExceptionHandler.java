package com.fit2cloud.demo.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public String allExceptionHandler(HttpServletRequest request, Exception exception) {
        exception.printStackTrace();
        System.out.println("我报错了：" + exception.getLocalizedMessage());
        System.out.println("我报错了：" + exception.getCause());
        System.out.println("我报错了：" + Arrays.toString(exception.getSuppressed()));
        System.out.println("我报错了：" + exception.getMessage());
        System.out.println("我报错了：" + Arrays.toString(exception.getStackTrace()));
        return "服务器异常，请联系管理员！";
    }
}