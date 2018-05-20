package com.qg.kinectdoctor.ui.information;

import com.qg.kinectdoctor.ui.BasePresenter;
import com.qg.kinectdoctor.ui.BaseView;

/**
 * Created by TZH on 2016/10/26.
 */

class PersonalInfoContract {
    interface View extends BaseView<Presenter> {
        void showName(String name);

        void showInfo(String info);

        void showBaseInfo(int doctorId);

        void showJobInfo(int doctorId);

        void showAccountManage(int doctorId);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void baseInfo();

        void jobInfo();

        void manageAccount();
    }
}
