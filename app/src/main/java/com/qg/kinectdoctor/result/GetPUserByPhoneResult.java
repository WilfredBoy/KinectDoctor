package com.qg.kinectdoctor.result;

import com.qg.kinectdoctor.model.PUser;

import java.util.Map;

/**
 * Created by ZH_L on 2016/10/26.
 */
public class GetPUserByPhoneResult extends Result{
        private Map<String,PUser> phoneToPUser;

    public Map<String, PUser> getPhoneToPUser() {
        return phoneToPUser;
    }
}
