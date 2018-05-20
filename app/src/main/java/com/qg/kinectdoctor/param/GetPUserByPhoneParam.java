package com.qg.kinectdoctor.param;

import java.util.List;

/**
 * Created by ZH_L on 2016/10/26.
 */
public class GetPUserByPhoneParam extends Param{
    private List<String> phones;

    public GetPUserByPhoneParam(List<String> phones){
        this.phones = phones;
    }
}
