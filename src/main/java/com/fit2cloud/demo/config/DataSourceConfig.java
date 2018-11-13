package com.fit2cloud.demo.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Bean
    @Primary
    public DataSource dataSource() throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(env.getProperty("rdb.user"));
        dataSource.setDriverClass(env.getProperty("rdb.driver"));
        dataSource.setPassword(env.getProperty("rdb.password"));
        dataSource.setJdbcUrl(env.getProperty("rdb.url"));
        dataSource.setMaxIdleTime(1800); // 最大空闲时间
        dataSource.setAcquireIncrement(3);// 增长数
        dataSource.setInitialPoolSize(3);// 初始连接数
        dataSource.setMinPoolSize(3); // 最小连接数
        dataSource.setMaxPoolSize(30); // 默认连接数
        dataSource.setAcquireRetryAttempts(30);// 获取连接重试次数
        dataSource.setIdleConnectionTestPeriod(60); // 每60s检查数据库空闲连接
        dataSource.setMaxStatements(0); // c3p0全局的PreparedStatements缓存的大小
        dataSource.setBreakAfterAcquireFailure(false);  // 获取连接失败将会引起所有等待连接池来获取连接的线程抛出异常。但是数据源仍有效保留，并在下次调用getConnection()的时候继续尝试获取连接。如果设为true，那么在尝试获取连接失败后该数据源将申明已断开并永久关闭。Default: false
        dataSource.setTestConnectionOnCheckout(false); // 在每个connection 提交是校验有效性
        dataSource.setTestConnectionOnCheckin(true); // 取得连接的同时将校验连接的有效性
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource, Interceptor[] interceptors) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setVfs(SpringBootVFS.class);
        factory.setPlugins(interceptors);
        // todo 自行添加其他参数
        return factory.getObject();
    }

    /**
     * 创建数据源
     */
    @Bean
    public DataSource optionalDataSource() throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(env.getProperty("optional.rdb.user"));
        dataSource.setDriverClass(env.getProperty("optional.rdb.driver"));
        dataSource.setPassword(env.getProperty("optional.rdb.password"));
        dataSource.setJdbcUrl(env.getProperty("optional.rdb.url"));
        // todo 自行添加其他参数
        return dataSource;
    }

    @Bean
    public SqlSessionFactory optionalSqlSessionFactory(@Qualifier("optionalDataSource") DataSource optionalDataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(optionalDataSource);
        // todo 自行添加其他参数
        return factory.getObject();
    }
}
