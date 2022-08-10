package com.yang.core.helper;


import com.yang.core.oauth.CustomUserDetails;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

/**
 * <p>
 * 获取用户语言工具类
 * </p>
 *
 * @author qingsheng.chen 2018/9/12 星期三 15:40
 */
public class LanguageHelper {
    private static volatile String defaultLanguage = "zh_CN";


    private LanguageHelper() {
    }

    public static String getDefaultLanguage() {
        return defaultLanguage;
    }

    public static void setDefaultLanguage(String lang) {
        LanguageHelper.defaultLanguage = lang;
    }

    /**
     * 根据当前登陆用户获取语言信息
     *
     * @return String
     */
    public static String language() {
        CustomUserDetails details = null;
//        CustomUserDetails details = DetailsHelper.getUserDetails();
        String language = null;
        if (details != null) {
            language = details.getLanguage();
        }
        if (StringUtils.isBlank(language)) {
            return defaultLanguage;
        }
        return language;
    }

    public static Locale locale() {
        return LocaleUtils.toLocale(LanguageHelper.language());
    }
}
