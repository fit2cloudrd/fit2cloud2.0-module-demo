package com.fit2cloud.demo.controller;

import com.fit2cloud.commons.server.constants.IndexConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    private static final Long timestamp = System.currentTimeMillis();

    /**
     * 跳转到无Header主页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("timestamp", timestamp);
        String banner = request.getParameter(IndexConstants.BANNER);
        if (StringUtils.equals(banner, IndexConstants.BANNER_FALSE)) {
            return "index";
        } else {
            return "redirect:/web-public";
        }
    }
}
