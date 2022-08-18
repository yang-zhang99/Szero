package com.me.gateway.helper.service;

import com.me.gateway.helper.domain.vo.CustomUserDetailsWithResult;

public interface GetUserDetailsService {


    CustomUserDetailsWithResult getUserDetails(String accessToken);
}
