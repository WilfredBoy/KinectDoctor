package com.qg.kinectdoctor.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qg.kinectdoctor.R;


/**
 * Created by 攀登者 on 2016/10/25.
 */
public class TopbarZ extends LinearLayout {

    private Context mContext;
    private TextView title, rText, lText;
    private FrameLayout flCenter;
    private ImageButton leftBt, rightBt;
    private View mView;

    public TopbarZ(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initUI();
    }

    private void initUI() {
        try {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            mView = inflater.inflate(R.layout.topbarz, null);
            if (null != mView) {
                LayoutParams param = new LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//				param.topMargin = getPaddingTop();
//				setBackgroundDrawable(mView.getBackground());
                addView(mView, param);
                title = (TextView) mView.findViewById(R.id.tv_title);
                leftBt = (ImageButton) mView.findViewById(R.id.bt_left);
                rightBt = (ImageButton) mView.findViewById(R.id.bt_right);
                rText = (TextView) mView.findViewById(R.id.tv_right);
                lText = (TextView) mView.findViewById(R.id.tv_left);
                flCenter = (FrameLayout) mView.findViewById(R.id.fl_center);
                mView.setPadding(0, 0, 0, 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean setTitleVisiable(boolean visible) {
        if (visible)
            title.setVisibility(View.VISIBLE);
        else
            title.setVisibility(View.INVISIBLE);
        return visible;
    }

    public void setTitle(boolean visible, int resid, OnClickListener ocl) {
        if (setTitleVisiable(visible)) {
            title.setText(resid);
            if (null != ocl) {
                title.setClickable(true);
                title.setOnClickListener(ocl);
            }
        }
    }

    public void setTitle(boolean visible, String text, OnClickListener ocl) {
        if (setTitleVisiable(visible)) {
            title.setText(text);
            if (null != ocl) {
                title.setClickable(true);
                title.setOnClickListener(ocl);
            }
        }
    }

    public boolean setInputVisiable(boolean visible) {
        if (visible)
            flCenter.setVisibility(View.VISIBLE);
        else
            flCenter.setVisibility(View.INVISIBLE);
        return visible;
    }

    public void setCenterView(boolean visible, View view) {
        if (setInputVisiable(visible)) {
            flCenter.removeAllViews();
            flCenter.addView(view, android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    public void setCenterView(boolean visible, View view, OnClickListener ocl) {
        if (setInputVisiable(visible)) {
            flCenter.removeAllViews();
            flCenter.addView(view, android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
            if (null != ocl) {
                view.setOnClickListener(ocl);
            }
        }
    }

    public boolean setLeftButtonVisible(boolean visible) {
        if (visible)
            leftBt.setVisibility(View.VISIBLE);
        else
            leftBt.setVisibility(View.INVISIBLE);
        return visible;
    }

    public void setLeftButton(boolean visible, int resid, OnClickListener ocl) {
        if (setLeftButtonVisible(visible)) {
            leftBt.setImageResource(resid);
            if (null != ocl)
                leftBt.setOnClickListener(ocl);
        }
    }

    public boolean setRightButtonVisible(boolean visible) {
        if (visible)
            rightBt.setVisibility(View.VISIBLE);
        else
            rightBt.setVisibility(View.INVISIBLE);
        return visible;
    }

    public void setRightButton(boolean visible, int resid, OnClickListener ocl) {
        if (setRightButtonVisible(visible)) {
            rightBt.setImageResource(resid);
            if (null != ocl)
                rightBt.setOnClickListener(ocl);
        }
    }

    public boolean setRightTextVisible(boolean visible) {
        if (visible)
            rText.setVisibility(View.VISIBLE);
        else
            rText.setVisibility(View.INVISIBLE);
        return visible;
    }


    public void setLeftText(Boolean visible, int resid, OnClickListener ocl) {
        if (setRightTextVisible(visible)) {
            lText.setText(resid);
            if (null != ocl) {
                lText.setClickable(true);
                lText.setOnClickListener(ocl);
            }
        }
    }

    public void setRightText(Boolean visible, int resid, OnClickListener ocl) {
        if (setRightTextVisible(visible)) {
            rText.setText(resid);
            if (null != ocl) {
                rText.setClickable(true);
                rText.setOnClickListener(ocl);
            }
        }
    }

    public void setRightText(Boolean visible, String text, OnClickListener ocl) {
        if (setRightTextVisible(visible)) {
            rText.setText(text == null ? "" : text);
            if (null != ocl) {
                rText.setClickable(true);
                rText.setOnClickListener(ocl);
            }
        }
    }

    public void setRightTextColor(int color) {
        rText.setTextColor(color);
    }

    // android4.4以上版本使用透明状态栏
    public static int getPaddingHeight(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return getStatusBarHeight(v);
        }
        return 0;
    }

    // A method to find height of the status bar
    public static int getStatusBarHeight(View v) {
        int result = 0;
        int resourceId = v.getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = v.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
