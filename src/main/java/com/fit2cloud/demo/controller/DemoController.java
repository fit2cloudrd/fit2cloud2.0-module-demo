package com.fit2cloud.demo.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/demo")
public class DemoController {
    @RequestMapping(value = "/menus/{module}")
    public Object getMenus(@PathVariable String module) {
        return ResultHolder.error(module);
    }

}
