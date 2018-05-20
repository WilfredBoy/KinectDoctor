package com.qg.kinectdoctor.emsdk;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import com.qg.kinectdoctor.activity.App;

import java.io.File;
import java.io.IOException;

/**
 * Created by ZH_L on 2016/10/26.
 */
public class RecorderStateMachine {
    private static final String TAG = RecorderStateMachine.class.getSimpleName();

    private static final int DEFAULT_AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
    private static final int DEFAULT_OUTPUT_FORMAT =  MediaRecorder.OutputFormat.AMR_NB;
    private static final String DEFAUL_OUTPUT_FILE = Environment.getExternalStorageDirectory()+ File.separator+ App.getInstance().getPackageName()+ File.separator+"voice";
    private static final int DEFAULT_AUDIO_ENCODER = MediaRecorder.AudioEncoder.AMR_NB;


    private MediaRecorder mediaRecorder;

    private boolean isPrepared;

    private boolean isRecording;

    private File recordingFile ;

    private RecorderStateMachineListener listener;

    public RecorderStateMachine(){
        mediaRecorder = new MediaRecorder();
        initStatus();
    }

    private void initStatus(){
        isPrepared = false;
        isRecording = false;
        recordingFile = null;
        recordStartTime = 0;
        recordEndTime = 0;
    }

//    private boolean isInit;

    public void initRecorder(){
        initStatus();
        mediaRecorder.setAudioSource(DEFAULT_AUDIO_SOURCE);
        mediaRecorder.setOutputFormat(DEFAULT_OUTPUT_FORMAT);
        File dir = new File(DEFAUL_OUTPUT_FILE);
        if(!dir.exists()){
            dir.mkdirs();
        }
//        Log.e(TAG,"dir is exist->"+(dir.exists()));
        File file  = new File(DEFAUL_OUTPUT_FILE + File.separator + System.currentTimeMillis()+".arm");
        if(!file.exists()){
            try {
                file.createNewFile();
                recordingFile = file;
                Log.e(TAG, "new file");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
            }
        }
        mediaRecorder.setOutputFile(file.getAbsolutePath());
        mediaRecorder.setAudioEncoder(DEFAULT_AUDIO_ENCODER);
        Log.d(TAG, "recorder init");
        prepareRecorder();
    }



    private void prepareRecorder(){
        try {
            mediaRecorder.prepare();
            Log.d(TAG, "recorder prepare");
            isPrepared = true;
        } catch (IOException e) {
            isPrepared = false;
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        }
    }

    private long recordStartTime;
    private long recordEndTime;

//    private boolean isStarted;

    public void startRecorder(){
        mediaRecorder.start();
        recordStartTime = System.currentTimeMillis();
        Log.d(TAG, "recorder start");
        isRecording = true;
    }

//    private boolean isStop;

    public void stopRecorder(boolean cancelRecord){
        mediaRecorder.stop();
        recordEndTime = System.currentTimeMillis();
        Log.d(TAG, "recorder stop");
        if(listener != null){
            listener.onStop(recordingFile, cancelRecord, recordEndTime - recordStartTime);
        }
        recordingFile = null;
        isRecording = false;
        resetRecorder();
    }

//    private boolean isReset;

    private void resetRecorder(){
        mediaRecorder.reset();
        Log.d(TAG, "recorder reset");
    }

    public boolean isPrepared(){
        return isPrepared;
    }

    public boolean isRecording(){
        return isRecording;
    }

    public void setRecorderStateMachineListener(RecorderStateMachineListener listener){
        this.listener = listener;
    }

    public interface RecorderStateMachineListener{
        void onStop(File recordingFile, boolean cancelRecord, long recordDuration);
    }
}
