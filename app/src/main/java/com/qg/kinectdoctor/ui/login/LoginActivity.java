package com.qg.kinectdoctor.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.qg.kinectdoctor.activity.SingleFragmentActivity;
import com.qg.kinectdoctor.emsdk.EMConstants;
import com.qg.kinectdoctor.emsdk.IMManager;

public class LoginActivity extends SingleFragmentActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initChatListener() {
        IMManager.getInstance(LoginActivity.this).setContactListener(new EMContactListener() {
            @Override
            public void onContactAdded(String s) {

            }

            @Override
            public void onContactDeleted(String s) {

            }

            @Override
            public void onContactInvited(String phone, String s1) {
                try{
                    EMClient.getInstance().contactManager().acceptInvitation(EMConstants.PATIENT_USERNAME_PREFIX + phone);
                }catch (HyphenateException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onContactAgreed(String s) {

            }

            @Override
            public void onContactRefused(String s) {

            }
        });
    }

    @Override
    protected LoginFragment newFragment() {
        return LoginFragment.newInstance();
    }

    @Override
    protected <T extends Fragment> void createPresenter(T fragment) {
        new LoginPresenter((LoginFragment) fragment);
    }

}
