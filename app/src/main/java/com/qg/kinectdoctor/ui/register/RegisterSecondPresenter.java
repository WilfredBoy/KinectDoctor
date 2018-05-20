package com.qg.kinectdoctor.ui.register;

import android.text.TextUtils;

import com.qg.kinectdoctor.logic.LogicHandler;
import com.qg.kinectdoctor.logic.LogicImpl;
import com.qg.kinectdoctor.param.RegisterParam;
import com.qg.kinectdoctor.result.RegisterResult;
import com.qg.kinectdoctor.util.ToastUtil;

import static android.text.TextUtils.isEmpty;
import static com.qg.kinectdoctor.util.Preconditions.checkNotNull;

/**
 * Created by TZH on 2016/10/27.
 */
public class RegisterSecondPresenter implements RegisterContract.Presenter2 {

    private final RegisterContract.View2 mRegisterSecondView;

    private final String mPhone;

    private final String mPassword;

    public RegisterSecondPresenter(RegisterContract.View2 registerSecondView, String phone, String password) {
        mPhone = checkNotNull(phone);
        mPassword = checkNotNull(password);
        mRegisterSecondView = checkNotNull(registerSecondView, "registerFirstView cannot be null!");
        mRegisterSecondView.setPresenter(this);
    }

    @Override
    public void start() {
        // Do nothing
    }

    @Override
    public void register(String hospital, String clinicDepartment, String jobTitle) {
        if (!checkParameters(hospital, clinicDepartment, jobTitle)) {
            mRegisterSecondView.showEmptyError();
            return;
        }
        mRegisterSecondView.setUploadingIndicator(true);
        LogicImpl.getInstance().register(new RegisterParam(mPhone, mPassword, hospital, clinicDepartment, jobTitle), new LogicHandler<RegisterResult>() {
            @Override
            public void onResult(RegisterResult result, boolean onUIThread) {
                if (onUIThread) {
                    if (!mRegisterSecondView.isActive()) {
                        return;
                    }
                    mRegisterSecondView.setUploadingIndicator(false);
                    if (result.isOk()) {
                        mRegisterSecondView.showSuccess();
                        mRegisterSecondView.showLogin();
                    } else {
                        mRegisterSecondView.showError(result.getErrMsg());
                    }
                }
            }
        });
    }

    private boolean checkParameters(String hospital, String clinicDepartment, String jobTitle) {
        return !isEmpty(hospital) && !isEmpty(clinicDepartment) && !isEmpty(jobTitle);
    }

}
