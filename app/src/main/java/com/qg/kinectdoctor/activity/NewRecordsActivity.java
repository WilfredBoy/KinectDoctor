package com.qg.kinectdoctor.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qg.kinectdoctor.R;
import com.qg.kinectdoctor.logic.LogicHandler;
import com.qg.kinectdoctor.logic.LogicImpl;
import com.qg.kinectdoctor.model.MedicalRecord;
import com.qg.kinectdoctor.param.SetMRParam;
import com.qg.kinectdoctor.result.SetMRResult;
import com.qg.kinectdoctor.util.CheckContact;
import com.qg.kinectdoctor.view.MyNumberPicker;
import com.qg.kinectdoctor.view.TopbarZ;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Created by 攀登者 on 2016/10/27.
 */
public class NewRecordsActivity extends BaseActivity implements NumberPicker.OnValueChangeListener, NumberPicker.OnScrollListener, NumberPicker.Formatter,
        View.OnClickListener {

    private final static String TAG = "NewRecordsActivity";
    private MyNumberPicker mNumberPicker;
    private TopbarZ topbar;
    private EditText name, phone, allergyMedications;
    private TextView age, sex, male, female;
    private RelativeLayout medical_condition;
    private Button saveRecords, cancle_sex;
    private MedicalRecord medicalRecord;
    private String condition = null; // 病况
    private AlertDialog dialog = null, dialog_sex = null;
    private Button cancle, sure;
    private int agen = -1; // 年龄
    private int sexn = -1; // 性别
    private InputMethodManager imm;
    private final static int REQUEST_CODE_C = 3;


    public static void start(Activity context, int requestCode) {
        Intent intent = new Intent(context, NewRecordsActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_records);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        initUI();
    }

    private void initUI() {
        topbar = (TopbarZ) findViewById(R.id.topbar);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        allergyMedications = (EditText) findViewById(R.id.allergy_medications);
        age = (TextView) findViewById(R.id.age);
        sex = (TextView) findViewById(R.id.sex);
        medical_condition = (RelativeLayout) findViewById(R.id.rela_medical_condition);
        saveRecords = (Button) findViewById(R.id.save_records);

        topbar.setLeftButton(true, R.drawable.back_selector, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        topbar.setTitle(true, R.string.new_records, null);

        saveRecords.setOnClickListener(this);
        age.setOnClickListener(this);
        sex.setOnClickListener(this);
        medical_condition.setOnClickListener(this);

        // 年龄选择
        View view = LayoutInflater.from(this).inflate(R.layout.age_choose, null);
        mNumberPicker = (MyNumberPicker) view.findViewById(R.id.age_choose);
        cancle = (Button) view.findViewById(R.id.cancle);
        sure = (Button) view.findViewById(R.id.sure);
        cancle.setOnClickListener(this);
        sure.setOnClickListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        builder.setView(view);
        dialog = builder.create();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialog_animation);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        initMyNumberPicker();

        // 性别
        View view_sex = LayoutInflater.from(this).inflate(R.layout.sex_choose, null);
        male = (TextView) view_sex.findViewById(R.id.male);
        female = (TextView) view_sex.findViewById(R.id.female);
        cancle_sex = (Button) view_sex.findViewById(R.id.cancle_sex);
        male.setOnClickListener(this);
        female.setOnClickListener(this);
        cancle_sex.setOnClickListener(this);
        AlertDialog.Builder builder_sex = new AlertDialog.Builder(this, R.style.DialogTheme);
        builder_sex.setView(view_sex);
        dialog_sex = builder_sex.create();
        Window window_sex = dialog_sex.getWindow();
        window_sex.setGravity(Gravity.BOTTOM);
        window_sex.setWindowAnimations(R.style.dialog_animation);
        window_sex.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp_sex = window_sex.getAttributes();
        lp_sex.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp_sex.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window_sex.setAttributes(lp_sex);
    }

    private void initMyNumberPicker() {
        setNumberPickerDividerColor(mNumberPicker);
        mNumberPicker.setFormatter(this);
        mNumberPicker.setOnValueChangedListener(this);
        mNumberPicker.setOnScrollListener(this);
        mNumberPicker.setMaxValue(300);
        mNumberPicker.setMinValue(0);
        mNumberPicker.setValue(0);
    }

    private void setNumberPickerDividerColor(NumberPicker numberPicker) {
        NumberPicker picker = numberPicker;
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //设置分割线的颜色值
                    pf.set(picker, new ColorDrawable(this.getResources().getColor(R.color.xiahuaxian)));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        agen = newVal;
        Log.i("tag", "oldValue:" + oldVal + "   ; newValue: " + newVal);
    }

    @Override
    public String format(int value) {
        String tmpStr = String.valueOf(value);
        if (value < 10) {
            tmpStr = "0" + tmpStr;
        }
        return tmpStr;
    }

    @Override
    public void onScrollStateChange(NumberPicker view, int scrollState) {
        switch (scrollState) {
            case NumberPicker.OnScrollListener.SCROLL_STATE_FLING:
                // 手离开之后还在滑动
                break;
            case NumberPicker.OnScrollListener.SCROLL_STATE_IDLE:
                // 不滑动
                break;
            case NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                // 滑动中
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_records:
                saveRecords();
                break;
            case R.id.age:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                dialog.show();
                break;
            case R.id.sex:
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                dialog_sex.show();
                break;
            case R.id.rela_medical_condition:
                NewRecordsEnterActivity.start(NewRecordsActivity.this, REQUEST_CODE_C);
                break;
            case R.id.cancle:
                dialog.dismiss();
                break;
            case R.id.sure:
                age.setText(String.valueOf(agen));
                dialog.dismiss();
                break;
            case R.id.male:
                sexn = 1;
                dialog_sex.dismiss();
                sex.setText(R.string.male);
                break;
            case R.id.female:
                sexn = 0;
                sex.setText(R.string.female);
                dialog_sex.dismiss();
                break;
            case R.id.cancle_sex:
                dialog_sex.dismiss();
                break;
        }
    }

    private void saveRecords() {
        if (!name.getText().toString().equals("") && !age.getText().toString().equals("") && !sex.getText().toString().equals("") && !phone.getText().toString().
                equals("") && !allergyMedications.getText().toString().equals("") && condition != null) {
            if (CheckContact.isMobile(phone.getText().toString())) {
                medicalRecord = new MedicalRecord(0, App.getInstance().getUser().getId(), agen, name.getText().toString(), sexn, phone.getText().toString(), null,
                        App.getInstance().getUser().getName(), App.getInstance().getUser().getPhone(), App.getInstance().getUser().getHospital(),
                        App.getInstance().getUser().getDepartment(), new Date(), condition, allergyMedications.getText().toString(), null);
                setMR();
            } else {
                Toast.makeText(NewRecordsActivity.this, R.string.phone_error, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(NewRecordsActivity.this, R.string.empty, Toast.LENGTH_SHORT).show();
        }
    }

    private void setMR() {
        SetMRParam param = new SetMRParam();
        param.medicalRecord = medicalRecord;
        LogicImpl.getInstance().SetMR(param, new LogicHandler<SetMRResult>() {
            @Override
            public void onResult(SetMRResult result, boolean onUIThread) {
                if (result.isOk() && onUIThread) {
                    if (result.status == 1) {
                        Toast.makeText(NewRecordsActivity.this, R.string.setMRsuc, Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(NewRecordsActivity.this, R.string.setMRfail, Toast.LENGTH_SHORT).show();
                    }
                } else if (!result.isOk() && onUIThread) {
                    Toast.makeText(NewRecordsActivity.this, R.string.setMRfail, Toast.LENGTH_SHORT).show();
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
            case REQUEST_CODE_C:
                condition = data.getStringExtra("condition");
                break;
            default:
                break;
        }
    }
}
