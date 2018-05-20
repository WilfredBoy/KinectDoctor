package com.qg.kinectdoctor.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.qg.kinectdoctor.R;
import com.qg.kinectdoctor.view.TopbarZ;

/**
 * Created by 攀登者 on 2016/10/27.
 */
public class NewRecordsEnterActivity extends BaseActivity {

    private static final String TAG = "NewRecordsEnterActivity";
    private TopbarZ topbar;
    private EditText editText;
    private AlertDialog dialog;

    public static void start(Activity context, int requestCode) {
        Intent intent = new Intent(context, NewRecordsEnterActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_records_enter);
        initUI();
    }

    private void initUI() {
        topbar = (TopbarZ) findViewById(R.id.topbar);
        editText = (EditText) findViewById(R.id.edittext);
        topbar.setLeftButton(true, R.drawable.back_selector, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText.getText().toString().equals("")) {
                    dialog = new AlertDialog.Builder(NewRecordsEnterActivity.this).
                            setTitle(R.string.cancle_change).
                            setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialog.dismiss();
                                }
                            }).
                            setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialog.dismiss();
                                    finish();
                                }
                            }).create();
                    dialog.show();

                } else {
                    finish();
                }
            }
        });
        topbar.setTitle(true, R.string.mr_detail, null);
        topbar.setRightText(true, R.string.sure, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText.getText().toString().equals("")) {
                    Intent intent = new Intent();
                    intent.putExtra("condition", editText.getText().toString());
                    setResult(RESULT_OK, intent);
                    Log.e(TAG, editText.getText().toString());
                    finish();
                } else {
                    Toast.makeText(NewRecordsEnterActivity.this, R.string.please_enter, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
