package com.me.gateway.helper.service;

import com.me.gateway.helper.domain.entity.RequestContext;

public interface CustomPermissionCheckService {
    void checkPermission(RequestContext context);
}
