package com.qg.kinectdoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;

import com.qg.kinectdoctor.R;
import com.qg.kinectdoctor.fragment.ChatListFragment;
import com.qg.kinectdoctor.fragment.PatientFragment;
import com.qg.kinectdoctor.ui.information.PersonalInfoFragment;

/**
 * Created by 攀登者 on 2016/10/28.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {
    public static final String ACTION_CHECK_UNREADMSG = "com.qg.onlinemedicineforpatient.ACTION_CHECK_UNREADMSG";

    private static final String TAG = "HomeActivity";
    private static final int REQUEST_BUY_VIP = 0xff;
    private PatientFragment mPatientFragment;
    private ChatListFragment mMessageFragment;
    private PersonalInfoFragment mPersonalInfoFragment;
    private final static int RECORDS_DETAIL = 0; // 病历详情
    private final static int NEW_RECORDS = 1; // 创建病历

    private ImageButton pacient, message, me;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        fragmentManager = getSupportFragmentManager();
        setTabSelection(1);
        setTabSelection(2);
        setTabSelection(0);
    }

    private void initViews() {
        pacient = (ImageButton) findViewById(R.id.pacient);
        message = (ImageButton) findViewById(R.id.message);
        me = (ImageButton) findViewById(R.id.me);
        pacient.setImageResource(R.drawable.pacient_click);
        message.setImageResource(R.drawable.me_normal);
        me.setImageResource(R.drawable.me_normal);
        pacient.setOnClickListener(this);
        message.setOnClickListener(this);
        me.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pacient:
                setTabSelection(0);
                break;
            case R.id.message:
                setTabSelection(1);
                break;
            case R.id.me:
                setTabSelection(2);
                break;
            default:
                break;
        }
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index 每个tab页对应的下标。
     */
    private void setTabSelection(int index) {
        clearSelection();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                pacient.setImageResource(R.drawable.pacient_click);
                if (mPatientFragment == null) {
                    mPatientFragment = new PatientFragment();
                    transaction.add(R.id.fragmentlayout, mPatientFragment);
                } else {
                    transaction.show(mPatientFragment);
                }
                break;
            case 1:
                message.setImageResource(R.drawable.message_click_null);
                if (mMessageFragment == null) {
                    mMessageFragment = new ChatListFragment();
                    transaction.add(R.id.fragmentlayout, mMessageFragment);
                } else {
                    transaction.show(mMessageFragment);
                }
                break;
            case 2:
                me.setImageResource(R.drawable.me_click);
                if (mPersonalInfoFragment == null) {
                    mPersonalInfoFragment = PersonalInfoFragment.newInstanceWithPresenter();
                    transaction.add(R.id.fragmentlayout, mPersonalInfoFragment);
                } else {
                    transaction.show(mPersonalInfoFragment);
                }
                break;
            default:

                break;
        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        pacient.setImageResource(R.drawable.pacient_normal);
        message.setImageResource(R.drawable.message_normal_null);
        me.setImageResource(R.drawable.me_normal);
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (mPatientFragment != null) {
            transaction.hide(mPatientFragment);
        }
        if (mMessageFragment != null) {
            transaction.hide(mMessageFragment);
        }
        if (mPersonalInfoFragment != null) {
            transaction.hide(mPersonalInfoFragment);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case RECORDS_DETAIL:
                mPatientFragment.initData();
                break;
            case NEW_RECORDS:
                mPatientFragment.initData();
                break;
            default:
                break;
        }
    }
}