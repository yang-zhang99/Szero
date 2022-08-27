package com.me.oauth.domain.service;

import com.me.core.convertor.ApplicationContextHelper;
import org.springframework.core.env.Environment;

import com.me.core.oauth.CustomUserDetails;
import com.me.core.oauth.DetailsHelper;
import org.apache.commons.lang3.BooleanUtils;

/**
 * Root 用户服务
 *
 * @author bojiangzhou 2020/05/12
 */
public class RootUserService {

    private static final String ROOT_CONFIG_KEY = "hzero.user.enable-root";

    /**
     * If enabled root func ( config key <i>hzero.user.enable-root=true</i> ), and current user is admin, then return true, otherwise return false.
     *
     * @return true - Root enabled
     */
    public static boolean isRootUser() {
        CustomUserDetails self = DetailsHelper.getUserDetails();
        return isRootUser(self);
    }

    /**
     * If enabled root func ( config key <i>hzero.user.enable-root=true</i> ), and current user is admin, then return true, otherwise return false.
     *
     * @param self CustomUserDetails
     * @return true - Root enabled
     */
    public static boolean isRootUser(CustomUserDetails self) {
        if (self == null) {
            return false;
        }
        if (!BooleanUtils.isTrue(self.getAdmin())) {
            return false;
        }
        Environment env = ApplicationContextHelper.getContext().getBean(Environment.class);
        return env.getProperty(ROOT_CONFIG_KEY, Boolean.class, false);
    }

}
