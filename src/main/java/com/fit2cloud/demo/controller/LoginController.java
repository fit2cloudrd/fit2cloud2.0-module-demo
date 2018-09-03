package com.fit2cloud.demo.controller;

import com.fit2cloud.commons.server.constants.WebConstants;
import com.fit2cloud.commons.server.utils.SessionUtils;
import com.fit2cloud.commons.utils.GlobalConfigurations;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "Login", tags = {"login"})
@Controller
@RequestMapping(headers = "Accept=application/json")
public class LoginController {


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Object login(HttpServletResponse response, Model model) {
        if (SessionUtils.getUser() == null) {
            return loginPath(response, model);
        } else {
            // 如果已经登录过，不用重新登录
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String user, String password, HttpServletResponse response, Model model) {
        String msg;
        if (StringUtils.isBlank(user) || StringUtils.isBlank(password)) {
            msg = "user or password can't be null";
            model.addAttribute("message", msg);
            return loginPath(response, model);
        }

        UsernamePasswordToken token = new UsernamePasswordToken(StringUtils.trim(user), StringUtils.trim(password));
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(token);
            if (subject.isAuthenticated()) {
                return "redirect:/";
            } else {
                model.addAttribute("message", "");
                return loginPath(response, model);
            }
        } catch (IncorrectCredentialsException | UnknownAccountException e) {
            msg = e.getMessage();
        } catch (ExcessiveAttemptsException e) {
            msg = "excessive attempts";
        } catch (LockedAccountException e) {
            msg = "the user has been locked.";
        } catch (DisabledAccountException e) {
            msg = "the user has been disabled. ";
        } catch (ExpiredCredentialsException e) {
            msg = "user expires. ";
        } catch (UnauthorizedException e) {
            msg = "not authorized. " + e.getMessage();
        } catch (AuthenticationException e) {
            msg = e.getMessage();
        }
        model.addAttribute("message", msg);
        return loginPath(response, model);

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
        SecurityUtils.getSubject().logout();
        return loginPath(response, model);
    }

    public String loginPath(HttpServletResponse response, Model model) {
        if (GlobalConfigurations.isReleaseMode()) {
            response.setHeader("Location", "/logout");
            response.setStatus(302);
        }
        model.addAttribute("timestamp", WebConstants.timestamp);
        return "web-public/login";
    }
}
