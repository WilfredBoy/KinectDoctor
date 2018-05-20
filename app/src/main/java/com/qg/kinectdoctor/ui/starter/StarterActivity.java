package com.qg.kinectdoctor.ui.starter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.qg.kinectdoctor.activity.SingleFragmentActivity;

public class StarterActivity extends SingleFragmentActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, StarterActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected Fragment newFragment() {
        return StarterFragment.newInstance();
    }

    @Override
    protected <T extends Fragment> void createPresenter(T fragment) {
        new StarterPresenter((StarterContract.View) fragment);
    }

}
