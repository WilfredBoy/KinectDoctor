package com.qg.kinectdoctor.util;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.qg.kinectdoctor.activity.App;
import com.qg.kinectdoctor.result.Result;

/**
 * Created by ZH_L on 2016/10/22.
 */
public class ToastUtil {

    private static Toast mToast = null;

    public static void showToast(Context context, String str){
        if(mToast == null){
            mToast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(str);
        }
        mToast.show();
    }

    public static void showToast(Context context, @StringRes int strId){
        showToast(context, context.getResources().getString(strId));
    }

    public static void showResultErrorToast(Result r){
        showToast(App.getInstance(), r.getErrMsg());
    }

    public static void showToast2(Context context, String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    public static void showToast2(Context context, int s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
