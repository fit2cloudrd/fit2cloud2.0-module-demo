package com.fit2cloud.demo;

import com.fit2cloud.commons.server.base.domain.User;
import com.fit2cloud.commons.server.base.mapper.UserMapper;
import com.fit2cloud.demo.dao.optional.OptionalDemoMapper;
import com.fit2cloud.demo.dao.primary.DemoMapper;
import com.fit2cloud.demo.model.Demo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MultiDataSourceTest {

    @Resource
    private DemoMapper demoMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private OptionalDemoMapper optionalDemoMapper;

    @Test
    public void testMultiDataSource() {
        List<Demo> demos = demoMapper.selectByExample(null);
        System.out.println(demos.size());

        List<Demo> demoList = optionalDemoMapper.selectAll();
        System.out.println(demoList.size());
    }

    @Test
    public void testPageable() {
        Page<Object> page = PageHelper.startPage(1, 1, true);
        List<User> userList = userMapper.selectByExample(null);
        System.out.println(userList.size());

        page = PageHelper.startPage(1, 1, true);
        List<Demo> demoList = optionalDemoMapper.selectAll();
        System.out.println(demoList.size());
    }
}
