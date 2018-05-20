package com.qg.kinectdoctor.model;

/**
 * Created by ZH_L on 2016/10/22.
 */
public class RcStage {
    private int id;
    private int mrId;//病历id，所属病历
    private int num;//所属阶段
//    private Action action;//阶段动作
    private int actionId;   //动作id
    private String actionName;  //动作名称
    private float matchValue;//匹配数值
    private int puserId; //病人id

    public RcStage(){};

    public RcStage(int mrId, int num, float matchValue, int actionId, String actionName, int puserId) {
        this.mrId = mrId;
        this.num = num;
//        this.action = action;
        this.matchValue = matchValue;
        this.actionId = actionId;
        this.actionName = actionName;
        this.puserId = puserId;
    }

    public int getMrId() {
        return mrId;
    }

    public void setMrId(int mrId) {
        this.mrId = mrId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public float getMatchValue() {
        return matchValue;
    }

    public void setMatchValue(float matchValue) {
        this.matchValue = matchValue;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getPuserId() {
        return puserId;
    }

    public void setPuserId(int puserId) {
        this.puserId = puserId;
    }
}
