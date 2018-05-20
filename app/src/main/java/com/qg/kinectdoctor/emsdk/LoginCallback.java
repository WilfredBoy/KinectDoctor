package com.qg.kinectdoctor.emsdk;

/**
 * Created by ZH_L on 2016/10/22.
 */
public interface LoginCallback {
    void onSuccess();
    void onError(String errorMsg);
}
