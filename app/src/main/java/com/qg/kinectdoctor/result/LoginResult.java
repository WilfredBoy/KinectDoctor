package com.qg.kinectdoctor.result;

import com.qg.kinectdoctor.model.DUser;

/**
 * Created by ZH_L on 2016/10/22.
 */
public class LoginResult extends Result {
    //    private int status;//状态，1为登陆成功，0为密码错误，2为不存在用户
    private DUser dUser;//仅在登陆成功时数据有效

    public int getStatus() {
        return status;
    }

    public DUser getdUser() {
        return dUser;
    }

    @Override
    protected String otherErrMsg() {
        switch (status) {
            case 0:
                return "密码错误";
            case 2:
                return "用户不存在";
            default:
                return super.otherErrMsg();
        }
    }
}
