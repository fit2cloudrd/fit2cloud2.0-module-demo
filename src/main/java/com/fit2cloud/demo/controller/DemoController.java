package com.fit2cloud.demo.controller;

import com.fit2cloud.commons.server.handle.annotation.NoResultHolder;
import com.fit2cloud.commons.utils.ResultHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/demo")
public class DemoController {

    @RequestMapping(value = "/test1/{module}")
    public ResultHolder resultHolder1(@PathVariable String module) throws InterruptedException {
        Thread.sleep(Integer.valueOf(module));
        return ResultHolder.success(module);
    }

    @RequestMapping(value = "/test2/{module}")
    public String resultHolder2(@PathVariable String module) {
        return module;
    }


    @RequestMapping(value = "/test3/{module}")
    @NoResultHolder
    public String resultHolder3(@PathVariable String module) {
        return module;
    }
}
