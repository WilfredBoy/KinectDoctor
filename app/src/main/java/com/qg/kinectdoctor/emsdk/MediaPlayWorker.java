package com.qg.kinectdoctor.emsdk;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.qg.kinectdoctor.model.VoiceBean;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ZH_L on 2016/10/25.
 */
public class MediaPlayWorker extends BaseWorker<PlayTask> implements   PlayerStateMachine.PlayerStatusListener{
    private static final String TAG = MediaPlayWorker.class.getSimpleName();
//    private Handler handler;
//    private BlockingQueue<PlayTask> mpQueue;
//    private MediaPlayer mediaPlayer;
//    private SoundPool soundPool;
//    private Map<Integer, Integer> soundMap;
//    private int sampleId;

    private PlayStatusChangedListener mListener;

    private PlayerStateMachine psMachine;
    public MediaPlayWorker(){
        super(Looper.getMainLooper());
//        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
//        soundPool.setOnLoadCompleteListener(this);
//        soundMap = new HashMap<>();
//        sampleId = 0;
        psMachine = new PlayerStateMachine();
        psMachine.setPlayStatusListener(this);
    }

    private VoiceBean curVoiceBean = null;

    @Override
    public void run() {
        while(true) {
            try {
                PlayTask task = mQueue.take();
                VoiceBean voiceBean = task.getVoiceBean();

                EMVoiceMessageBody body = voiceBean.getVoice();
                EMFileMessageBody.EMDownloadStatus s = body.downloadStatus();

                if(s == EMFileMessageBody.EMDownloadStatus.PENDING){
                    Log.e(TAG,"voice-pending-download");
                }else if(s == EMFileMessageBody.EMDownloadStatus.DOWNLOADING){
                    Log.e(TAG,"voice-downloading");
                }else if(s == EMFileMessageBody.EMDownloadStatus.SUCCESSED){
                    checkIsPlaying();
                    curVoiceBean = voiceBean;
                    Log.e(TAG,"voice-download-success");
                    callToMainThread(PlayStatus.PROGRESS.setVoiceBean(curVoiceBean));
                    loadVoice(body);
                }else if(s == EMFileMessageBody.EMDownloadStatus.FAILED){
                    Log.e(TAG,"voice-download-fail");
                    callToMainThread(PlayStatus.FAIL.setErrMsg("voice-download-fail").setVoiceBean(curVoiceBean));
                    curVoiceBean = null;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkIsPlaying(){
        if(psMachine.isPlaying()){
            callToMainThread(PlayStatus.SUCCESS.setVoiceBean(curVoiceBean));
        }
    }

    private void loadVoice(EMVoiceMessageBody body){
        String localUrl = body.getLocalUrl();
        String remoteUrl = body.getRemoteUrl();
        Log.e(TAG, "localUrl->"+localUrl+",remoteUrl->" + remoteUrl);
        if(localUrl != null && !localUrl.equals("")){
            File file = new File(localUrl);
//            int soundId = soundPool.load(file.getAbsolutePath(), 1);
//            if(soundId != 0){
//                //load success
//                soundMap.put(++sampleId, soundId);
//                return;
//            }
            psMachine.playMedia(file);
            return;
        }

        callToMainThread(PlayStatus.FAIL.setErrMsg("load voice fail").setVoiceBean(curVoiceBean));
        curVoiceBean = null;
    }

    public void callToMainThread(final PlayStatus status){
        if(status == null) return;

        Runnable r = new Runnable() {
            @Override
            public void run() {
                if(mListener != null){
                    mListener.onPlayStatusChanged(status);
                }
            }
        };
        handler.post(r);

    }

    public void setPlayStatusChangedListener(PlayStatusChangedListener listener){
        mListener = listener;
    }

    @Override
    public void onPlayComplete() {
        callToMainThread(PlayStatus.SUCCESS.setVoiceBean(curVoiceBean));
        curVoiceBean = null;
    }

    @Override
    public void onPlayError() {
        callToMainThread(PlayStatus.FAIL.setVoiceBean(curVoiceBean).setErrMsg("media play error"));
        curVoiceBean = null;
    }

    public enum PlayStatus{
        PROGRESS, SUCCESS, FAIL();

        PlayStatus(){};

        private String errMsg = "";
        private VoiceBean voiceBean;

        public VoiceBean getVoiceBean() {
            return voiceBean;
        }

        public PlayStatus setVoiceBean(VoiceBean voiceBean) {
            this.voiceBean = voiceBean;
            return this;
        }

        public String getErrMsg(){
            return errMsg;
        }

        public PlayStatus setErrMsg(String errMsg){
            this.errMsg = errMsg;
            return this;
        }
    }

    public interface PlayStatusChangedListener{
        void onPlayStatusChanged(PlayStatus nowStatus);
    }


    @Override
    public void onDestroy() {

    }

    //sampleId is SoundMap's Key
//    @Override
//    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
//        if(status == 0 &&sampleId !=0){
//            Log.e(TAG,"onLoadComplete->sampleId:"+sampleId+",status:"+status);
//            int soundId = soundMap.get(sampleId);
//            soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f);
//
//            callToMainThread(PlayStatus.SUCCESS.setVoiceBean(curVoiceBean));
//            curVoiceBean = null;
//        }
//    }
}
