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
    public CommonBeanFactory commonBeanFactory() {
        return new CommonBeanFactory();
    }
}
