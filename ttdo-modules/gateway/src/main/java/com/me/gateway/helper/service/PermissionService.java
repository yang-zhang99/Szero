package com.me.gateway.helper.service;


import com.me.gateway.helper.entity.PermissionDO;

public interface PermissionService {

    PermissionDO selectPermissionByRequest(String requestKey);

}
