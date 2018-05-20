package com.qg.kinectdoctor.ui.starter;

import com.qg.kinectdoctor.ui.BasePresenter;
import com.qg.kinectdoctor.ui.BaseView;

/**
 * Created by TZH on 2016/10/27.
 */

public class StarterContract {

    interface View extends BaseView<Presenter> {
        void showLogin();
        void showRegister();
    }

    interface Presenter extends BasePresenter {
        void login();
        void register();
    }
}
