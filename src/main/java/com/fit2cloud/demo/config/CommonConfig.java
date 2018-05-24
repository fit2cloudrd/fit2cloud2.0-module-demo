package com.fit2cloud.demo.config;

import com.fit2cloud.commons.pluginmanager.CloudProviderManager;
import com.fit2cloud.commons.utils.CommonThreadPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 */
@PropertySource(value = {
        "file:/opt/fit2cloud/fit2cloud2.0.properties",
        "classpath:properties/global.properties",
        "classpath:properties/quartz.properties"
}, encoding = "UTF-8", ignoreResourceNotFound = true)
@ComponentScan(basePackages = {"com.fit2cloud.commons.server", "com.fit2cloud.common.web", "com.fit2cloud.demo"})
@Configuration
public class CommonConfig {

    @Bean(initMethod = "init")
    public CloudProviderManager cloudProviderManager() {
        return new CloudProviderManager("/opt/fit2cloud/plugins", "com.fit2cloud");
    }

    @Bean(destroyMethod = "shutdown")
    public CommonThreadPool commonThreadPool() {
        CommonThreadPool commonThreadPool = new CommonThreadPool();
        commonThreadPool.setCorePoolSize(20);
        commonThreadPool.setKeepAliveSeconds(3600);
        return commonThreadPool;
    }


}
