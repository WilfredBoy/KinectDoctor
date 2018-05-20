package com.qg.kinectdoctor.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;

import com.qg.kinectdoctor.R;

import java.lang.reflect.Field;

/**
 * Created by TZH on 2016/10/31.
 */

public class AgeSexChooseDialogBuilder implements View.OnClickListener, NumberPicker.OnValueChangeListener, NumberPicker.OnScrollListener, NumberPicker.Formatter {

    private MyNumberPicker mNumberPicker;

    private AlertDialog mAgeDialog;
    private AlertDialog mSexDialog;
    private Context mContext;
    private boolean isInit;
    private OnSelectListener mOnSelectListener;
    private int mAge;

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public void uninstall() {
        setOnSelectListener(null);
        if (isInit) {
            mAgeDialog.dismiss();
            mSexDialog.dismiss();
        }
    }

    public interface OnSelectListener {
        void selectSex(String sex);

        void selectAge(int age);
    }

    public AgeSexChooseDialogBuilder(Context context) {
        mContext = context;
    }

    public void showSexDialog() {
        init();
        mSexDialog.show();
    }

    public void showAgeDialog() {
        init();
        mAgeDialog.show();
    }

    void init() {
        if (isInit) {
            return;
        }
        isInit = true;
        // 年龄选择
        View view = LayoutInflater.from(mContext).inflate(R.layout.age_choose, null);
        mNumberPicker = (MyNumberPicker) view.findViewById(R.id.age_choose);
        view.findViewById(R.id.cancle).setOnClickListener(this);
        view.findViewById(R.id.sure).setOnClickListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.DialogTheme);
        builder.setView(view);
        mAgeDialog = builder.create();
        Window window = mAgeDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialog_animation);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        initMyNumberPicker();

        // 性别
        View view_sex = LayoutInflater.from(mContext).inflate(R.layout.sex_choose, null);
        view_sex.findViewById(R.id.male).setOnClickListener(this);
        view_sex.findViewById(R.id.female).setOnClickListener(this);
        view_sex.findViewById(R.id.cancle_sex).setOnClickListener(this);
        AlertDialog.Builder builder_sex = new AlertDialog.Builder(mContext, R.style.DialogTheme);
        builder_sex.setView(view_sex);
        mSexDialog = builder_sex.create();
        Window window_sex = mSexDialog.getWindow();
        window_sex.setGravity(Gravity.BOTTOM);
        window_sex.setWindowAnimations(R.style.dialog_animation);
        window_sex.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp_sex = window_sex.getAttributes();
        lp_sex.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp_sex.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window_sex.setAttributes(lp_sex);
    }

    private void initMyNumberPicker() {
        setNumberPickerDividerColor(mNumberPicker);
        mNumberPicker.setFormatter(this);
        mNumberPicker.setOnValueChangedListener(this);
        mNumberPicker.setOnScrollListener(this);
        mNumberPicker.setMaxValue(300);
        mNumberPicker.setMinValue(0);
        mNumberPicker.setValue(0);
    }

    private void setNumberPickerDividerColor(NumberPicker picker) {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //设置分割线的颜色值
                    pf.set(picker, new ColorDrawable(ContextCompat.getColor(mContext, R.color.xiahuaxian)));
                } catch (IllegalArgumentException | Resources.NotFoundException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        mAge = newVal;
    }

    @Override
    public String format(int value) {
        String tmpStr = String.valueOf(value);
        if (value < 10) {
            tmpStr = "0" + tmpStr;
        }
        return tmpStr;
    }

    @Override
    public void onScrollStateChange(NumberPicker view, int scrollState) {
        switch (scrollState) {
            case NumberPicker.OnScrollListener.SCROLL_STATE_FLING:
                // 手离开之后还在滑动
                break;
            case NumberPicker.OnScrollListener.SCROLL_STATE_IDLE:
                // 不滑动
                break;
            case NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                // 滑动中
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancle:
                mAgeDialog.dismiss();
                break;
            case R.id.sure:
                mOnSelectListener.selectAge(mAge);
                mAgeDialog.dismiss();
                break;
            case R.id.male:
                mOnSelectListener.selectSex(mContext.getString(R.string.male));
                mSexDialog.dismiss();
                break;
            case R.id.female:
                mOnSelectListener.selectSex(mContext.getString(R.string.female));
                mSexDialog.dismiss();
                break;
            case R.id.cancle_sex:
                mSexDialog.dismiss();
                break;
        }
    }
}
