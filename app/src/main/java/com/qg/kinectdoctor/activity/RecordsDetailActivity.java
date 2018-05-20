package com.qg.kinectdoctor.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.exceptions.HyphenateException;
import com.qg.kinectdoctor.R;
import com.qg.kinectdoctor.emsdk.IMManager;
import com.qg.kinectdoctor.logic.LogicHandler;
import com.qg.kinectdoctor.logic.LogicImpl;
import com.qg.kinectdoctor.model.MedicalRecord;
import com.qg.kinectdoctor.param.DelMRParam;
import com.qg.kinectdoctor.param.SetMRParam;
import com.qg.kinectdoctor.result.DelMRResult;
import com.qg.kinectdoctor.view.TopbarZ;

/**
 * Created by 攀登者 on 2016/10/26.
 */
public class RecordsDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "RecordsDetailActivity";
    private TopbarZ topbar;
    private TextView name, ageAndSex, allergyMedications, medicalCondition;
    private RelativeLayout rela_rehabilitation_programmes;
    private Button delete_records, add_records;
    private static MedicalRecord medicalRecord;
    private LinearLayout hadRecords;
    private static final int REQUEST_CODE_R = 4;
    private static final int RESULT_CODS_RE = 5;
    private final static int NEW_RECORDS = 1; // 创建病历

    public static void start(Activity context, int requestCode, MedicalRecord data) {
        medicalRecord = data;
        Intent intent = new Intent(context, RecordsDetailActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordsdetails);
        initUI();
    }

    private void initUI() {
        topbar = (TopbarZ) findViewById(R.id.topbar);
        topbar.setLeftButton(true, R.drawable.back_selector, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });
        topbar.setTitle(true, R.string.detail, null);
        topbar.setRightButton(true, R.drawable.message, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 聊天
                try{
                    IMManager.getInstance(RecordsDetailActivity.this).addContact(medicalRecord.getPphone());
                    Toast.makeText(RecordsDetailActivity.this, "已发起聊天请求，待病人确认", Toast.LENGTH_SHORT).show();
                }catch (HyphenateException e){
                    e.printStackTrace();
                }
            }
        });
        hadRecords = (LinearLayout) findViewById(R.id.had_records);
        name = (TextView) findViewById(R.id.name);
        ageAndSex = (TextView) findViewById(R.id.age_sex);
        allergyMedications = (TextView) findViewById(R.id.allergy_medications);
        medicalCondition = (TextView) findViewById(R.id.medical_condition);
        rela_rehabilitation_programmes = (RelativeLayout) findViewById(R.id.rela_rehabilitation_programmes);
        rela_rehabilitation_programmes.setOnClickListener(this);
        delete_records = (Button) findViewById(R.id.delete_records);
        add_records = (Button) findViewById(R.id.add_records);
        delete_records.setOnClickListener(this);
        add_records.setOnClickListener(this);

        // 设置名字，年龄，性别
        name.setText(medicalRecord.getPname());
        ageAndSex.setText(medicalRecord.getAge() + "，" + ((medicalRecord.getSex() == 1) ? "男" : "女"));
        allergyMedications.setText((medicalRecord.getAllergicDrug() == null) ? "无" : medicalRecord.getAllergicDrug());
        medicalCondition.setText((medicalRecord.getConditions() == null) ? "无" : medicalRecord.getConditions());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rela_rehabilitation_programmes:
                RehabilitationProgrammesActivity.start(RecordsDetailActivity.this, REQUEST_CODE_R, medicalRecord);
                break;
            case R.id.delete_records:
                deleteRecords();
                break;
            case R.id.add_records:
                addRecords();
                break;
        }
    }

    private void deleteRecords() {
        DelMRParam param = new DelMRParam();
        param.id = medicalRecord.getId();
        LogicImpl.getInstance().DelMR(param, new LogicHandler<DelMRResult>() {
            @Override
            public void onResult(DelMRResult result, boolean onUIThread) {
                if (result.isOk() && onUIThread) {
                    if (result.status == 1) {
                        Toast.makeText(RecordsDetailActivity.this, R.string.del_mr_suc, Toast.LENGTH_SHORT).show();
                        hadRecords.setVisibility(View.GONE);
                        add_records.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(RecordsDetailActivity.this, R.string.del_mr_fail, Toast.LENGTH_SHORT).show();
                    }
                } else if (!result.isOk() && onUIThread) {
                    Toast.makeText(RecordsDetailActivity.this, R.string.del_mr_fail, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addRecords() {
        NewRecordsActivity.start(RecordsDetailActivity.this, NEW_RECORDS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case REQUEST_CODE_R:
                finish();
                break;
            case RESULT_CODS_RE:

                break;
            case NEW_RECORDS:
                // 直接返回主页
                setResult(RESULT_OK);
                finish();
                break;
            default:
                break;
        }
    }
}
