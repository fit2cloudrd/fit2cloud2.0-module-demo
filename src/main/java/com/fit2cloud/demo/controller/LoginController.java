package com.fit2cloud.demo.controller;

import com.fit2cloud.commons.utils.ResultHolder;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: chunxing
 * Date: 2018/5/9  下午1:05
 * Description:
 */
@Api(value = "Login", tags = {"login"})
@RestController
@RequestMapping(headers = "Accept=application/json")
public class LoginController {


    @RequestMapping(value = "login", method = RequestMethod.GET)
    public Object login(@RequestParam("username") String userName, @RequestParam("password") String password) {

        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);

        return ResultHolder.success(null);
    }


    @RequiresPermissions({"user1"})
    @RequestMapping("authorizeTest")
    public Object authorizeTest() {
        return ResultHolder.success("success");
    }
}
