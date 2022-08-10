package com.ttdo.oauth.domain.service.impl;


import com.ttdo.oauth.domain.service.ClearResourceService;
import com.ttdo.oauth.security.service.LoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author bojiangzhou 2020/05/25
 */
@Component
public class ClearResourceServiceImpl implements ClearResourceService {

    @Autowired
    private LoginRecordService loginRecordService;

    @Override
    public void cleaningResource() {
        // 清理登录用户
        loginRecordService.clearLocalLoginUser();

        // 清理 UserDetails
//        CustomUserDetailsService.clearLocalResource();

        // 清理 ClientDetails
//        CustomClientDetailsService.clearLocalResource();
    }
}
