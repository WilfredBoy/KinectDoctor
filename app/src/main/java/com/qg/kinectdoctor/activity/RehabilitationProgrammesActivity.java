package com.qg.kinectdoctor.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.qg.kinectdoctor.R;
import com.qg.kinectdoctor.adapter.RehabilitationProgrammesAdapter;
import com.qg.kinectdoctor.logic.LogicHandler;
import com.qg.kinectdoctor.logic.LogicImpl;
import com.qg.kinectdoctor.model.MedicalRecord;
import com.qg.kinectdoctor.model.RcStage;
import com.qg.kinectdoctor.param.DelRcStageParam;
import com.qg.kinectdoctor.param.GetRcStageParam;
import com.qg.kinectdoctor.param.SetRcStageParam;
import com.qg.kinectdoctor.result.DelRcStageResult;
import com.qg.kinectdoctor.result.GetRcStageResult;
import com.qg.kinectdoctor.result.SetRcStageResult;
import com.qg.kinectdoctor.view.TopbarZ;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * 康复方案
 */
public class RehabilitationProgrammesActivity extends BaseActivity implements View.OnClickListener {

    private TopbarZ topbar;
    private ListView mListView;
    private View view;
    private Button add_rehabilitation_stage;
    private ArrayList<RcStage> list;
    private static MedicalRecord medicalRecord;
    private RehabilitationProgrammesAdapter adapter;
    private final static int ADDRP = 6;
    private AlertDialog dialog;

    public static void start(Activity context, int requestCode, MedicalRecord data) {
        medicalRecord = data;
        Intent intent = new Intent(context, RehabilitationProgrammesActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rehabilitation_programmes);
        initUI();
        getRcStage();
    }

    private void initUI() {
        topbar = (TopbarZ) findViewById(R.id.topbar);
        topbar.setTitle(true, R.string.rehabilitation_programmes, null);
        topbar.setLeftButton(true, R.drawable.back_selector, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mListView = (ListView) findViewById(R.id.listview);
        view = LayoutInflater.from(RehabilitationProgrammesActivity.this).inflate(R.layout.rehabilitation_programmes_footerview, null);
        add_rehabilitation_stage = (Button) view.findViewById(R.id.add_rehabilitation_stage);
        mListView.addFooterView(view);
        add_rehabilitation_stage.setOnClickListener(this);

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                dialog = new AlertDialog.Builder(RehabilitationProgrammesActivity.this).
                        setTitle(R.string.delete_rh).
                        setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.dismiss();
                            }
                        }).
                        setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteRehabilitationStage(list.get(position).getId());
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();

                return false;
            }
        });
    }

    private void getRcStage() {
        GetRcStageParam param = new GetRcStageParam();
        param.mrId = medicalRecord.getId();
        LogicImpl.getInstance().getRcStage(param, new LogicHandler<GetRcStageResult>() {
            @Override
            public void onResult(GetRcStageResult result, boolean onUIThread) {
                if (result.isOk() && onUIThread) {
                    list = new ArrayList<RcStage>();
                    if (result.rcStages != null) {
                        list.addAll(result.rcStages);
                    }
                    adapter = new RehabilitationProgrammesAdapter(RehabilitationProgrammesActivity.this, R.layout.rehabilitation_programmes_item, list);
                    mListView.setAdapter(adapter);
                } else if (!result.isOk() && onUIThread) {
                    Toast.makeText(RehabilitationProgrammesActivity.this, R.string.getRCfail, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_rehabilitation_stage:
                AddRehabilitationProgrammesActivity.start(RehabilitationProgrammesActivity.this, ADDRP, medicalRecord, list.size());
                break;
        }
    }

    private void deleteRehabilitationStage(int id) {
        DelRcStageParam param = new DelRcStageParam();
        param.id = id;
        LogicImpl.getInstance().DelRcStage(param, new LogicHandler<DelRcStageResult>() {
            @Override
            public void onResult(DelRcStageResult result, boolean onUIThread) {
                if (result.isOk() && onUIThread) {
                    if (result.status == 1) {
                        getRcStage();
                        Toast.makeText(RehabilitationProgrammesActivity.this, R.string.del_mr_suc, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RehabilitationProgrammesActivity.this, R.string.del_mr_fail, Toast.LENGTH_SHORT).show();
                    }
                } else if (!result.isOk() && onUIThread) {
                    Toast.makeText(RehabilitationProgrammesActivity.this, R.string.del_mr_fail, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case ADDRP:
                getRcStage();
                break;
            default:
                break;
        }
    }
}
