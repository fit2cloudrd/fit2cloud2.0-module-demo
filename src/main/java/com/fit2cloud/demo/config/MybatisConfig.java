package com.fit2cloud.demo.config;

import com.fit2cloud.commons.utils.DBEncryptInterceptor;
import com.fit2cloud.commons.utils.EncryptConfig;
import com.github.pagehelper.PageInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * spring-boot集成mybatis的基本入口
 * 1）创建数据源
 * 2）创建SqlSessionFactory
 */
@Configuration    //该注解类似于spring配置文件
@MapperScan(basePackages = {"com.fit2cloud.mc.dao", "com.fit2cloud.commons.server.base.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
@EnableTransactionManagement
public class MybatisConfig {

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

    @Bean
    public DBEncryptInterceptor dbEncryptInterceptor() {
        DBEncryptInterceptor dbEncryptInterceptor = new DBEncryptInterceptor();
        List<EncryptConfig> configList = new ArrayList<>();
        // 添加加密/解密配置
        configList.add(new EncryptConfig() {{
            setAttrName("credential");
            setModelName("com.fit2cloud.mc.dto.CloudAccountDto");
            setEncryptClass("com.fit2cloud.commons.utils.EncryptUtils");
            setEncryptMethod("aesEncrypt");
            setDecryptClass("com.fit2cloud.commons.utils.EncryptUtils");
            setDecryptMethod("aesDecrypt");
        }});
        dbEncryptInterceptor.setEncryptConfigList(configList);
        return dbEncryptInterceptor;
    }
}