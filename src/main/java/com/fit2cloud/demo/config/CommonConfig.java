package com.fit2cloud.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 */
@PropertySource(value = {
        "file:/opt/fit2cloud/conf/fit2cloud.properties",
        "classpath:properties/global.properties",
        "classpath:properties/quartz.properties"
}, encoding = "UTF-8", ignoreResourceNotFound = true)
@ComponentScan(basePackages = {"com.fit2cloud.commons.server", "com.fit2cloud.common.web", "com.fit2cloud.demo"})
@Configuration
public class CommonConfig {

}
