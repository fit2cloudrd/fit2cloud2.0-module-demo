package com.fit2cloud.demo.controller;

import com.fit2cloud.commons.server.constants.WebConstants;
import com.fit2cloud.commons.server.constants.IndexConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    /**
     * 跳转到无Header主页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("timestamp", WebConstants.timestamp);
        String banner = request.getParameter(IndexConstants.BANNER);
        if (StringUtils.equals(banner, IndexConstants.BANNER_FALSE)) {
            modelAndView.setViewName("index");
        } else {
            modelAndView.setViewName("web-public/index");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/swagger", method = RequestMethod.GET)
    public String swagger() {
        return "swagger";
    }

}
