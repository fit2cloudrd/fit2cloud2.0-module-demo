package com.fit2cloud.demo;

import com.fit2cloud.commons.server.base.domain.CloudAccount;
import com.fit2cloud.commons.server.base.mapper.CloudAccountMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    @Resource
    private CloudAccountMapper cloudAccountMapper;

    @Test
    public void contextLoads() {
        List<CloudAccount> cloudAccounts = cloudAccountMapper.selectByExample(null);
        cloudAccounts.forEach(cloudAccount -> {
            System.out.println(cloudAccount.getCredential());
        });
    }

}
