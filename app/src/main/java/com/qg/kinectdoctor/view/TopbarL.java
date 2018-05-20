package com.qg.kinectdoctor.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringDef;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qg.kinectdoctor.R;

/**
 * Created by ZH_L on 2016/10/25.
 */
public class TopbarL extends LinearLayout{
    private static final String TAG = TopbarL.class.getSimpleName();
    private Context context;
    private TextView cTv;
    private ImageView lIv,rIv;

    public TopbarL(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init(){
       LayoutInflater.from(context).inflate(R.layout.topbarl,this, true);
        lIv = (ImageView)findViewById(R.id.left_iv);
        cTv = (TextView)findViewById(R.id.center_tv);
        rIv = (ImageView)findViewById(R.id.right_iv);
    }


    public void setLeftImage(boolean visible, @DrawableRes int drawId, OnClickListener ocl){
        lIv.setImageResource(drawId);
        setLeftVisible(visible);
        lIv.setOnClickListener(ocl);
    }

    public void setLeftVisible(boolean visible){
        if(visible){
            lIv.setVisibility(View.VISIBLE);
        }else{
           lIv.setVisibility(View.GONE);
        }
    }

    public void setRightImage(boolean visible, @DrawableRes int drawId, OnClickListener ocl){
        rIv.setImageResource(drawId);
        setRightVisible(visible);
        rIv.setOnClickListener(ocl);
    }

    public void setRightVisible(boolean visible){
        if(visible){
            rIv.setVisibility(View.VISIBLE);
        }else{
            rIv.setVisibility(View.GONE);
        }
    }

    public void setCenterText(boolean visible, int strId, OnClickListener ocl){
        setCenterText(visible, context.getResources().getString(strId), ocl);
    }

    public void setCenterText(boolean visible, String text, OnClickListener ocl){
        cTv.setText(text);
        setCenterVisible(visible);
        cTv.setOnClickListener(ocl);
    }

    public void setCenterVisible(boolean visible){
        if(visible){
            cTv.setVisibility(View.VISIBLE);
        }else{
            cTv.setVisibility(View.GONE);
        }
    }
}
