package com.qg.kinectdoctor.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qg.kinectdoctor.R;
import com.qg.kinectdoctor.fragment.BaseFragment;
import com.qg.kinectdoctor.ui.login.LoginActivity;

import static com.qg.kinectdoctor.util.Preconditions.checkNotNull;

public class RegisterFirstFragment extends BaseFragment implements RegisterContract.View1 {

    private RegisterContract.Presenter1 mPresenter;

    private TextView mPhone;

    private TextView mPassword;

    private TextView mPhoneError;

    private TextView mPasswordError;

    public static RegisterFirstFragment newInstance() {
        return new RegisterFirstFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(RegisterContract.Presenter1 presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.register_first_fragment, container, false);
        mPhone = (TextView) root.findViewById(R.id.phone_input);
        mPassword = (TextView) root.findViewById(R.id.password_input);
        mPhoneError = (TextView) root.findViewById(R.id.phone_format_error);
        mPasswordError = (TextView) root.findViewById(R.id.password_format_error);

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
                        // Hide password.
                        setPasswordVisibility(false);
                        return true;
                }
                return false;
            }
        });

        // Set up register button.
        View rb = root.findViewById(R.id.register);
        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.next(
                        mPhone.getText().toString(),
                        mPassword.getText().toString()
                );
            }
        });

        View pb = root.findViewById(R.id.login);
        pb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login();
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public void showPhoneError() {
        mPhoneError.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePhoneError() {
        mPhoneError.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showPasswordError() {
        mPasswordError.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePasswordError() {
        mPasswordError.setVisibility(View.INVISIBLE);
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
    public void showLogin() {
        getActivity().finish();
        LoginActivity.start(getContext());
    }

    @Override
    public void showNext(String phone, String password) {
        RegisterSecondActivity.startForResult(this, RegisterFirstActivity.REQUEST_REGISTER, phone, password);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

}
