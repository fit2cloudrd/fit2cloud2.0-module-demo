package com.fit2cloud.demo.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Resource
    private Environment env; // 保存了配置文件的信息

    /**
     * 创建数据源
     * <p>
     * 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
     * </p>
     */
    @Bean
    @Primary
    public DataSource dataSource() throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(env.getProperty("rdb.user"));
        dataSource.setDriverClass(env.getProperty("rdb.driver"));
        dataSource.setPassword(env.getProperty("rdb.password"));
        dataSource.setJdbcUrl(env.getProperty("rdb.url"));
        dataSource.setMaxIdleTime(1800);
        dataSource.setAcquireIncrement(3);
        dataSource.setInitialPoolSize(3);
        dataSource.setAcquireRetryAttempts(30);
        dataSource.setIdleConnectionTestPeriod(600);
        dataSource.setMaxStatements(0);
        dataSource.setBreakAfterAcquireFailure(false);
        dataSource.setTestConnectionOnCheckout(false);
        return dataSource;
    }
}
