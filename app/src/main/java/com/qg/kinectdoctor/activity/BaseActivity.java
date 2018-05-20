package com.qg.kinectdoctor.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qg.kinectdoctor.util.ActivityCollector;

/**
 * Created by ZH_L on 2016/10/21.
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.getInstance().add(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().remove(this);
    }

    @Override
    public void onClick(View view) {

    }
}
