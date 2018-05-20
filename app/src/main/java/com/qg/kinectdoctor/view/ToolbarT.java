package com.qg.kinectdoctor.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qg.kinectdoctor.R;

import static com.qg.kinectdoctor.util.Preconditions.checkNotNull;

/**
 * A Toolbar with center title.
 * Created by TZH on 2016/10/28.
 */
public class ToolbarT extends Toolbar {
    private TextView mTitleTextView;

    private CharSequence mTitleText;
    private int mTitleTextAppearance;
    private int mTitleTextColor;
    private float mTitleTextSize = 16;

    public ToolbarT(Context context) {
        this(context, null);
    }

    public ToolbarT(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.toolbarStyle);
    }

    public ToolbarT(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTitleTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
    }

    @Override
    public void setTitle(@StringRes int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            if (mTitleTextView == null) {
                final Context context = getContext();
                mTitleTextView = new AppCompatTextView(context);
                mTitleTextView.setSingleLine();
                mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                if (mTitleTextSize != 0) {
                    mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize);
                }
                if (mTitleTextAppearance != 0) {
                    TextViewCompat.setTextAppearance(mTitleTextView, mTitleTextAppearance);
                }
                if (mTitleTextColor != 0) {
                    mTitleTextView.setTextColor(mTitleTextColor);
                }
            }
            if (!isChild(mTitleTextView)) {
                addDefaultView(mTitleTextView);
            }
        } else if (mTitleTextView != null && isChild(mTitleTextView)) {
            removeView(mTitleTextView);
        }
        if (mTitleTextView != null) {
            mTitleTextView.setText(title);
        }
        mTitleText = title;
    }

    private void addDefaultView(View v) {
        final ViewGroup.LayoutParams vlp = v.getLayoutParams();
        final LayoutParams lp;
        if (vlp == null) {
            lp = generateDefaultLayoutParams();
        } else if (!checkLayoutParams(vlp)) {
            lp = generateLayoutParams(vlp);
        } else {
            lp = (LayoutParams) vlp;
        }
        lp.gravity = Gravity.CENTER;

        addView(v, lp);
    }

    private boolean isChild(View child) {
        return child.getParent() == this;
    }

    public void setDefaultUpButton(final Activity activity) {
        setLeftImage(R.drawable.back_selector, new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
    }

    public void setLeftImage(@DrawableRes int resId, @Nullable OnClickListener listener) {
        setNavigationIcon(resId);
        setNavigationOnClickListener(listener);
    }

    public void setRightListener(@Nullable OnClickListener listener) {
        View right = checkNotNull(findViewById(R.id.right), "There is no child with id:right in ToolBarT.");
        right.setOnClickListener(listener);
    }

    @Override
    public CharSequence getTitle() {
        return mTitleText;
    }

    @Override
    public void setTitleTextColor(@ColorInt int color) {
        mTitleTextColor = color;
        if (mTitleTextView != null) {
            mTitleTextView.setTextColor(color);
        }
    }

    /**
     * Set the title text size to a given unit and value.  See {@link
     * TypedValue} for the possible dimension units.
     *
     * @param unit          The desired dimension unit.
     * @param titleTextSize The desired size in the given units.
     */
    public void setTitleTextSize(int unit, float titleTextSize) {
        mTitleTextSize = titleTextSize;
        mTitleTextView.setTextSize(unit, titleTextSize);
    }

    @Override
    public void setTitleTextAppearance(Context context, @StyleRes int resId) {
        mTitleTextAppearance = resId;
        TextViewCompat.setTextAppearance(mTitleTextView, resId);
    }
}
