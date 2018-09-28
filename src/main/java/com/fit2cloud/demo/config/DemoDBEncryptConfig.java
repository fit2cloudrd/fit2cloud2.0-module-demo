package com.fit2cloud.demo.config;

import com.fit2cloud.commons.server.config.DBEncryptConfig;
import com.fit2cloud.commons.utils.EncryptConfig;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置加密
 */
@Component
public class DemoDBEncryptConfig implements DBEncryptConfig {
    @Override
    public List<EncryptConfig> encryptConfig() {
        return new ArrayList<>();
    }
}
