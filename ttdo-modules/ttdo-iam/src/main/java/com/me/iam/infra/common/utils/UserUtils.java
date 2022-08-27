package com.me.iam.infra.common.utils;

import com.me.core.oauth.CustomUserDetails;
import com.me.core.oauth.DetailsHelper;

public class UserUtils {


    public static CustomUserDetails getUserDetails() {
//        return Optional.ofNullable(DetailsHelper.getUserDetails()).orElseThrow(NotLoginException::new);
        return DetailsHelper.getUserDetails();
    }

}
