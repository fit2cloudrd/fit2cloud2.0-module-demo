package com.fit2cloud.demo.config;

import com.zwzx.common.spring.CommonBeanFactory;
import com.zwzx.common.spring.PropertiesConfigurer;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 *
 */
@Configuration
public class CommonConfig {
    @Bean
    public PropertiesConfigurer propertiesConfigurer() throws Exception {
        PropertiesConfigurer propertiesConfigurer = new PropertiesConfigurer();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        propertiesConfigurer.setIgnoreResourceNotFound(true);
        Resource[] classPathResources = resolver.getResources("classpath*:properties/*.properties");
        Resource applicationResource = resolver.getResource("classpath:application.properties");
        Resource fileSystemResource = resolver.getResource("file:/opt/fit2cloud/fit2cloud.properties");
        //
        Resource[] resources = ArrayUtils.addAll(classPathResources, fileSystemResource, applicationResource);
        propertiesConfigurer.setLocations(resources);
        return propertiesConfigurer;
    }

    @Bean
    public CommonBeanFactory commonBeanFactory() {
        return new CommonBeanFactory();
    }
}
