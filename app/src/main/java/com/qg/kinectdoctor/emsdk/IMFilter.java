package com.qg.kinectdoctor.emsdk;

import com.hyphenate.chat.EMMessage;
import com.qg.kinectdoctor.model.VoiceBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ZH_L on 2016/10/26.
 */
public class IMFilter {


    public static List<String> filterToPhones(String username){
        List<String> list = new ArrayList<>(1);
        if(username == null)return list;
        String phone = filterToPhone(username);
        list.add(phone);
        return list;
    }

    public static List<String> filterToPhones(List<String> usernames){
        List<String> list = new ArrayList<>();
        if(usernames == null)return list;
        for(String username: usernames){
            String phone = filterToPhone(username);
            list.add(phone);
        }
        return list;
    }

    private static String filterToPhone(String username){
        return username.replace(EMConstants.DOCTOR_USERNAME_PREFIX,"").replace(EMConstants.PATIENT_USERNAME_PREFIX, "");
    }

//    public static List<VoiceBean> devideByTimeTitle(List<EMMessage> messages, String chatingName){
//        List<VoiceBean> newMsgs = new ArrayList<>(messages.size());
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        long time = calendar.getTimeInMillis();
//
//        final List<EMMessage> msgs = messages;
//        int index = 0;
//        for(int i=index; i<msgs.size() ; i++){
//            EMMessage msg = msgs.get(i);
//
//            if(msg.getMsgTime() >= time){
//                if(i == index){
//                    //first, add time view
//                    VoiceBean bean = new VoiceBean(EMConstants.VIEWTYPE_TIME, msg);
//                    newMsgs.add(bean);
//                }
//
//                String fromWho = msg.getFrom();
//                int type;
//                if(chatingName.equals(fromWho)){
//                    type = EMConstants.VIEWTYPE_SOMEONE;
//                }else{
//                    type = EMConstants.VIEWTYPE_ME;
//                }
//                VoiceBean bean = new VoiceBean(type, msg);
//                newMsgs.add(bean);
//            }else{
//
//                //record the index
//                index = i;
//
//                //set the time to compare again
//                int day = calendar.get(Calendar.DAY_OF_YEAR);
//                calendar.set(Calendar.DAY_OF_YEAR, --day);
//                time = calendar.getTimeInMillis();
//
//                //i-- for that make i when ++ to the original index
//                i--;
//            }
//        }
//        return newMsgs;
//    }

    //记得奥特曼在地球时间是3分钟
    private static final long DEVIDE_DURATION = 3 * 60 * 1000 ;

    public static List<VoiceBean> devideByTimeTitle(List<EMMessage> messages, String chatingName, EMMessage lastMsg){
        List<VoiceBean> newMsgs = new ArrayList<>();
        EMMessage comparedMsg = lastMsg;
        for(EMMessage message: messages){
            if(comparedMsg == null){
                VoiceBean voiceBean = createVoiceBean(message, chatingName);
                newMsgs.add(voiceBean);
                comparedMsg = message;
                continue;
            }
            long msgTime = message.getMsgTime();
            long comparedMsgTime = comparedMsg.getMsgTime();
            long duration = msgTime - comparedMsgTime;
            if(duration > DEVIDE_DURATION){
                //add time view
                VoiceBean voiceBean = new VoiceBean(EMConstants.VIEWTYPE_TIME, message);
                newMsgs.add(voiceBean);
            }

            //add doctor/patient view
            VoiceBean voiceBean = createVoiceBean(message, chatingName);
            newMsgs.add(voiceBean);

            comparedMsg = message;
        }

        return newMsgs;
    }

    private static VoiceBean createVoiceBean(EMMessage message, String chatingName){

        String fromWho = message.getFrom();
        int type;
        if(fromWho.equals(chatingName)){
            type = EMConstants.VIEWTYPE_SOMEONE;
        }else{
            type = EMConstants.VIEWTYPE_ME;
        }
        VoiceBean voiceBean = new VoiceBean(type, message);
        return voiceBean;
    }
}


