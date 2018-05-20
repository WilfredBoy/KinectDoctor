package com.qg.kinectdoctor.ui.information.job;

import com.qg.kinectdoctor.activity.App;
import com.qg.kinectdoctor.logic.LogicHandler;
import com.qg.kinectdoctor.logic.LogicImpl;
import com.qg.kinectdoctor.model.DUser;
import com.qg.kinectdoctor.param.UpdateDUserParam;
import com.qg.kinectdoctor.result.UpdateDUserResult;

import static com.qg.kinectdoctor.util.Preconditions.checkNotNull;

/**
 * Created by TZH on 2016/10/29.
 */

class JobInfoPresenter implements JobInfoContract.Presenter {

    private final JobInfoContract.View mView;

    private final int mDoctorId;

    JobInfoPresenter(int doctorId, JobInfoContract.View view) {
        mDoctorId = doctorId;
        mView = checkNotNull(view);
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadInfo();
    }

    private void loadInfo() {
        DUser user = App.getInstance().getUser();
        mView.setJobTitle(user.getJobTitle());
        mView.setHospital(user.getHospital());
        mView.setDepartment(user.getDepartment());
    }

    @Override
    public void saveJobInfo(String hospital, String department, String jobTitle) {
        DUser user = App.getInstance().getUser();
        user.setHospital(hospital);
        user.setDepartment(department);
        user.setJobTitle(jobTitle);
        LogicImpl.getInstance().updateDUser(new UpdateDUserParam(user), new LogicHandler<UpdateDUserResult>() {
            @Override
            public void onResult(UpdateDUserResult result, boolean onUIThread) {
                if (onUIThread && mView.isActive()) {
                    if (result.isOk()) {
                        mView.showSuccessEdit();
                    } else {
                        mView.showError(result.getErrMsg());
                    }
                }
            }
        });
    }

}
