package com.qg.kinectdoctor.model;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ZH_L on 2016/10/25.
 */
public class VoiceBean {
//    private PUser pUser;
//    private DUser dUser;
//    private String time;
    private int type;
    private EMMessage message;
    private boolean isPlaying;

    private VoiceBean(int type){
        this.type = type;
        isPlaying = false;
    }

    public VoiceBean(int type, EMMessage message){
        this(type);
        this.message = message;
    }

//    public VoiceBean(int type, DUser dUser){
//        this(type);
//        this.dUser = dUser;
//    }

//    public VoiceBean(int type, String time){
//        this(type);
//        this.time = time;
//    }

//    public DUser getdUser() {
//        return dUser;
//    }

//    public void setdUser(DUser dUser) {
//        this.dUser = dUser;
//    }

//    public PUser getpUser() {
//        return pUser;
//    }

//    public void setpUser(PUser pUser) {
//        this.pUser = pUser;
//    }

    public String getTime() {
        Calendar calendar  = Calendar.getInstance();
        calendar.setTimeInMillis(message.getMsgTime());
        DateFormat format = new SimpleDateFormat("MM-dd hh:mm");
        String str = format.format(calendar.getTime());
        return str;
    }

//    public void setTime(String time) {
//        this.time = time;
//    }

    public EMVoiceMessageBody getVoice(){
        return (EMVoiceMessageBody) message.getBody();
    }

    public int getType() {
        return type;
    }

    public boolean isPlaying(){
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying){
        this.isPlaying = isPlaying;
    }

    public EMMessage getMessage(){
        return message;
    }

    public void setMessage(EMMessage message){
        this.message = message;
    }

//    public void setType(int type) {
//        this.type = type;
//    }
}
