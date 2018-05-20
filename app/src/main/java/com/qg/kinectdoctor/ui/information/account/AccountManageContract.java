package com.qg.kinectdoctor.ui.information.account;

import com.qg.kinectdoctor.ui.BasePresenter;
import com.qg.kinectdoctor.ui.BaseView;

/**
 * @deprecated
 */
public class AccountManageContract {
    interface View extends BaseView<Presenter> {
        void showPhone(String phone);

        void showInputPassword(int doctorId);

        void showEditPassword(int doctorId);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void editPassword();
    }
}
