package com.qg.kinectdoctor.emsdk;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.util.Log;

import com.qg.kinectdoctor.activity.App;

import java.io.File;
import java.io.IOException;

/**
 * Created by ZH_L on 2016/10/27.
 */
public class PlayerStateMachine implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener{
    private static final String TAG = PlayerStateMachine.class.getSimpleName();
    private MediaPlayer mediaPlayer;
    private PlayerStatusListener psListener;

    public PlayerStateMachine(){
        initStatus();
    }

    private boolean isPrepared;
    private boolean isPlaying;

    private void initStatus(){
        isPrepared = false;
        isPlaying = false;
    }

    private void init(File dataSource){
        initStatus();
        if(mediaPlayer == null){
            Uri uri = Uri.fromFile(dataSource);
            mediaPlayer = MediaPlayer.create(App.getInstance(), uri);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setOnPreparedListener(this);
        }else{
            try {
                mediaPlayer.setDataSource(dataSource.getAbsolutePath());
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void playMedia(File file){
        if(isMediaPlaying()){
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        init(file);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.e(TAG, "onCompletion");
        if(psListener != null){
            psListener.onPlayComplete();
        }
        mediaPlayer.reset();
        isPlaying = false;
    }

    private boolean isMediaPlaying(){
        if(mediaPlayer == null) return false;
        return mediaPlayer.isPlaying();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        isPrepared = true;
        isPlaying = true;
        mediaPlayer.start();
    }

    public boolean isPrepared(){
        return isPrepared;
    }

    public boolean isPlaying(){
        return isPlaying;
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
        mediaPlayer.reset();
        isPlaying = false;
        if(psListener != null){
            psListener.onPlayError();
            return true;
        }
        return false;
    }

    public void setPlayStatusListener(PlayerStatusListener listener){
        psListener = listener;
    }

    public interface PlayerStatusListener{
        void onPlayComplete();
        void onPlayError();
    }
}
