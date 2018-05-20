package com.qg.kinectdoctor.emsdk;

import com.hyphenate.chat.EMVoiceMessageBody;
import com.qg.kinectdoctor.model.VoiceBean;

/**
 * Created by ZH_L on 2016/10/25.
 */
public class PlayTask {

    private VoiceBean voiceBean;

    public PlayTask(VoiceBean voiceBean){
        this.voiceBean = voiceBean;
    }

    public VoiceBean getVoiceBean() {
        return voiceBean;
    }

    public void setVoiceBean(VoiceBean voiceBean) {
        this.voiceBean = voiceBean;
    }
}
