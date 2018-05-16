package com.fit2cloud.demo.controller;

import com.fit2cloud.commons.utils.ResultHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/demo")
public class DemoController {
    @RequestMapping(value = "/test1/{module}")
    public Object noResultHolder(@PathVariable String module) {
        return ResultHolder.success(module);
    }

    @RequestMapping(value = "/test2/{module}")
    public Object resultHolder(@PathVariable String module) {
        return module;
    }

}
