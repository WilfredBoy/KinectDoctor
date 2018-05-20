package com.qg.kinectdoctor.result;

import android.text.TextUtils;

/**
 * Created by ZH_L on 2016/10/21.
 */
public class Result {
    public int status = 1;
    public String errMsg = "";

    public boolean isOk() {
        return status == 1;
    }

    public String getErrMsg() {
        String errMsg = this.errMsg;
        if (!TextUtils.isEmpty(errMsg)) {
            return errMsg;
        } else {
            return otherErrMsg();
        }
    }

    protected String otherErrMsg() {
        return "未知错误";
    }
}
