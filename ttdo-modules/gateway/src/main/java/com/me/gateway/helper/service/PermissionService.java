package com.me.gateway.helper.service;


import com.me.gateway.helper.domain.entity.PermissionDO;

public interface PermissionService {

    PermissionDO selectPermissionByRequest(String requestKey);

}
