package com.qg.kinectdoctor.emsdk;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ZH_L on 2016/10/22.
 */
public class IMManager {
    private static final String TAG = IMManager.class.getSimpleName();

    private IMManager(Context appContext){
        EMOptions options = new EMOptions();
        //默认添加好友时是不需要验证的，这里也不需要验证
        options.setAcceptInvitationAlways(true);

        EMClient.getInstance().init(appContext, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    };

    private static IMManager instance = null;

    public static IMManager getInstance(Context appContext){
        if(instance == null){
            synchronized (IMManager.class){
                if(instance == null){
                    instance = new IMManager(appContext);
                }
            }
        }
        return instance;
    }

    /**
     * 环信登录
     * @param phone
     * @param callback
     */
    public void login(final String phone, final LoginCallback callback){
        final Handler handler = new Handler(Looper.getMainLooper());
        EMClient.getInstance().login(EMConstants.DOCTOR_USERNAME_PREFIX + phone, EMConstants.LOGIN_PASSWORD, new EMCallBack() {
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d(TAG,"emsdk login success-phone:" + phone);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess();
                    }
                });

            }

            @Override
            public void onError(final int code,final String message) {
                Log.e(TAG, "emsdk login error-message: code = " + code);
                Log.e(TAG,"emsdk login error-message:" + message);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(message);
//                        callback.onSuccess();
                    }
                });

            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }

    /**
     * 环信登录退出(异步)
     */
    public void logoutAsy(final LoginCallback callback){
        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.d(TAG,"emsdk logout success");
                callback.onSuccess();
            }

            @Override
            public void onError(int i, String message) {
                Log.e(TAG,"emsdk logout error-message:" + message);
                callback.onError(message);
            }

            @Override
            public void onProgress(int code, String status) {

            }
        });

    }

    /**
     * 环信登录退出
     */
    public void logout(){
        EMClient.getInstance().logout(false);
    }

    public void sendVoiceMessage(String filePath, int length, String toChatUsername, final Handler handler, final MediaRecordWorker.MediaRecordListener listener){
        final EMMessage message = EMMessage.createVoiceSendMessage(filePath, length, toChatUsername);
        message.setChatType(EMMessage.ChatType.Chat);
//        message.setMessageStatusCallback(callBack);
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                checkIsInMainThread(handler);
                if(listener == null) return;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onSuccess(message);
                    }
                });
            }

            @Override
            public void onError(final int code,final String errMsg) {
                checkIsInMainThread(handler);
                if(listener == null)return;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onError(code, errMsg);
                    }
                });

            }

            @Override
            public void onProgress(final int progress,final String status) {
                checkIsInMainThread(handler);
                if(listener == null) return;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onProgressing(progress, status);
                    }
                });
            }

            private void checkIsInMainThread(Handler handler){
                if(handler == null) {
                    throw new NullPointerException("handler is null");
                }
                Looper looper = handler.getLooper();
                if(looper != Looper.getMainLooper()){
                    throw new RuntimeException("you should a handler associated with the main thread");
                }
            }
        });
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    public void addMessageListener(EMMessageListener listener){
        EMClient.getInstance().chatManager().addMessageListener(listener);
    }

    public void removeMessageListener(EMMessageListener listener){
        EMClient.getInstance().chatManager().removeMessageListener(listener);
    }

    /**
     * 获取未读消息数量
     * @param username
     * @return
     */
    public int getUnreadMsgCount(String username){
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
        if(conversation == null)return 0;
        return conversation.getUnreadMsgCount();
    }

    /**
     * 未读消息清零
     * @param username
     */
    public void clearUnReadMsg(String username){
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
        if(conversation == null){
            return;
        }
        conversation.markAllMessagesAsRead();
    }

    /**
     * 获取所有会话
     * @return
     */
    public Map<String, EMConversation> getAllConversations(){
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        return conversations;
    }

    public void addContact(String phone) throws HyphenateException{
        EMClient.getInstance().contactManager().addContact(EMConstants.PATIENT_USERNAME_PREFIX + phone, null);
    }

    public List<String> getFriendsList() throws HyphenateException {
        List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
        return usernames;
    }

    public EMConversation getCertainConversion(String username){
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username, EMConversation.EMConversationType.Chat);
        return conversation;
    }

    public List<EMMessage> getChatHistory(String username){
        EMConversation conversation = getCertainConversion(username);
        if(conversation != null){
            return conversation.getAllMessages();
        }
        return new ArrayList<>();
    }

    public void setContactListener(EMContactListener listener){
        EMClient.getInstance().contactManager().setContactListener(listener);
    }


}
