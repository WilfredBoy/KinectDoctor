package com.qg.kinectdoctor.logic;

import android.os.AsyncTask;

import com.qg.kinectdoctor.http.HttpProcess;
import com.qg.kinectdoctor.param.DelMRParam;
import com.qg.kinectdoctor.param.DelRcStageParam;
import com.qg.kinectdoctor.param.GetActionsParam;
import com.qg.kinectdoctor.param.GetDUserByPhoneParam;
import com.qg.kinectdoctor.param.GetMRParam;
import com.qg.kinectdoctor.param.GetPUserByPhoneParam;
import com.qg.kinectdoctor.param.GetRcStageParam;
import com.qg.kinectdoctor.param.LoginParam;
import com.qg.kinectdoctor.param.Param;
import com.qg.kinectdoctor.param.RegisterParam;
import com.qg.kinectdoctor.param.SetMRParam;
import com.qg.kinectdoctor.param.SetRcStageParam;
import com.qg.kinectdoctor.param.UpdateDUserParam;
import com.qg.kinectdoctor.result.DelMRResult;
import com.qg.kinectdoctor.result.DelRcStageResult;
import com.qg.kinectdoctor.result.GetActionsResult;
import com.qg.kinectdoctor.result.GetDUserByPhoneResult;
import com.qg.kinectdoctor.result.GetMRResult;
import com.qg.kinectdoctor.result.GetPUserByPhoneResult;
import com.qg.kinectdoctor.result.GetRcStageResult;
import com.qg.kinectdoctor.result.LoginResult;
import com.qg.kinectdoctor.result.RegisterResult;
import com.qg.kinectdoctor.result.Result;
import com.qg.kinectdoctor.result.SetMRResult;
import com.qg.kinectdoctor.result.SetRcStageResult;
import com.qg.kinectdoctor.result.UpdateDUserResult;
import com.qg.kinectdoctor.util.CommandUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by ZH_L on 2016/10/21.
 */
public class LogicImpl implements Logic{

    private static final String TAG = LogicImpl.class.getSimpleName();
    private Executor exec = Executors.newFixedThreadPool(CommandUtil.getCoreNum()*2);

    private static LogicImpl instance;

    private LogicImpl(){};

    public static LogicImpl getInstance(){
        if(instance == null){
            synchronized (LogicImpl.class){
                if(instance == null){
                    instance = new LogicImpl();
                }
            }
        }
        return instance;
    }

    private <P extends Param, R extends Result>void getResult(final P param,final LogicHandler<R> handler,final Class<R> clazz){
        GetResultTask<R> task = new GetResultTask<R>() {
            @Override
            public R onBackground() {
                R result = HttpProcess.sendHttp(param, clazz);
                handler.onResult(result, false);
                return result;
            }

            @Override
            public void onUI(R result) {
                handler.onResult(result, true);
            }
        };
        task.executeOnExecutor(exec);
    }

    private <P extends Param, R extends Result>void getResultP(final P param,final LogicHandler<R> handler,final Class<R> clazz){
        GetResultTask<R> task = new GetResultTask<R>() {
            @Override
            public R onBackground() {
                R result = HttpProcess.sendHttpP(param, clazz);
                handler.onResult(result, false);
                return result;
            }

            @Override
            public void onUI(R result) {
                handler.onResult(result, true);
            }
        };
        task.executeOnExecutor(exec);
    }

    private abstract class GetResultTask<R extends Result> extends AsyncTask<Void,Void,R>{

        @Override
        protected R doInBackground(Void... ps) {
            return onBackground();
        }

        @Override
        protected void onPostExecute(R r) {
            onUI(r);
        }

        public abstract R onBackground();

        public abstract void onUI(R result);
    }

    @Override
    public void login(LoginParam param, LogicHandler<LoginResult> handler) {
        getResult(param, handler, LoginResult.class);
    }

    @Override
    public void register(RegisterParam param, LogicHandler<RegisterResult> handler) {
        getResult(param, handler, RegisterResult.class);
    }

    @Override
    public void updateDUser(UpdateDUserParam param, LogicHandler<UpdateDUserResult> handler) {
        getResult(param, handler, UpdateDUserResult.class);
    }

    @Override
    public void getDUserByPhone(GetDUserByPhoneParam param, LogicHandler<GetDUserByPhoneResult> handler) {
        getResult(param, handler, GetDUserByPhoneResult.class);
    }

    @Override
    public void getPUserByPhone(GetPUserByPhoneParam param, LogicHandler<GetPUserByPhoneResult> handler) {
        getResult(param, handler, GetPUserByPhoneResult.class);
    }

    @Override
    public void GetMR(GetMRParam param, LogicHandler<GetMRResult> handler) {
        getResult(param, handler, GetMRResult.class);
    }

    @Override
    public void SetMR(SetMRParam param, LogicHandler<SetMRResult> handler) {
        getResult(param, handler, SetMRResult.class);
    }

    @Override
    public void DelMR(DelMRParam param, LogicHandler<DelMRResult> handler) {
        getResult(param, handler, DelMRResult.class);
    }

    @Override
    public void SetRcStage(SetRcStageParam param, LogicHandler<SetRcStageResult> handler) {
        getResult(param, handler, SetRcStageResult.class);
    }

    @Override
    public void DelRcStage(DelRcStageParam param, LogicHandler<DelRcStageResult> handler) {
        getResult(param, handler, DelRcStageResult.class);
    }

    @Override
    public void getRcStage(GetRcStageParam param, LogicHandler<GetRcStageResult> handler) {
        getResultP(param, handler, GetRcStageResult.class);
    }

    @Override
    public void getActions(GetActionsParam param, LogicHandler<GetActionsResult> handler) {
        getResult(param, handler, GetActionsResult.class);
    }
}
