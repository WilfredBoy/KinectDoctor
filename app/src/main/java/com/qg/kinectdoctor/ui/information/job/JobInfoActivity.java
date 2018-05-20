package com.qg.kinectdoctor.ui.information.job;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.qg.kinectdoctor.activity.SingleFragmentActivity;

public class JobInfoActivity extends SingleFragmentActivity {

    public static final String ARGUMENT_DOCTOR_ID = "DOCTOR_ID";

    private int mDoctorId;

    public static void start(Context context, int doctorId) {
        Intent starter = new Intent(context, JobInfoActivity.class);
        starter.putExtra(ARGUMENT_DOCTOR_ID, doctorId);
        context.startActivity(starter);
    }

    @Override
    protected void prepareExtraData() {
        mDoctorId = getIntent().getIntExtra(ARGUMENT_DOCTOR_ID, 0);
    }

    @Override
    protected Fragment newFragment() {
        return JobInfoFragment.newInstance();
    }

    @Override
    protected <T extends Fragment> void createPresenter(T fragment) {
        new JobInfoPresenter(mDoctorId, (JobInfoContract.View) fragment);
    }
}
