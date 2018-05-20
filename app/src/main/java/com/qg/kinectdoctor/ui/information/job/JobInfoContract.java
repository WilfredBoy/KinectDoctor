package com.qg.kinectdoctor.ui.information.job;

import com.qg.kinectdoctor.ui.BasePresenter;
import com.qg.kinectdoctor.ui.BaseView;

/**
 * Created by TZH on 2016/10/29.
 */
class JobInfoContract {
    interface View extends BaseView<Presenter> {
        void setHospital(String hospital);

        void setDepartment(String department);

        void setJobTitle(String jobTitle);

        void showError(String error);

        void showSuccessEdit();

        boolean isActive();
   }

    interface Presenter extends BasePresenter {
        void saveJobInfo(String hospital, String department, String jobTitle);
    }
}
