package com.qg.kinectdoctor.ui.information.job;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qg.kinectdoctor.R;
import com.qg.kinectdoctor.fragment.BaseFragment;
import com.qg.kinectdoctor.util.ToastUtil;
import com.qg.kinectdoctor.view.ToolbarT;

import static com.qg.kinectdoctor.util.Preconditions.checkNotNull;

/**
 * Created by TZH on 2016/10/29.
 */

public class JobInfoFragment extends BaseFragment implements JobInfoContract.View {

    private JobInfoContract.Presenter mPresenter;

    private TextView mHospitalText;

    private TextView mDepartmentText;

    private TextView mJobTitleText;

    public static JobInfoFragment newInstance() {
        Bundle args = new Bundle();

        JobInfoFragment fragment = new JobInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.job_info_fragment, container, false);

        mHospitalText = (TextView) root.findViewById(R.id.hospital_text);
        mDepartmentText = (TextView) root.findViewById(R.id.clinic_department_text);
        mJobTitleText = (TextView) root.findViewById(R.id.job_title_text);

        // Set up toolbar.
        ToolbarT toolbar = (ToolbarT) root.findViewById(R.id.toolbar);
        toolbar.setDefaultUpButton(getActivity());
        toolbar.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.saveJobInfo(
                        mHospitalText.getText().toString(),
                        mDepartmentText.getText().toString(),
                        mJobTitleText.getText().toString()
                );
            }
        });

        return root;
    }

    @Override
    public void setHospital(String hospital) {
        mHospitalText.setText(hospital);
    }

    @Override
    public void setDepartment(String department) {
        mDepartmentText.setText(department);
    }

    @Override
    public void setJobTitle(String jobTitle) {
        mJobTitleText.setText(jobTitle);
    }

    @Override
    public void showError(String error) {
        ToastUtil.showToast2(getContext(), error);
    }

    @Override
    public void setPresenter(JobInfoContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showSuccessEdit() {
        ToastUtil.showToast2(getContext(), R.string.edit_success);
        getActivity().finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
