package com.qg.kinectdoctor.ui.information.base;

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
public class BaseInfoPresenter implements BaseInfoContract.Presenter {

    private BaseInfoContract.View mView;

    private final int mDoctorId;

    public BaseInfoPresenter(int doctorId, BaseInfoContract.View view) {
        mDoctorId = doctorId;
        mView = checkNotNull(view);
        mView.setPresenter(this);
    }


    @Override
    public void start() {
        DUser user = App.getInstance().getUser();
        mView.setName(user.getName());
        mView.setAge(user.getAge());
        mView.setSex(user.getSex() != 0 ? "男" : "女");
    }

    @Override
    public void saveBaseInfo(final String name, final int age, final String sex) {
        DUser user = App.getInstance().getUser();
        user.setName(name);
        user.setAge(age);
        user.setSex(sex.equals("男") ? 1 : 0);
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
