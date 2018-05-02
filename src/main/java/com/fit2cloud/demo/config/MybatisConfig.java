package com.fit2cloud.demo.config;

import com.github.pagehelper.PageInterceptor;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zwzx.common.spring.PropertiesConfigurer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * spring-boot集成mybatis的基本入口
 * 1）创建数据源
 * 2）创建SqlSessionFactory
 */
@Configuration    //该注解类似于spring配置文件
@MapperScan(basePackages = "com.fit2cloud.demo.dao", sqlSessionFactoryRef = "sqlSessionFactory")
@EnableTransactionManagement
public class MybatisConfig {

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

    /**
     * 分页插件需要自己写，不然无法添加其他的plugins
     *
     * @return page helper
     */
    @Bean
    public PageInterceptor pageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("pageSizeZero", "true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }
}