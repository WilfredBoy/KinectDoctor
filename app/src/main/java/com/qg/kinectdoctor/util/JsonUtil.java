package com.qg.kinectdoctor.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by ZH_L on 2016/10/21.
 */
public class JsonUtil {

    //date-format-->2016-10-30 07:22:47

    public static String toJson(Object obj){
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd hh:mm:ss");
        Gson gson = builder.create();
        return gson.toJson(obj);
    }

    public static <T> T toObj(String json, Class<T> clazz){
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd hh:mm:ss");
        Gson gson = builder.create();
        return gson.fromJson(json, clazz);
    }
}
