package com.qg.kinectdoctor.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.qg.kinectdoctor.R;
import com.qg.kinectdoctor.adapter.ChatAdapter;
import com.qg.kinectdoctor.emsdk.EMConstants;
import com.qg.kinectdoctor.emsdk.IMFilter;
import com.qg.kinectdoctor.emsdk.IMManager;
import com.qg.kinectdoctor.emsdk.MediaExectutor;
import com.qg.kinectdoctor.emsdk.MediaPlayWorker;
import com.qg.kinectdoctor.emsdk.MediaRecordWorker;
import com.qg.kinectdoctor.emsdk.PlayTask;
import com.qg.kinectdoctor.emsdk.RecordTask;
import com.qg.kinectdoctor.model.ChatInfoBean;
import com.qg.kinectdoctor.model.VoiceBean;
import com.qg.kinectdoctor.util.CommandUtil;
import com.qg.kinectdoctor.emsdk.RecorderStateMachine;
import com.qg.kinectdoctor.util.ToastUtil;
import com.qg.kinectdoctor.view.TopbarL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZH_L on 2016/10/22.
 */
public class ChatActivity extends BaseActivity implements EMMessageListener, ChatAdapter.OnItemVoiceClickListener, View.OnLongClickListener, View.OnTouchListener, RecorderStateMachine.RecorderStateMachineListener, MediaPlayWorker.PlayStatusChangedListener, MediaRecordWorker.MediaRecordListener{
    private static final String TAG = ChatActivity.class.getSimpleName();

    public static void startForResult(Activity activity, int requestCode){
        Intent intent = new Intent(activity, ChatActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startForResult(Activity activity, Bundle b,int requestCode){
        Intent intent = new Intent(activity, ChatActivity.class);
        intent.putExtra(EMConstants.EXTRA_FROM_CHAT_CONTACT_LIST, b);
        activity.startActivityForResult(intent, requestCode);
    }

    private TopbarL mTopbar;
    private Button mRecordBtn;
    private RecyclerView mRecyclerView;
    private List<VoiceBean> mList;
    private ChatAdapter  mAdapter;

    private ChatInfoBean curChatingBean;

    private RecorderStateMachine rsMachine;

    private boolean isFirstCreated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle b = getIntent().getBundleExtra(EMConstants.EXTRA_FROM_CHAT_CONTACT_LIST);
        curChatingBean = (ChatInfoBean) b.getSerializable(EMConstants.KEY_CHATINFO_BEAN);

        initUI();
        initEM();

        rsMachine = new RecorderStateMachine();
        rsMachine.setRecorderStateMachineListener(this);
        MediaExectutor.getInstance().setPlayStatusChangedListener(this);
        MediaExectutor.getInstance().setMediaRecordListener(this);

        isFirstCreated = true;
        getPermission();
    }

    private void getPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO }, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else {
                    finish();
                }
        }
    }

    private void initUI(){
        mTopbar = (TopbarL) findViewById(R.id.chat_topbar);
        initTopbar();

        mRecyclerView = (RecyclerView) findViewById(R.id.chat_recyclerview);
        initRecyclerView();

        mRecordBtn = (Button) findViewById(R.id.chat_record_btn);
        mRecordBtn.setOnLongClickListener(this);
        mRecordBtn.setOnTouchListener(this);
    }

    private void initTopbar(){
        mTopbar.setLeftImage(true, R.drawable.back_selector, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        mTopbar.setRightImage(true, R.drawable.person_info_selector, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //jump to where
//            }
//        });

        mTopbar.setCenterText(true, curChatingBean.getPUser().getName(), null);
    }

    private void initRecyclerView(){
        mList = new ArrayList<>();
        mAdapter = new ChatAdapter(this, mList);
        mAdapter.setOnItemVoiceClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        setResult(EMConstants.REQCODE_START_CHAT);
        Intent intent = new Intent(EMConstants.ACTION_CHAT_ACTIVITY_FINISH);
        sendBroadcast(intent);
    }

    private void initEM(){
        String username = curChatingBean.getIMUsername();
        List<EMMessage> history = IMManager.getInstance(this).getChatHistory(username);
        Log.e(TAG, "history-size->"+history.size());
        EMMessage lastMsg = getLastMessage();
        List<VoiceBean> beans = IMFilter.devideByTimeTitle(history, username, lastMsg);
        Log.e(TAG, "bean-size->"+beans.size());
        mList.addAll(beans);
        mAdapter.notifyDataSetChanged();
        IMManager.getInstance(this).addMessageListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus && isFirstCreated && !mList.isEmpty()){
            mRecyclerView.smoothScrollToPosition(mList.size()-1);
            isFirstCreated = !isFirstCreated;
        }
    }

    private String filterToPhone(String imUsername){
        return imUsername.replace(EMConstants.PATIENT_USERNAME_PREFIX,"").replace(EMConstants.DOCTOR_USERNAME_PREFIX, "");
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            mAdapter.notifyDataSetChanged();
            mRecyclerView.smoothScrollToPosition(mList.size()-1);
        }
    };

    @Override
    public void onMessageReceived(List<EMMessage> list) {
        if(list == null) return;
        String chating = curChatingBean.getIMUsername();
        List<EMMessage> chatingMsgs = new ArrayList<>();
        for(EMMessage message: list){
            String imUsername = message.getFrom();
            if(chating.equals(imUsername)){
                chatingMsgs.add(message);
            }
        }
        if(!chatingMsgs.isEmpty()){
            CommandUtil.vibrate(1000);
        }
        EMMessage lastMsg = getLastMessage();
        List<VoiceBean> newBeans = IMFilter.devideByTimeTitle(chatingMsgs, chating, lastMsg);
        mList.addAll(newBeans);
        runOnUiThread(r);
    }

    private EMMessage getLastMessage(){
        if(mList.size() <=0 )return null;
        return mList.get(mList.size()-1).getMessage();
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageReadAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageDeliveryAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }

    private void showMessage(String text){
        ToastUtil.showToast(this, text);
    }

    @Override
    public void onVoiceClick(VoiceBean bean, int position) {
//        VoiceBean bean = mList.get(position);
        bean.setIsPlaying(true);
        PlayTask task = new PlayTask(bean);
        MediaExectutor.getInstance().executePlayTask(task);
    }



    private boolean isLongClick = false;

    @Override
    public boolean onLongClick(View view) {
        isLongClick = true;
        switch(view.getId()){
            case R.id.chat_record_btn:
                final RecorderStateMachine machine = rsMachine;
                machine.initRecorder();
                if(!machine.isRecording()) {
                    mRecordBtn.setLongClickable(false);
                    if(machine.isPrepared()) {
                        machine.startRecorder();

                        showMessage("开始录音");
                    }
                }else{

                }
                break;
        }
        return true;
    }


    //handle record button 's gesture action, longclick to record
    //up to check whether is longclick,if true send record, else cancel
    //
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "ACTION_DOWN");
                //give longclick to handle
                return false;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "ACTION_UP");
                //check whether is longclick
                if(isLongClick){
                    //end to record
                    rsMachine.stopRecorder(false);
                    showMessage("停止录音");
                    //如果这里return true 将看到按钮按了下去弹不起来
                    //return true;
                }
                return false;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "ACTION_MOVE");
