package com.qg.kinectdoctor.http;

import android.util.Log;

import com.qg.kinectdoctor.R;
import com.qg.kinectdoctor.param.Param;
import com.qg.kinectdoctor.result.Result;
import com.qg.kinectdoctor.util.JsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by ZH_L on 2016/10/21.
 */
public class HttpProcess {
    private static final String TAG = HttpProcess.class.getSimpleName();
    public static <P extends Param, R extends Result> R sendHttp(P param, Class<R> clazz){
        R result = null;
        try {
            String response = sendParamToServer(param);
            Log.d(TAG,"response->"+response);
            result = JsonUtil.toObj(response, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
            Result r = new Result();
            r.status = 0;
            r.errMsg = e.getMessage();
            String j = JsonUtil.toJson(r);
            result = JsonUtil.toObj(j, clazz);
        }
        return result;
    }

    public static <P extends Param, R extends Result> R sendHttpP(P param, Class<R> clazz){
        R result = null;
        try {
            String response = sendParamToServerP(param);
            Log.d(TAG,"response->"+response);
            result = JsonUtil.toObj(response, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
            Result r = new Result();
            r.status = 0;
            r.errMsg = e.getMessage();
            String j = JsonUtil.toJson(r);
            result = JsonUtil.toObj(j, clazz);
        }
        return result;
    }

    private static <P extends Param> String sendParamToServer(P param, String urlSpec) throws IOException {
        String json = JsonUtil.toJson(param);
        Log.d(TAG,"json->"+json);
        BufferedReader br = null;
        OutputStream os = null;
        try {
            String entrance = paramToEntrance(param);
            Log.d(TAG,"url->"+(urlSpec + entrance));
//            URL url = new URL("http://10.21.48.11:10086/RecoveryTraining/PUser/" + entrance);
            URL url = new URL(urlSpec + entrance);
            try {
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                initConnection(conn);
                os = conn.getOutputStream();
                os.write((json).getBytes());
                os.flush();
                Log.d(TAG,"send->"+json);
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String str = null;
                while((str = br.readLine()) != null){
                    sb.append(str);
                }

                if(sb.toString().length() <= 0){
                    throw new IOException("服务器无响应");
                }

                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                throw new IOException("网络错误");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new MalformedURLException("URL 错误");
        }finally{
            if(br != null){
                br.close();
            }

            if(os != null){
                os.close();
            }
        }
    }
    private static <P extends Param> String sendParamToServer(P param) throws IOException {
        return sendParamToServer(param, DefList.url2);
    }

    private static <P extends Param> String sendParamToServerP(P param) throws IOException {
        return sendParamToServer(param, DefList.url3);
    }

    private static void initConnection(HttpURLConnection conn){
        if(conn == null)return;
        try {
            conn.setConnectTimeout(15*1000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.addRequestProperty("content-type","application/json");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }

    private static <P extends Param> String paramToEntrance(P param){
        String clazzName = param.getClass().getSimpleName();
        if(!clazzName.contains("Param")){
            throw new RuntimeException("the http param class's name must contains \"Parm\" in the end");
        }
        String exEntrance = clazzName.replace("Param","");
        if(exEntrance.isEmpty()){
            throw new RuntimeException("you should send the \"Param\"class's subClass");
        }
        String latterPart = exEntrance.substring(1);
        String firstCh = clazzName.substring(0,1).toLowerCase();
        return firstCh + latterPart;
    }
}
