package com.fit2cloud.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DemoController {

    private static final Long timestamp = System.currentTimeMillis();

    /**
     * 跳转到无Header主页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("timestamp", timestamp);
        return "index";
    }

    /**
     * 跳转到有Header主页
     */
    @RequestMapping(value = "/local", method = RequestMethod.GET)
    public String local(Model model) {
        model.addAttribute("timestamp", timestamp);
        return "local-index";
    }
}
