package com.qg.kinectdoctor.util;

import java.util.regex.Pattern;

/**
 * Created by TZH on 2016/10/28.
 */

public class FormatChecker {
    /**
     * 手机号验证
     */
    public static boolean isMobile(String str) {
        return match(str, "^[1][3,4,5,7,8][0-9]{9}$");
    }

    /**
     * 密码格式验证
     */
    public static boolean isAcceptablePassword(String str) {
        return match(str, "^\\w{6,}$");
    }


    private static boolean match(String str, String pattern) {
        return Pattern.compile(pattern).matcher(str).matches();
    }
}
