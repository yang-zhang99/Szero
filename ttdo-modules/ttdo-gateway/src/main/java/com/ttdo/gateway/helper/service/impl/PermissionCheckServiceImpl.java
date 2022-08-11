package com.ttdo.gateway.helper.service.impl;


import com.ttdo.gateway.helper.domain.repository.PermissionCheckRepository;
import com.ttdo.gateway.helper.entity.CheckResponse;
import com.ttdo.gateway.helper.entity.RequestContext;
import com.ttdo.gateway.helper.service.PermissionCheckService;
import org.apache.tomcat.util.security.PermissionCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


/**
 * 缺失权限应用服务实现
 *
 * @author KAIBING.JIANG@HAND-CHINA.COM
 */
@Service
public class PermissionCheckServiceImpl implements PermissionCheckService {

    @Autowired
    private PermissionCheckRepository permissionCheckRepository;

    @Override
    @Async("permissionCheckSaveExecutor")
    public void savePermissionCheck(RequestContext requestContext, CheckResponse checkResponse) {
//        permissionCheckRepository.insertSelective(PermissionCheck.build(requestContext, checkResponse));
    }
}
