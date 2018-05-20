package com.qg.kinectdoctor.util;

import com.qg.kinectdoctor.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZH_L on 2016/10/22.
 */
public class ActivityCollector {
    private List<BaseActivity> activities = new ArrayList<>();

    private ActivityCollector(){};

    private static ActivityCollector instance = null;

    public static ActivityCollector getInstance(){
        if(instance == null){
            synchronized (ActivityCollector.class){
                if(instance == null){
                    instance = new ActivityCollector();
                }
            }
        }
        return instance;
    }

    public void add(BaseActivity activity){
        activities.add(activity);
    }

    public void remove(BaseActivity activity){
        activities.remove(activity);
    }

    public boolean exist(Class<? extends BaseActivity> clazz){
        for(BaseActivity activity: activities){
            if(activity.getClass() == clazz){
                return true;
            }
        }
        return false;
    }

    public void finishAll(){
        for(BaseActivity activity: activities){
            activity.finish();
        }
    }
}
