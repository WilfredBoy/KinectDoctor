package com.qg.kinectdoctor.param;

import java.util.List;

/**
 * Created by ZH_L on 2016/10/26.
 */
public class GetDUserByPhoneParam extends Param{
    private List<String> phones;

    public GetDUserByPhoneParam(List<String> phones){
        this.phones = phones;
    }
}
