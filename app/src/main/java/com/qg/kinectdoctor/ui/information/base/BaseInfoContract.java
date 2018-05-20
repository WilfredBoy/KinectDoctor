package com.qg.kinectdoctor.ui.information.base;

import com.qg.kinectdoctor.ui.BasePresenter;
import com.qg.kinectdoctor.ui.BaseView;

/**
 * Created by TZH on 2016/10/29.
 */

public class BaseInfoContract {
    interface View extends BaseView<Presenter> {
        void setName(String name);

        void setAge(int age);

        void setSex(String sex);

        void showEditAge(int age);

        void showError(String error);

        void showEditSex();

        void showSuccessEdit();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void saveBaseInfo(String name, int age, String sex);
    }
}
