package com.qg.kinectdoctor.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.SharedPreferencesCompat;

import com.qg.kinectdoctor.activity.App;
import com.qg.kinectdoctor.model.DUser;
import com.qg.kinectdoctor.result.UpdateDUserResult;

/**
 * Created by TZH on 2016/10/30.
 */
public class UserRepository {

    private static final String SP_NAME = "fjxciapqweklzxcmklhjlavl";
    private static final String SP_UN = "fjxciapqwekalmxcmklhjlavl";
    private static final String SP_PW = "fjxciapqwekalzxcmklhjlavl";
    private static UserRepository INSTANCE;
    private SharedPreferences mSharedPreferences;

    private UserRepository(Context context) {
        mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static UserRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository(App.getInstance());
        }
        return INSTANCE;
    }

    public void clear() {
        mSharedPreferences.edit().remove(SP_PW).remove(SP_UN).apply();
    }

    public void saveUser(String phone, String password) {
        mSharedPreferences.edit().putString(SP_UN, phone).putString(SP_PW, password).apply();
    }

    public String getPassword() {
        return mSharedPreferences.getString(SP_PW, "");
    }

    public String getPhone() {
        return mSharedPreferences.getString(SP_UN, "");
    }
}
