package com.me.gateway.helper.service.impl;


import com.me.gateway.helper.domain.repository.PermissionCheckRepository;
import com.me.gateway.helper.entity.CheckResponse;
import com.me.gateway.helper.entity.RequestContext;
import com.me.gateway.helper.service.PermissionCheckService;
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
