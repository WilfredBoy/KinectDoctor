package com.qg.kinectdoctor.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.qg.kinectdoctor.R;
import com.qg.kinectdoctor.activity.ChatActivity;
import com.qg.kinectdoctor.adapter.ChatContactListAdapter;
import com.qg.kinectdoctor.emsdk.EMConstants;
import com.qg.kinectdoctor.emsdk.IMFilter;
import com.qg.kinectdoctor.emsdk.IMManager;
import com.qg.kinectdoctor.emsdk.LoginCallback;
import com.qg.kinectdoctor.logic.LogicHandler;
import com.qg.kinectdoctor.logic.LogicImpl;
import com.qg.kinectdoctor.model.ChatInfoBean;
import com.qg.kinectdoctor.model.PUser;
import com.qg.kinectdoctor.param.GetPUserByPhoneParam;
import com.qg.kinectdoctor.result.GetPUserByPhoneResult;
import com.qg.kinectdoctor.util.CommandUtil;
import com.qg.kinectdoctor.util.ToastUtil;
import com.qg.kinectdoctor.view.TopbarL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RunnableFuture;

/**
 * Created by ZH_L on 2016/10/21.
 */
public class ChatListFragment extends BaseFragment implements ChatContactListAdapter.OnChatItemClickListener, EMMessageListener, EMContactListener{

    private static final String TAG = ChatListFragment.class.getSimpleName();

    private TopbarL mTopbar;
    private RecyclerView mRecyclerView;
    private List<ChatInfoBean> mList;
    private ChatContactListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chatlist,null);
        if(v != null){


            mTopbar = (TopbarL)v.findViewById(R.id.chat_list_topbar);
            mRecyclerView = (RecyclerView)v.findViewById(R.id.recyclerview);
            initTopbar();
            initRecyclerView();
            initEM();
            initReceiver();

//            Log.d("weifeng->", "getDataFromServer()执行了");
            getDataFromServer();

//            loginEM();
        }
        return v;
    }

    private void initTopbar(){
        String title = getActivity().getResources().getString(R.string.chat_list);
        mTopbar.setCenterText(true, title, null);
    }

    private void initRecyclerView(){
        mList = new ArrayList<>();
        mAdapter = new ChatContactListAdapter(getActivity(), mList, R.layout.item_chatlist);
        mAdapter.setOnChatItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

//    private void loginEM(){
//        String phone = "13549991585";
//        IMManager.getInstance(getActivity()).login(phone, new LoginCallback() {
//            @Override
//            public void onSuccess() {
//                getDataFromServer();
//            }
//
//            @Override
//            public void onError(String errorMsg) {
//
//            }
//        });
//    }


    private ChatInfoBean curChatingBean = null;
    private BroadcastReceiver mReceiver;


    private void initReceiver(){
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                curChatingBean = null;
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(EMConstants.ACTION_CHAT_ACTIVITY_FINISH);
        getActivity().registerReceiver(mReceiver, filter);
    }

    private void getDataFromServer(){
        Log.e(TAG,"getDataFromServer");
        new Thread(){
            @Override
            public void run() {
                try {
//                    Log.d("weifeng->", "run方法执行了");
                    final List<String> usernames = IMManager.getInstance(getActivity()).getFriendsList();
//                     final List<String> users = new ArrayList<String>();
//                    users.add("15222223333");

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

//                            Log.d("weifeng->", "内部run方法执行了");
//                            final List<String> phones = IMFilter.filterToPhones(users);
                            final List<String> phones = IMFilter.filterToPhones(usernames);

                            //weifeng

//                                StringBuffer sb = new StringBuffer();
//                                for(Object obj : phones){
//                                    sb.append(obj);
//                                }
//                                Log.d("weifeng->", sb.toString() + "haha");

//                            GetPUserByPhoneParam param = new GetPUserByPhoneParam(users);
                            GetPUserByPhoneParam param = new GetPUserByPhoneParam(phones);
                            LogicImpl.getInstance().getPUserByPhone(param, new LogicHandler<GetPUserByPhoneResult>() {
                                @Override
                                public void onResult(GetPUserByPhoneResult result, boolean onUIThread) {
                                    if(onUIThread){
                                        if(result.isOk()){
                                            //get a Map<String, PUser>
                                            Map<String, PUser> phoneToPUser = result.getPhoneToPUser();
                                            if(phoneToPUser != null){
                                                Log.d("GetPUserByPhoneResult", phoneToPUser.toString());
                                            }
                                            for(String phone: phones) {
                                                PUser pUser = phoneToPUser.get(phone);
                                                if(pUser != null){
                                                    ChatInfoBean bean = new ChatInfoBean(pUser);
                                                    mList.add(bean);
                                                }
                                            }
                                            mAdapter.notifyDataSetChanged();
                                        }else{
                                            ToastUtil.showResultErrorToast(result);
                                        }
//
//                                        PUser pUser = new PUser(18, "测试", 1, "12345678901", "", "1995-05-16");
//                                        ChatInfoBean cb = new ChatInfoBean(pUser);
//                                        mList.add(cb);
//                                        mAdapter.notifyDataSetChanged();
                                    }
                                }
                            });
                        }
                    });

                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                }
            }
        }.start();


    }


    private void initEM(){
        //监听消息回调
        IMManager.getInstance(getActivity()).addMessageListener(this);
        //监听联系人回调
        IMManager.getInstance(getActivity()).setContactListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mReceiver != null){
            getActivity().unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }


    //    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == EMConstants.REQCODE_START_CHAT){
