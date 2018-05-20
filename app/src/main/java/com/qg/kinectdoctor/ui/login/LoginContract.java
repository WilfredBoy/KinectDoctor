package com.qg.kinectdoctor.ui.login;

import com.qg.kinectdoctor.model.DUser;
import com.qg.kinectdoctor.ui.BasePresenter;
import com.qg.kinectdoctor.ui.BaseView;

/**
 * Created by TZH on 2016/10/27.
 */
public class LoginContract {

    interface View extends BaseView<Presenter> {
        void showInputError();

        void showError(String error);

        void setPhone(String phone);

        void setPassword(String password);

        void setPasswordVisibility(boolean visible);

        void showMain(DUser dUser);

        void showRegister();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void login(String phone, String password, boolean rememberPassword);

        void register();
    }
}
