package com.qg.kinectdoctor.activity;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.qg.kinectdoctor.model.DUser;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * Created by HWF on 2018/5/26.
 */
public class App extends Application {

    private static App instance = null;
    private static final String TAG = App.class.getSimpleName();
    private DUser user = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;


        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        if (processAppName == null || !processAppName.equalsIgnoreCase(instance.getPackageName())) {
            Log.e(TAG, "enter the service process!");
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }

//        File dir = new File(Environment.getExternalStorageDirectory()+File.separator+getPackageName()+"voice");
//        if(!dir.exists()){
//            dir.mkdirs();
//        }
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (pID == info.pid) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                Log.e("Process", "Error>> :" + e.toString());
            }
        }
        return processName;
    }

    public static App getInstance() {
        return instance;
    }

    public synchronized DUser getUser() {
        return user;
    }

    public synchronized void setUser(DUser user) {
        this.user = user;
    }
}
