package com.qg.kinectdoctor.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.qg.kinectdoctor.R;
import com.qg.kinectdoctor.activity.App;
import com.qg.kinectdoctor.activity.NewRecordsActivity;
import com.qg.kinectdoctor.activity.RecordsDetailActivity;
import com.qg.kinectdoctor.adapter.MyRecyclerAdapter;
import com.qg.kinectdoctor.listener.AppBarStateChangeListener;
import com.qg.kinectdoctor.logic.LogicHandler;
import com.qg.kinectdoctor.logic.LogicImpl;
import com.qg.kinectdoctor.model.DUser;
import com.qg.kinectdoctor.model.MedicalRecord;
import com.qg.kinectdoctor.param.GetMRParam;
import com.qg.kinectdoctor.param.LoginParam;
import com.qg.kinectdoctor.result.GetMRResult;
import com.qg.kinectdoctor.result.LoginResult;
import com.qg.kinectdoctor.view.DividerItemDecoration;

import java.util.ArrayList;

/**
 * Created by 攀登者 on 2016/10/28.
 */
public class PatientFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "PatientFragment";
    private View view;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private TextView name, age_department;
    private ArrayList<MedicalRecord> medicalRecordList;
    private MyRecyclerAdapter mAdapter;
    private final static int RECORDS_DETAIL = 0; // 病历详情
    private final static int NEW_RECORDS = 1; // 创建病历

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pacient, container, false);
        test();
//        initUI();
//        initData();
        return view;
    }

    private void test() {
        DUser user = App.getInstance().getUser();
        LoginParam param = new LoginParam(user.getPhone(), user.getPassword());
//        LoginParam param = new LoginParam("15222223333", "qgmobile");
//        LoginParam param = new LoginParam("13549991585", "qgmobile");
        LogicImpl.getInstance().login(param, new LogicHandler<LoginResult>() {
            @Override
            public void onResult(LoginResult result, boolean onUIThread) {
                if (result.isOk() && onUIThread) {
                    App.getInstance().setUser(result.getdUser());

                    initUI();
                    initData();
                } else if (!result.isOk() && onUIThread) {
                    Toast.makeText(getActivity(), "登录失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void initData() {
        Log.e(TAG, "init");
        medicalRecordList = new ArrayList<>();
        // 获取联系人列表
        GetMRParam param = new GetMRParam();
        param.dUserId = App.getInstance().getUser().getId();
        LogicImpl.getInstance().GetMR(param, new LogicHandler< GetMRResult>() {

            @Override
            public void onResult(GetMRResult result, boolean onUIThread) {
                if(result.isOk() && onUIThread) {
                    medicalRecordList.addAll(result.medicalRecords);
                    mAdapter = new MyRecyclerAdapter(getActivity(), medicalRecordList);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, MedicalRecord data) {
//                            Intent intent = new Intent(getActivity(), RecordsDetailActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("data", data);
//                            intent.putExtras(bundle);
//                            startActivityForResult(intent, RECORDS_DETAIL);
                            RecordsDetailActivity.start(getActivity(), RECORDS_DETAIL, data);
                        }
                    });
                } else if(onUIThread && !result.isOk()) {
                    Toast.makeText(getActivity(), R.string.getMRfail, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // super.onSaveInstanceState(outState);
    }

    private void initUI() {
        name = (TextView) view.findViewById(R.id.name);
        age_department = (TextView) view.findViewById(R.id.age_department);
        // 設置年齡科室
        name.setText(App.getInstance().getUser().getName());
        age_department.setText(App.getInstance().getUser().getAge() + "，"  + App.getInstance().getUser().getDepartment());

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Log.d("", "fab");
                NewRecordsActivity.start(getActivity(), NEW_RECORDS);
                break;
            default:

                break;
        }
    }
}
