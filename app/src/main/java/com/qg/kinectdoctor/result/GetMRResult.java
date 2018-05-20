package com.qg.kinectdoctor.result;


import com.qg.kinectdoctor.model.MedicalRecord;

import java.util.ArrayList;

/**
 * 获取病历列表返回参数
 * Created by jason on 16-10-18.
 */
public class GetMRResult extends Result{
    public ArrayList<MedicalRecord> medicalRecords;//弱没有病历则为空
}
