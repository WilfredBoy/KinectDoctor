package com.qg.kinectdoctor.model;

import com.qg.kinectdoctor.emsdk.EMConstants;
import com.qg.kinectdoctor.emsdk.IMManager;

import java.io.Serializable;

/**
 * Created by ZH_L on 2016/10/21.
 */
public class ChatInfoBean implements Serializable{
    private PUser pUser;
//    private int unReadCount;

    public ChatInfoBean(PUser pUser){
        this.pUser = pUser;
//        this.unReadCount = unReadCount;
    }

//    public ChatInfoBean(PUser pUser){
//        this.pUser = pUser;
//        this.unReadCount = 0;
//    }

    public void setPUser(PUser pUser){
        this.pUser = pUser;
    }

    public PUser getPUser(){
        return pUser;
    }

//    public int getUnReadCount() {
//        return unReadCount;
//    }

//    public void setUnReadCount(int unReadCount) {
//        this.unReadCount = unReadCount;
//    }

    public String getIMUsername(){
        if(pUser == null){
            throw new NullPointerException("PUser is null");
        }
        return EMConstants.PATIENT_USERNAME_PREFIX + pUser.getPhone();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        }else if(obj instanceof ChatInfoBean){
            ChatInfoBean bean = (ChatInfoBean)obj;
            return bean.getIMUsername().equals(getIMUsername());
        }
        return false;
    }

//    public void clearUnReadCount(){
//
//    }
}
