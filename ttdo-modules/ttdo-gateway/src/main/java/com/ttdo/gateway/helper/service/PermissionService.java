package com.ttdo.gateway.helper.service;


import com.ttdo.gateway.helper.entity.PermissionDO;

public interface PermissionService {

    PermissionDO selectPermissionByRequest(String requestKey);

}
