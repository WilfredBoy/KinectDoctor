package com.qg.kinectdoctor.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.qg.kinectdoctor.R;
import com.qg.kinectdoctor.adapter.AddRehabilitationProgrammesAdapter;
import com.qg.kinectdoctor.logic.LogicHandler;
import com.qg.kinectdoctor.logic.LogicImpl;
import com.qg.kinectdoctor.model.Action;
import com.qg.kinectdoctor.model.MedicalRecord;
import com.qg.kinectdoctor.model.RcStage;
import com.qg.kinectdoctor.param.GetActionsParam;
import com.qg.kinectdoctor.param.SetRcStageParam;
import com.qg.kinectdoctor.result.GetActionsResult;
import com.qg.kinectdoctor.result.SetRcStageResult;
import com.qg.kinectdoctor.view.TopbarZ;

import java.util.ArrayList;

/**
 * Created by 攀登者 on 2016/10/30.
 */
public class AddRehabilitationProgrammesActivity extends BaseActivity implements View.OnClickListener {

    private final static String TAG = "AddRehabilitationProgrammesActivity";
    private ListView mListView;
    private TopbarZ topbar;
    private ArrayList<Action> actions;
    private AddRehabilitationProgrammesAdapter adapter;
    private static MedicalRecord medicalRecord;
    private AlertDialog dialog;
    private static int num;

    public static void start(Activity context, int requestCode, MedicalRecord data, int n) {
        medicalRecord = data;
        num = n;
        Intent intent = new Intent(context, AddRehabilitationProgrammesActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rehabilitation_programmes);
        initUI();
        getActions();
    }

    private void initUI() {
        topbar = (TopbarZ) findViewById(R.id.topbar);
        mListView = (ListView) findViewById(R.id.listview);
        topbar.setTitle(true, R.string.add_rp, null);
        topbar.setLeftButton(true, R.drawable.back_selector, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getActions() {
        GetActionsParam param = new GetActionsParam();
        LogicImpl.getInstance().getActions(param, new LogicHandler<GetActionsResult>() {

            @Override
            public void onResult(GetActionsResult result, boolean onUIThread) {
                if(result.isOk() && onUIThread) {
                    actions = new ArrayList<Action>();
                    if(result.actions != null ) {
                        actions.addAll(result.actions);
                    }
                    adapter = new AddRehabilitationProgrammesAdapter(AddRehabilitationProgrammesActivity.this, R.layout.add_rp_item, actions);
                    mListView.setAdapter(adapter);
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                            dialog = new AlertDialog.Builder(AddRehabilitationProgrammesActivity.this).
                                    setTitle(R.string.add_rh).
                                    setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialog.dismiss();
                                        }
                                    }).
                                    setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Log.e("dd", position + "" );
                                            RcStage r = new RcStage(medicalRecord.getId(), num + 1, 0, actions.get(position).getId(),
                                                    actions.get(position).getName(), medicalRecord.getPuserId());
                                            addRehabilitationStage(r);
                                            dialog.dismiss();
                                        }
                                    }).create();
                            dialog.show();
                        }
                    });
                } else if(!result.isOk() && onUIThread) {
                    Toast.makeText(AddRehabilitationProgrammesActivity.this, R.string.getAfail, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addRehabilitationStage(RcStage rcStage) {
        SetRcStageParam param = new SetRcStageParam();
        param.rcStage = rcStage;
        LogicImpl.getInstance().SetRcStage(param, new LogicHandler<SetRcStageResult>() {
            @Override
            public void onResult(SetRcStageResult result, boolean onUIThread) {
                if (result.isOk() && onUIThread) {
                    if (result.status == 1) {
                        Toast.makeText(AddRehabilitationProgrammesActivity.this, R.string.add_rc_suc, Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(AddRehabilitationProgrammesActivity.this, R.string.add_rc_fail, Toast.LENGTH_SHORT).show();
                    }
                } else if (!result.isOk() && onUIThread) {
                    Toast.makeText(AddRehabilitationProgrammesActivity.this, R.string.add_rc_fail, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
