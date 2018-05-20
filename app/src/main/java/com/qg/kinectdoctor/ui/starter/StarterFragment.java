package com.qg.kinectdoctor.ui.starter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.qg.kinectdoctor.R;
import com.qg.kinectdoctor.activity.App;
import com.qg.kinectdoctor.fragment.BaseFragment;
import com.qg.kinectdoctor.ui.login.LoginActivity;
import com.qg.kinectdoctor.ui.register.RegisterFirstActivity;
import com.qg.kinectdoctor.util.ServerIpGetter;
import com.qg.kinectdoctor.util.ToastUtil;

import static com.qg.kinectdoctor.util.Preconditions.checkNotNull;

/**
 * Created by TZH on 2016/10/27.
 */

public class StarterFragment extends BaseFragment implements StarterContract.View {

    private StarterContract.Presenter mPresenter;

    public static StarterFragment newInstance() {
        return new StarterFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(StarterContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.starter_fragment, container, false);

        // Set up login button.
        View lb = root.findViewById(R.id.login);
        lb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login();
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
        View bg = root.findViewById(R.id.backGround);
        bg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                final EditText editText = new EditText(getActivity());
                editText.setHint(ServerIpGetter.getIpPort());
                dialog.setTitle("服务器IP、Port")
                        .setView(editText)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ServerIpGetter.setIpPort(editText.getText().toString());
                                ToastUtil.showToast(getActivity(), "已保存");
                                dialogInterface.dismiss();
                            }
                        })
                        .show();

                return true;
            }
        });
        return root;
    }

    @Override
    public void showLogin() {
        LoginActivity.start(getContext());
    }

    @Override
    public void showRegister() {
        RegisterFirstActivity.start(getContext());
    }

}
