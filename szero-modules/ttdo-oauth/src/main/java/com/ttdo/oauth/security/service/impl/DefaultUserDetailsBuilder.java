package com.ttdo.oauth.security.service.impl;

import com.ttdo.oauth.domain.entity.User;
import com.ttdo.oauth.security.custom.CustomUserDetails;
import com.ttdo.oauth.security.service.UserDetailsBuilder;

import java.util.Collections;

public class DefaultUserDetailsBuilder implements UserDetailsBuilder {


    @Override
    public CustomUserDetails buildUserDetails(User user) {
        CustomUserDetails details = new CustomUserDetails(user.getLoginName(), user.getPassword(), user.getUserType(), Collections.emptyList());
        details.setUserId(user.getId());
//        details.setLanguage(Optional.ofNullable(getLanguageFromSession()).orElse(user.getLanguage()));
        details.setTimeZone(user.getTimeZone());
        details.setEmail(user.getEmail());
        details.setOrganizationId(user.getOrganizationId());
        details.setAdmin(user.getAdmin());
        details.setRealName(user.getRealName());
        details.setImageUrl(user.getImageUrl());

//        BaseClient client = userAccountService.findCurrentClient();
//        if (client != null) {
//            // 接口加密标识
//            details.setApiEncryptFlag(client.getApiEncryptFlag());
//        }
//
//        getUserDetailsWrapper().warp(details, user.getId(), Optional.ofNullable(getRequestTenantId()).orElse(user.getOrganizationId()), true);

        return details;
    }
}
