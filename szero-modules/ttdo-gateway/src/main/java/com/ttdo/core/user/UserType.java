package com.ttdo.core.user;

import com.ttdo.core.oauth.CustomUserDetails;
import org.apache.commons.lang.StringUtils;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

public abstract class UserType {


    public static final String PARAM_NAME = "user_type";
    public static final String DEFAULT_USER_TYPE = "P";

    private static final Map<String, UserType> HOLDER = new HashMap<>();

    public UserType() {
        HOLDER.put(this.value(), this);
    }

    /**
     * @return 类型值
     */
    public abstract String value();

    public static UserType ofDefault() {
        return HOLDER.get(DEFAULT_USER_TYPE);
    }

    public static UserType ofDefault(String userType) {
        if (StringUtils.isBlank(userType)) {
            CustomUserDetails self = null;
//            CustomUserDetails self = DetailsHelper.getUserDetails();
            if (self != null) {
                userType = self.getUserType();
            }
        }

        if (StringUtils.isBlank(userType)) {
            return ofDefault();
        }

        if (!HOLDER.containsKey(userType)) {
            String finalUserType = userType;
            UserType newUserType = new UserType() {
                @Override
                public String value() {
                    return finalUserType;
                }
            };
            HOLDER.put(userType, newUserType);
        }
        return HOLDER.get(userType);
    }

}
