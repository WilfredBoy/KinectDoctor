package com.qg.kinectdoctor.ui.starter;

import static com.qg.kinectdoctor.util.Preconditions.checkNotNull;

/**
 * Created by TZH on 2016/10/27.
 */
public class StarterPresenter implements StarterContract.Presenter {

    private final StarterContract.View mStarterView;

    public StarterPresenter(StarterContract.View starterView) {
        mStarterView = checkNotNull(starterView, "starterView cannot be null!");
        mStarterView.setPresenter(this);
    }

    @Override
    public void start() {
        // Do nothing.
    }

    @Override
    public void login() {
        mStarterView.showLogin();
    }

    @Override
    public void register() {
        mStarterView.showRegister();
    }
}
