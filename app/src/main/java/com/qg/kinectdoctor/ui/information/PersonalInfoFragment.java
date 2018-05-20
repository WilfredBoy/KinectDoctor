package com.qg.kinectdoctor.ui.information;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qg.kinectdoctor.R;
import com.qg.kinectdoctor.activity.App;
import com.qg.kinectdoctor.fragment.BaseFragment;
import com.qg.kinectdoctor.ui.information.base.BaseInfoActivity;
import com.qg.kinectdoctor.ui.information.job.JobInfoActivity;
import com.qg.kinectdoctor.view.ToolbarT;

import static com.qg.kinectdoctor.util.Preconditions.checkNotNull;

/**
 * Created by TZH on 2016/10/26.
 */
public class PersonalInfoFragment extends BaseFragment implements PersonalInfoContract.View {

    private static final String ARGUMENT_DOCTOR_ID = "DOCTOR_ID";

    private PersonalInfoContract.Presenter mPresenter;

    private TextView mName;

    private TextView mInfo;

    public static PersonalInfoFragment newInstanceWithPresenter() {
        int doctorId = App.getInstance().getUser().getId();
        PersonalInfoFragment fragment = newInstance(doctorId);
        new PersonalInfoPresenter(doctorId, fragment);
        return fragment;
    }

    /*package*/ static PersonalInfoFragment newInstance(int doctorId) {
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_DOCTOR_ID, doctorId);
        PersonalInfoFragment fragment = new PersonalInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull PersonalInfoContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.personal_info_fragment, container, false);

        mName = (TextView) root.findViewById(R.id.name);
        mInfo = (TextView) root.findViewById(R.id.info);

        root.findViewById(R.id.base_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.baseInfo();
            }
        });

        root.findViewById(R.id.job_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.jobInfo();
            }
        });

        root.findViewById(R.id.account_manage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.manageAccount();
            }
        });

        return root;
    }

    @Override
    public void showName(String name) {
        mName.setText(name);
    }

    @Override
    public void showInfo(String info) {
        mInfo.setText(info);
    }

    @Override
    public void showBaseInfo(int doctorId) {
        BaseInfoActivity.start(getContext(), doctorId);
    }

    @Override
    public void showJobInfo(int doctorId) {
        JobInfoActivity.start(getContext(), doctorId);
    }

    @Override
    public void showAccountManage(int doctorId) {
        /* This method was deprecated */
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
