package com.qg.kinectdoctor.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.qg.kinectdoctor.R;
import com.qg.kinectdoctor.util.ActivityUtils;

/**
 * Created by TZH on 2016/10/28.
 */
public abstract class SingleFragmentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        prepareViews();

        prepareExtraData();

        Fragment fragment = getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            fragment = newFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.contentFrame);
        }
        createPresenter(fragment);
    }

    protected void prepareViews() {
    }

    protected void prepareExtraData() {
    }

    protected abstract Fragment newFragment();

    protected abstract <T extends Fragment> void createPresenter(T fragment);
}
