package com.qg.kinectdoctor.emsdk;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMMessage;

import java.util.List;

/**
 * Created by ZH_L on 2016/10/22.
 */
public abstract class IMCallback implements EMMessageListener, EMContactListener, EMCallBack {

    @Override
    public void onContactAdded(String username) {
        //增加了联系人时回调此方法
        onAddContact(username);
    }

    public abstract void onAddContact(String username);

    @Override
    public void onContactDeleted(String username) {
        //被删除时回调此方法
        onDeleteMe(username);
    }

    public abstract void onDeleteMe(String username);

    @Override
    public void onMessageReceived(List<EMMessage> list) {
        //收到消息
        onReceiveMessage(list);
    }

    public abstract void onReceiveMessage(List<EMMessage> list);

    @Override
    public void onSuccess() {
        //成功发送消息
        onSendMessageSuccess();
    }

    public abstract void onSendMessageSuccess();

    @Override
    public void onError(int code, String errorMsg) {
        //发送消息失败
        onSendMessageError(code, errorMsg);
    }

    public abstract void onSendMessageError(int code, String errorMsg);

    @Override
    public void onProgress(int progress, String status) {
        //正在发送消息
        onSendMessageProgressing(progress, status);
    }

    public abstract void onSendMessageProgressing(int progress, String status);

    @Override
    public void onContactInvited(String s, String s1) {

    }

    @Override
    public void onContactAgreed(String s) {

    }

    @Override
    public void onContactRefused(String s) {

    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageReadAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageDeliveryAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }
}