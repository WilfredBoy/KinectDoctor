package com.qg.kinectdoctor.ui.register;

import com.qg.kinectdoctor.ui.BasePresenter;
import com.qg.kinectdoctor.ui.BaseView;

/**
 * Created by TZH on 2016/10/27.
 */
public class RegisterContract {

    interface View1 extends BaseView<Presenter1> {
        void showPhoneError();

        void hidePhoneError();

        void showPasswordError();

        void hidePasswordError();

        void setPasswordVisibility(boolean visible);

        void showLogin();

        void showNext(String phone, String password);

        boolean isActive();
    }

    interface Presenter1 extends BasePresenter {
        void next(String phone, String password);

        void login();

        void result(int requestCode, int resultCode);
    }


    interface View2 extends BaseView<Presenter2> {
        void setUploadingIndicator(boolean active);

        void showError(String error);

        void showEmptyError();

        void showSuccess();

        void showLogin();

        boolean isActive();
    }

    interface Presenter2 extends BasePresenter {
        void register(String hospital, String clinicDepartment, String jobTitle);
    }}
