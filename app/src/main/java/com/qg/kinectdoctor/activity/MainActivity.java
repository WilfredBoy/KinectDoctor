package com.qg.kinectdoctor.activity;


import android.os.Bundle;

import com.qg.kinectdoctor.R;
import com.qg.kinectdoctor.ui.starter.StarterActivity;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StarterActivity.start(this);
    }

}
