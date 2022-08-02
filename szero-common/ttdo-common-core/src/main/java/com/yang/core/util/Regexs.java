package com.yang.core.util;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 */
public class Regexs {

    private Regexs() {
    }

    /**
     * 邮件
     */
    public static final String EMAIL = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";

    /**
     * 数字
     */
    public static final String NUMBER = "^([+-]?)\\d*\\.?\\d+$";

    /**
     * 匹配正则表达式
     *
     * @param str   str
     * @param regex regex
     * @return boolean
     */
    public static boolean is(String str, String regex) {
        return Pattern.matches(regex, str);
    }

    public static boolean isEmail(String str) {
        return is(str, Regexs.EMAIL);
    }

    public static boolean isNumber(String str) {
        return is(str, Regexs.NUMBER);
    }

    public static boolean isMobile(String str) {
        return isMobile("+86", str);
    }

    public static boolean isMobile(String crownCode, String phone) {
        int code = 0;
        long telNumber = 0;

        try {
            code = Integer.parseInt(crownCode);
            telNumber = Long.parseLong(phone);
        } catch (NumberFormatException e) {
            return false;
        }

        Phonenumber.PhoneNumber phoneNumber = new Phonenumber.PhoneNumber();
        phoneNumber.setCountryCode(code);
        phoneNumber.setNationalNumber(telNumber);
        return PHONE_NUMBER_UTIL.isValidNumber(phoneNumber);
    }

    private static final PhoneNumberUtil PHONE_NUMBER_UTIL = PhoneNumberUtil.getInstance();


}
