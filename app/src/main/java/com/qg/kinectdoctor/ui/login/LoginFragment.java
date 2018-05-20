package com.qg.kinectdoctor.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.qg.kinectdoctor.R;
import com.qg.kinectdoctor.activity.HomeActivity;
import com.qg.kinectdoctor.activity.MainActivity;
import com.qg.kinectdoctor.fragment.BaseFragment;
import com.qg.kinectdoctor.model.DUser;
import com.qg.kinectdoctor.ui.information.PersonalInfoActivity;
import com.qg.kinectdoctor.ui.register.RegisterFirstActivity;
import com.qg.kinectdoctor.util.ToastUtil;

import static com.qg.kinectdoctor.util.Preconditions.checkNotNull;

/**
 * Main UI for the login screen.
 * Created by TZH on 2016/10/27.
 */
public class LoginFragment extends BaseFragment implements LoginContract.View {

    private LoginContract.Presenter mPresenter;

    private TextView mPhone;

    private TextView mPassword;

    private CheckBox mRememberPassword;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_fragment, container, false);
        mPhone = (TextView) root.findViewById(R.id.phone_input);
        mPassword = (TextView) root.findViewById(R.id.password_input);
        mRememberPassword = (CheckBox) root.findViewById(R.id.rmb_pwd_cb);

        // Set up see password view.
        View spv = root.findViewById(R.id.see_pwd);
        spv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Show password.
                        setPasswordVisibility(true);
                        return true;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_OUTSIDE:
                        // Hide password.
                        setPasswordVisibility(false);
                        return true;
                }
                return false;
            }
        });
        setPasswordVisibility(false);

        // Set up login button.
        View lb = root.findViewById(R.id.login);
        lb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login(
                        mPhone.getText().toString(),
                        mPassword.getText().toString(),
                        mRememberPassword.isChecked()
                );
            }
        });

        // Set up register button.
        View rb = root.findViewById(R.id.register);
        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.register();
            }
        });

        return root;
    }

    @Override
    public void showInputError() {
        ToastUtil.showToast2(getContext(), R.string.login_input_error);
    }

    @Override
    public void showError(String error) {
        ToastUtil.showToast2(getContext(), error);
    }

    @Override
    public void setPhone(String phone) {
        mPhone.setText(phone);
    }

    @Override
    public void setPassword(String password) {
        mPassword.setText(password);
    }

    @Override
    public void setPasswordVisibility(boolean visible) {
        if (visible) {
            mPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            mPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
        }
    }

    @Override
    public void showMain(DUser dUser) {
        Intent intent = new Intent(getContext(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void showRegister() {
        getActivity().finish();
        RegisterFirstActivity.start(getContext());
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

}
