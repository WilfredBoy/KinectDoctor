package com.qg.kinectdoctor.param;

import com.qg.kinectdoctor.model.DUser;

/**
 * 注册传入参数
 * Created by jason on 16-10-18.
 */
public class RegisterParam extends Param{
    public DUser dUser;

    public RegisterParam(String phone, String password, String hospital, String department, String jobTitle) {

        dUser = new DUser(
                null,
                0,
                0,
                phone,
                password,
                hospital,
                department,
                jobTitle
        );
    }
}
