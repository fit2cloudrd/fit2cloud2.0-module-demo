package com.fit2cloud.demo.config;

import com.github.pagehelper.PageInterceptor;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * spring-boot集成mybatis的基本入口
 * 1）创建数据源
 * 2）创建SqlSessionFactory
 */
@Configuration    //该注解类似于spring配置文件
@MapperScan(basePackages = {"com.fit2cloud.demo.dao.optional"}, sqlSessionFactoryRef = "optionalSqlSessionFactory")
@EnableTransactionManagement
public class OptionalMybatisConfig {
    @Resource
    private Environment env; // 保存了配置文件的信息

    /**
     * 创建数据源
     */
    @Bean
//    @QuartzDataSource // 指定 quartz 的数据库连接池
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
    public SqlSessionFactory optionalSqlSessionFactory(@Qualifier("optionalDataSource") DataSource optionalDataSource,
                                                       @Qualifier("optionalPageInterceptor") PageInterceptor pageInterceptor) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(optionalDataSource);
        factory.setVfs(SpringBootVFS.class);
        // todo 自行添加其他参数
        // 分页插件需要写在第一个，具体原因看 https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/Interceptor.md
        Interceptor[] plugins = new Interceptor[]{pageInterceptor};
        factory.setPlugins(plugins);
        return factory.getObject();
    }

    /**
     * 多数据源需要定义自己的插件体系
     *
     * @return page helper
     */
    @Bean
    public PageInterceptor optionalPageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");// 有可能是其他数据库类型 oracle, pg, etc.
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("pageSizeZero", "true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

}