package com.fit2cloud.demo;

import com.fit2cloud.demo.dao.optional.OptionalDemoMapper;
import com.fit2cloud.demo.dao.primary.DemoMapper;
import com.fit2cloud.demo.model.Demo;
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
    private OptionalDemoMapper optionalDemoMapper;

    @Test
    public void testMultiDataSource() {
        List<Demo> demos = demoMapper.selectByExample(null);
        System.out.println(demos.size());

        List<Demo> demoList = optionalDemoMapper.selectAll();
        System.out.println(demoList.size());
    }
}
