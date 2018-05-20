package com.qg.kinectdoctor.util;

/**
 * Created by TZH on 2016/10/29.
 */

public class NumberUtil {
    public static int parseInt(String n, int def) {
        try {
            return Integer.valueOf(n);
        } catch (NumberFormatException e){
            return def;
        }
    }
}
