package com.qg.kinectdoctor.http;

import com.qg.kinectdoctor.util.ServerIpGetter;

/**
 * Created by ZH_L on 2016/10/21.
 */
public class DefList {
    public static String url = "http://" + ServerIpGetter.getIpPort() + "/RecoveryTraining/DUser/";
    public static String url2 = "http://" + ServerIpGetter.getIpPort() + "/RecoveryTraining/DUser/";
    public static String JSON_KEY = "json";

    public static String url3 = "http://" + ServerIpGetter.getIpPort() + "/RecoveryTraining/PUser/";
}
