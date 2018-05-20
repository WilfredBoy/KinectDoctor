package com.qg.kinectdoctor.result;

/**
 * Created by jason on 16-10-18.
 */
public class RegisterResult extends Result {
    //public int status;//1-成功，0-失败，2-以存在该用户

    @Override
    protected String otherErrMsg() {
        if (status == 2) {
            return "用户已存在";
        } else {
            return "注册失败";
        }
    }
}
