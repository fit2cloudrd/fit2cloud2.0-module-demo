package com.fit2cloud.demo.controller;

import com.fit2cloud.commons.server.service.CloudAccountService;
import com.fit2cloud.commons.utils.PageUtils;
import com.fit2cloud.commons.utils.Pager;
import com.fit2cloud.commons.utils.ResultHolder;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import javax.annotation.Resource;


@RestController
@RequestMapping(value = "/demo")
public class DemoController {

    @Resource
    private CloudAccountService cloudAccountService;

    @RequestMapping(value = "/test1/{module}")
    public Object noResultHolder(@PathVariable String module) throws InterruptedException {
        Thread.sleep(Integer.valueOf(module));
        return ResultHolder.success(module);
    }

    @RequestMapping(value = "/test2/{module}")
    public Object resultHolder(@PathVariable String module) {
        return module;
    }

    @RequestMapping(value = "list/{goPage}/{pageSize}", method = RequestMethod.POST)
    public Pager getCloudAccountList(
            @PathVariable int goPage, @PathVariable int pageSize, @RequestBody Map<String, Object> param) {
        System.out.println(param);
        Page page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, cloudAccountService.selectAccounts());
    }

}
