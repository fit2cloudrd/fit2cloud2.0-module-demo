package com.fit2cloud.demo.config;

import com.fit2cloud.commons.server.constants.RoleConstants;
import com.fit2cloud.commons.server.model.Permission;
import com.fit2cloud.commons.server.module.handler.F2CServerInfoConfig;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ServerInfoConfig implements F2CServerInfoConfig {

    @Override
    public List<Permission> configurePermission(List<Permission> permissionList) {
        Permission permission = new Permission();
        permission.setId("CLOUD_SERVER:READ");
        permission.setName("监控");
        permission.setRoles(Arrays.asList(RoleConstants.Id.ADMIN.name()));
        permissionList.add(permission);
        return permissionList;
    }
}
