package com.qg.kinectdoctor.ui.information.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qg.kinectdoctor.R;
import com.qg.kinectdoctor.fragment.BaseFragment;
import com.qg.kinectdoctor.util.NumberUtil;
import com.qg.kinectdoctor.util.ToastUtil;
import com.qg.kinectdoctor.view.AgeSexChooseDialogBuilder;
import com.qg.kinectdoctor.view.ToolbarT;

import static com.qg.kinectdoctor.util.Preconditions.checkNotNull;

/**
 * Created by TZH on 2016/10/29.
 */

public class BaseInfoFragment extends BaseFragment implements BaseInfoContract.View, AgeSexChooseDialogBuilder.OnSelectListener {

    private BaseInfoContract.Presenter mPresenter;

    private TextView mNameText;

    private TextView mAgeText;

    private TextView mSexText;

    private View mAge;

    private View mSex;

    public static BaseInfoFragment newInstance() {
        Bundle args = new Bundle();

        BaseInfoFragment fragment = new BaseInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(BaseInfoContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.base_info_fragment, container, false);

        mNameText = (TextView) root.findViewById(R.id.name_text);
        mAgeText = (TextView) root.findViewById(R.id.age_text);
        mSexText = (TextView) root.findViewById(R.id.sex_text);

        mAge = root.findViewById(R.id.age);
        mAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditAge(getAge());
            }
        });

        mSex = root.findViewById(R.id.sex);
        mSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditSex();
            }
        });

        // Set up toolbar.
        ToolbarT toolbar = (ToolbarT) root.findViewById(R.id.toolbar);
        toolbar.setDefaultUpButton(getActivity());
        toolbar.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.saveBaseInfo(
                        mNameText. getText().toString(),
                        NumberUtil.parseInt(mAgeText.getText().toString(), 0),
                        mSexText.getText().toString()
                );
            }
        });

        return root;
    }

    private int getAge() {
        return Integer.valueOf(mAgeText.getText().toString());
    }

    @Override
    public void setName(String name) {
        mNameText.setText(name);
    }

    @Override
    public void setAge(int age) {
        mAgeText.setText(String.valueOf(age));
    }

    @Override
    public void setSex(String sex) {
        mSexText.setText(sex);
    }

    @Override
    public void showError(String error) {
        ToastUtil.showToast2(getContext(), error);
    }

    private AgeSexChooseDialogBuilder mBuilder;
    @Override
    public void showEditAge(int age) {
        mBuilder.showAgeDialog();
    }

    @Override
    public void showEditSex() {
        mBuilder.showSexDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
        mBuilder = new AgeSexChooseDialogBuilder(getContext());
        mBuilder.setOnSelectListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mBuilder.uninstall();
        mBuilder = null;
    }

    @Override
    public void showSuccessEdit() {
        ToastUtil.showToast2(getContext(), R.string.edit_success);
        getActivity().finish();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void selectSex(String sex) {
        mSexText.setText(sex);
    }

    @Override
    public void selectAge(int age) {
        mAgeText.setText(String.valueOf(age));
    }
}