//                isLongClick = false;

                return true;
            case MotionEvent.ACTION_CANCEL:
                Log.e(TAG, "ACTION_CANCEL");
                isLongClick = false;
                //end to record and delete the recording file
                rsMachine.stopRecorder(true);

                return true;
        }
        return false;
    }

    @Override
    public void onStop(File recordingFile, boolean cancelRecord, long recordDuration) {
        isLongClick = false;
        mRecordBtn.setLongClickable(true);
        if(cancelRecord && recordingFile != null) {
            //delete the recording file
            recordingFile.delete();

        }else  if(recordingFile != null && curChatingBean != null) {
            //send record to network
            final String filePath = recordingFile.getAbsolutePath();
            final long length = recordDuration;
            final String imUsername = curChatingBean.getIMUsername();

            RecordTask task = new RecordTask(filePath, (int)length, imUsername);
            MediaExectutor.getInstance().executeRecordTask(task);
        }
    }

    @Override
    public void onPlayStatusChanged(MediaPlayWorker.PlayStatus nowStatus) {
        final VoiceBean voiceBean = nowStatus.getVoiceBean();
        switch(nowStatus){
            case SUCCESS:
                Log.e(TAG, "play status success");
                if(voiceBean != null){
                    voiceBean.setIsPlaying(false);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case PROGRESS:
                break;
            case FAIL:
                if(voiceBean != null){
                    voiceBean.setIsPlaying(false);
                    mAdapter.notifyDataSetChanged();
                }
                showMessage(nowStatus.getErrMsg());
                break;
        }
    }

    @Override
    public void onSuccess(EMMessage message) {
        Log.e(TAG," sendMessage->onSuccess");
        List<EMMessage> list = new ArrayList<>();
        list.add(message);
        EMMessage lastMsg = getLastMessage();
        List<VoiceBean> beans = IMFilter.devideByTimeTitle(list, message.getTo(), lastMsg);
        mList.addAll(beans);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.smoothScrollToPosition(mList.size()-1);
    }

    @Override
    public void onError(int code, String errMsg) {
        Log.e(TAG,"sendMessage->onError:"+errMsg);
        showMessage(errMsg);
    }

    @Override
    public void onProgressing(int progress, String status) {
        Log.e(TAG, "sendMessage->onProgressing-"+progress+",status:"+status);
    }
}
