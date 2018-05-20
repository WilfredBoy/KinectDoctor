package com.qg.kinectdoctor.emsdk;

import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.qg.kinectdoctor.activity.App;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ZH_L on 2016/10/25.
 */
public class MediaRecordWorker extends BaseWorker<RecordTask> {
    private static final String TAG  = MediaRecordWorker.class.getSimpleName();
//    private Handler handler;
//    private BlockingQueue<RecordTask> mrQueue;
    private MediaRecordListener mrListener;
//    private MediaRecorder mediaRecorder;

    public MediaRecordWorker(){
        super(Looper.getMainLooper());
//        mediaRecorder = new MediaRecorder();
    }


//    private RecordTask curTask = null;
//   private Object mLock = new Object();

    private boolean isCancel = false;
    private Looper looper;

    @Override
    public void run() {
        looper = Looper.myLooper();
        while(!isCancel){
            try {
                final RecordTask task = mQueue.take();
                String filePath = task.getFilePath();
                int length = task.getLength();
                String toWho = task.getImUsername();
                Log.e(TAG, "filePath->"+filePath+", length->"+length+", toWho->"+toWho);
                if(length < 1* 1000){
                    if(mrListener != null){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mrListener.onError(1001, "你的语音时间较短");
                            }
                        });
                    }
                    continue;
                }
                IMManager.getInstance(App.getInstance()).sendVoiceMessage(filePath, length, toWho, handler,mrListener);
                
//                synchronized (mLock) {
//                    curTask = task;
//                    int audioSource = task.getAudioSource();
//                    String path = task.getOutputFile();
//                    int outputFormat = task.getOutputFormat();
//                    int outputEncode = task.getOutputEncode();
//
//                    try {
//                        initRecorder(audioSource, path, outputFormat, outputEncode);
//                        startRecord();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        curTask = null;
//                    }
//                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void cancel(){
        isCancel = true;
    }

//    private void initRecorder(int audioSource, String path, int outputFormat, int outputEncode) throws IOException {
//        //from Initial to Initialized
//        mediaRecorder.setAudioSource(audioSource);
//
//        //from Initialized to DataSourceConfigured
//        mediaRecorder.setOutputFormat(outputFormat);
//
//        //circle(DataSourceConfigured)
//        mediaRecorder.setOutputFile(path);
//        mediaRecorder.setAudioEncoder(outputEncode);
//
//        //from DataSourceConfigured to Prepared
//        mediaRecorder.prepare();
//    }


//    private void startRecord(){
//        mediaRecorder.start();
//        if(mrListener != null){
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    mrListener.onStartRecord(MediaRecordWorker.this);
//                }
//            });
//        }
//    }

    public void setMediaRecordListener(MediaRecordListener listener){
        mrListener = listener;
    }

    //EMCallBack
//    @Override
//    public void onSuccess() {
//        Log.e(TAG,"is in the same thread->"+(Looper.myLooper() == looper));
//        if(handler != null &&mrListener != null){
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    mrListener.onSuccess();
//                }
//            });
//        }
//    }
//
//    @Override
//    public void onError(final int code, final String message) {
//        if(handler != null &&mrListener != null){
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    mrListener.onError(code, message);
//                }
//            });
//        }
//    }
//
//    @Override
//    public void onProgress(final int progress,final String status) {
//        if(handler != null &&mrListener != null){
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    mrListener.onProgressing(progress, status);
//                }
//            });
//        }
//    }

//    @Override
//    public void onError(MediaRecorder mediaRecorder, int what, int extra) {
//        if(mrListener != null){
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    mrListener.onErrorRecord();
//                }
//            });
//        }
//    }

//    @Override
//    public void onInfo(MediaRecorder mediaRecorder, int what, int extra) {
//        if(what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED){
//            mediaRecorder.stop();
//            if(mrListener != null){
//
//            }
//            mediaRecorder.reset();
//        }else if(what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED){
//
//        }
//    }

//    @Override
//    public void recordResume() {
//        mediaRecorder.resume();
//    }

//    @Override
//    public void recordPause() {
//        mediaRecorder.pause();
//    }

//    @Override
//    public void recordStop() {
//        mediaRecorder.stop();
//        synchronized (mLock) {
//            if (mrListener != null && curTask != null) {
//                String filePath = curTask.getOutputFile();
//                File file = new File(filePath);
//                mrListener.onEndRecord(file);
//            }
//            mediaRecorder.reset();
//        }
//    }


    public interface MediaRecordListener{
        void onSuccess(EMMessage message);
        void onError(int code, String message);
        void onProgressing(int progress, String status);
    }

    @Override
    public void onDestroy() {
//        if(mediaRecorder != null){
//            mediaRecorder.release();
//            mediaRecorder = null;
//        }
        mrListener = null;
    }
}
