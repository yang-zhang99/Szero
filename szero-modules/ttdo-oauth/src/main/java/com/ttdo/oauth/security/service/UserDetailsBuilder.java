package com.ttdo.oauth.security.service;

import com.ttdo.oauth.domain.entity.User;
import com.ttdo.oauth.security.custom.CustomUserDetails;

public interface UserDetailsBuilder {


    CustomUserDetails buildUserDetails(User user);
}
