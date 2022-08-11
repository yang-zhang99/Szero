package com.ttdo.iam.infra.common.utils;

import com.yang.core.oauth.CustomUserDetails;
import com.yang.core.oauth.DetailsHelper;

public class UserUtils {


    public static CustomUserDetails getUserDetails() {
//        return Optional.ofNullable(DetailsHelper.getUserDetails()).orElseThrow(NotLoginException::new);
        return DetailsHelper.getUserDetails();
    }

}
