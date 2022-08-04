package com.ttdo.oauth.security.service;

import com.ttdo.oauth.domain.entity.User;
import com.ttdo.oauth.security.custom.CustomUserDetails;
import org.springframework.stereotype.Component;

@Component
public interface UserDetailsBuilder {


    CustomUserDetails buildUserDetails(User user);
}
