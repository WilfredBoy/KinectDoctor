package com.qg.kinectdoctor.param;

import com.qg.kinectdoctor.model.DUser;

public class UpdateDUserParam extends Param {
    public DUser dUser;

    public UpdateDUserParam(DUser user) {
        dUser = user;
    }
}
