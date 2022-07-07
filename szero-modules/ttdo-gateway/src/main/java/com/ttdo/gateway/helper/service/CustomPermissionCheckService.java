package com.ttdo.gateway.helper.service;

import com.ttdo.gateway.helper.entity.RequestContext;

public interface CustomPermissionCheckService {
    void checkPermission(RequestContext context);
}
