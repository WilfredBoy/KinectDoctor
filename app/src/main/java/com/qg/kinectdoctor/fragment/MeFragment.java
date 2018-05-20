package com.qg.kinectdoctor.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qg.kinectdoctor.R;

/**
 * Created by 攀登者 on 2016/10/28.
 */
public class MeFragment extends BaseFragment {
    private View view;

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me, container, false);
        initUI();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // super.onSaveInstanceState(outState);
    }

    private void initUI() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:

                break;
        }
    }
}
