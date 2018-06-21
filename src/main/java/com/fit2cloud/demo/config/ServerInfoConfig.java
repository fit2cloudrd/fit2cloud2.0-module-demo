package com.fit2cloud.demo.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fit2cloud.commons.server.model.Permission;
import com.fit2cloud.commons.server.module.handler.F2CServerInfoConfig;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.InputStream;
import java.util.List;

@Configuration
public class ServerInfoConfig implements F2CServerInfoConfig {

    @Override
    public List<Permission> configurePermission(List<Permission> permissionList) {
        InputStream inputStream = null;
        try {
            PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
            if (!patternResolver.getResource("module.json").exists()) {
                return permissionList;
            }
            inputStream = patternResolver.getResource("module.json").getInputStream();
            String json = IOUtils.toString(inputStream);
            JSONObject object = JSON.parseObject(json);
            permissionList.addAll(JSON.parseArray(object.getString("permissionList"), Permission.class));
        } catch (Exception e) {

        } finally {
            if (inputStream != null) {
                IOUtils.closeQuietly(inputStream);
            }
        }
        return permissionList;
    }
}