//            Log.d(TAG,"onActivityResult");
//            curChatingBean = null;
//        }
//    }


    @Override
    public void onChatItemClick(View v, int position) {
        ChatInfoBean bean = mList.get(position);
        curChatingBean  = bean;
        mAdapter.clearUnReadCount(bean);
        Bundle extra = new Bundle();
        extra.putSerializable(EMConstants.KEY_CHATINFO_BEAN, bean);
        ChatActivity.startForResult(getActivity(),extra,EMConstants.REQCODE_START_CHAT);
    }

    private Runnable r = new Runnable(){

        @Override
        public void run() {
            //显示所有联系人的消息收到状态
            mAdapter.notifyDataSetChanged();

            //把正在聊天的人的未读消息清零
            if(curChatingBean != null){
                Log.e(TAG, "clearUnReadCount");
                mAdapter.clearUnReadCount(curChatingBean);
            }
        }
    };

    @Override
    public void onMessageReceived(List<EMMessage> list) {
        Log.e(TAG, "in main thread->"+(Looper.myLooper() == Looper.getMainLooper()));
        Log.e(TAG, "onMessageReceived");
        if(list == null)return;
        CommandUtil.vibrate(1000);
        getActivity().runOnUiThread(r);
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

    private String filterUsernameToPhone(String username){
        if(username == null)return "";
        return username.replace(EMConstants.DOCTOR_USERNAME_PREFIX,"").replace(EMConstants.PATIENT_USERNAME_PREFIX, "");
    }

    @Override
    public void onContactAdded(String username) {
        if(username == null || username.equals("")) return;
        //增加了某个联系人
        final List<String> phones = IMFilter.filterToPhones(username);
        GetPUserByPhoneParam param = new GetPUserByPhoneParam(phones);
        LogicImpl.getInstance().getPUserByPhone(param, new LogicHandler<GetPUserByPhoneResult>() {
            @Override
            public void onResult(GetPUserByPhoneResult result, boolean onUIThread) {
                if(onUIThread){
                    if(result.isOk()){
                        //get a Map<String, PUser>
                        Map<String, PUser> map = result.getPhoneToPUser();
                        if(map != null){
                            for(String phone: phones){
                                PUser pUser = map.get(phone);
                                ChatInfoBean bean = new ChatInfoBean(pUser);
                                mList.add(bean);
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }else{
                        ToastUtil.showResultErrorToast(result);
                    }
                }
            }
        });
    }

    @Override
    public void onContactDeleted(String username) {
        //被删除时调用,理论上这个for循环只有一个匹配
        for(ChatInfoBean bean: mList){
            if(username.equals(bean.getIMUsername())){
                mList.remove(bean);
                showMessage("病人-"+ bean.getPUser().getName() + "-删除了你");
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onContactInvited(String s, String s1) {

    }

    @Override
    public void onContactAgreed(String s) {

    }

    @Override
    public void onContactRefused(String s) {

    }

    private void showMessage(String text){
        ToastUtil.showToast2(getActivity(), text);
    }
}
