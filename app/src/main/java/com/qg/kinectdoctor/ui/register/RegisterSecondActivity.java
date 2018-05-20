package com.qg.kinectdoctor.ui.register;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.qg.kinectdoctor.activity.SingleFragmentActivity;

public class RegisterSecondActivity extends SingleFragmentActivity {

    public static String ARGS_PASSWORD = "password";
    public static String ARGS_PHONE = "phone";

    public static void startForResult(Fragment fragment, int requestCode, String phone, String password) {
        Intent starter = new Intent(fragment.getContext(), RegisterSecondActivity.class);
        starter.putExtra(ARGS_PHONE, phone);
        starter.putExtra(ARGS_PASSWORD, password);
        fragment.startActivityForResult(starter, requestCode);
    }

    private String mPhone;
    private String mPassword;

    @Override
    protected void prepareExtraData() {
        mPhone = getIntent().getStringExtra(ARGS_PHONE);
        mPassword = getIntent().getStringExtra(ARGS_PASSWORD);
    }

    @Override
    protected Fragment newFragment() {
        return RegisterSecondFragment.newInstance();
    }

    @Override
    protected <T extends Fragment> void createPresenter(T fragment) {
        new RegisterSecondPresenter((RegisterContract.View2) fragment, mPhone, mPassword);
    }
}
