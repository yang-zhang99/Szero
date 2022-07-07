package com.ttdo.gateway.helper.service;

import com.ttdo.gateway.helper.domain.vo.CustomUserDetailsWithResult;

public interface GetUserDetailsService {


    CustomUserDetailsWithResult getUserDetails(String accessToken);
}
