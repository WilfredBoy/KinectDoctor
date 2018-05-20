package com.qg.kinectdoctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hyphenate.chat.EMVoiceMessageBody;
import com.qg.kinectdoctor.R;
import com.qg.kinectdoctor.emsdk.EMConstants;
import com.qg.kinectdoctor.model.VoiceBean;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by ZH_L on 2016/10/25.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.VoiceHolder>{
    private static final String TAG = ChatAdapter.class.getSimpleName();

    private Context context;
    private List<VoiceBean> list;

    public ChatAdapter(Context context, List<VoiceBean> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public ChatAdapter.VoiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = getLayoutId();
        if(layoutId == 0) return null;
        final View v = LayoutInflater.from(context).inflate(layoutId, null);
        return new VoiceHolder(v);
    }

    private int getLayoutId(){
        int layoutId = R.layout.chat_row_layout;
        return layoutId;
    }

    @Override
    public void onBindViewHolder(ChatAdapter.VoiceHolder holder, int position) {
        VoiceBean bean = list.get(position);
        holder.bindElement(bean);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }


    public class VoiceHolder extends ItemViewHolder<VoiceBean>{

        private View itemTime, itemPatient, itemDoctor;
        private TextView timeTv;    //type time

        //type patient
        private Button patientBtn;
        private TextView pLengthTv;

        //type doctor
        private Button doctorBtn;
        private TextView dLengthTv;

        public VoiceHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindElement(VoiceBean bean) {
            switch(bean.getType()){
                case EMConstants.VIEWTYPE_TIME:
                    timeTv.setText(bean.getTime());
                    itemTime.setVisibility(View.VISIBLE);
                    itemDoctor.setVisibility(View.GONE);
                    itemPatient.setVisibility(View.GONE);
                    break;
                case EMConstants.VIEWTYPE_SOMEONE:

                    EMVoiceMessageBody pBody = bean.getVoice();
                    int pLen = pBody.getLength()/1000;
                    String dTimeLen = ""+((int)pBody.getLength()/1000);
                    pLengthTv.setText(""+dTimeLen+"''");
                    StringBuilder pSb = new StringBuilder();
                    for(int i=0; i<pLen;i++){
                        pSb.append("  ");
                    }
                    doctorBtn.setText(pSb.toString());
                    patientBtn.setTag(bean);
                    patientBtn.setOnClickListener(this);
                    if(bean.isPlaying()){
                        patientBtn.setClickable(false);
                        patientBtn.setBackgroundResource(R.drawable.patient_voice_click);
                    }else{
                        patientBtn.setBackgroundResource(R.drawable.patient_voice_normal);
                        patientBtn.setClickable(true);
                    }
                    itemTime.setVisibility(View.GONE);
                    itemDoctor.setVisibility(View.GONE);
                    itemPatient.setVisibility(View.VISIBLE);
                    break;
                case EMConstants.VIEWTYPE_ME:
                    EMVoiceMessageBody dBody = bean.getVoice();
                    int dLen = dBody.getLength()/1000;
                    String pTimeLen = ""+((int)dBody.getLength()/1000);
                    dLengthTv.setText("" + pTimeLen +"''");
                    StringBuilder dSb = new StringBuilder();
                    for(int i=0; i<dLen;i++){
                        dSb.append("  ");
                    }
                    doctorBtn.setText(dSb.toString());
                    doctorBtn.setTag(bean);
                    doctorBtn.setOnClickListener(this);
                    if(bean.isPlaying()){
                        doctorBtn.setClickable(false);
                        doctorBtn.setBackgroundResource(R.drawable.doctor_voice_click);
                    }else{
                        doctorBtn.setBackgroundResource(R.drawable.doctor_voice_normal);
                        doctorBtn.setClickable(true);
                    }
                    itemTime.setVisibility(View.GONE);
                    itemDoctor.setVisibility(View.VISIBLE);
                    itemPatient.setVisibility(View.GONE);
                    break;
            }
        }

        @Override
        public void initChilds(View itemView) {
           itemTime = itemView.findViewById(R.id.item_chat_time);
            itemDoctor = itemView.findViewById(R.id.item_chat_doctor);
            itemPatient = itemView.findViewById(R.id.item_chat_patient);
            timeTv = (TextView) itemView.findViewById(R.id.time_tv);
            pLengthTv = (TextView)itemView.findViewById(R.id.p_time_length_tv);
            patientBtn = (Button) itemView.findViewById(R.id.patient_voice_btn);
            dLengthTv = (TextView)itemView.findViewById(R.id.d_time_length_tv);
            doctorBtn = (Button) itemView.findViewById(R.id.doctor_voice_btn);
        }

        @Override
        public void onClick(View view) {
            VoiceBean bean = null;
            switch(view.getId()){
                case R.id.patient_voice_btn:
                    patientBtn.setClickable(false);
                    patientBtn.setBackgroundResource(R.drawable.patient_voice_click);
                    bean  = (VoiceBean) patientBtn.getTag();
                    break;
                case R.id.doctor_voice_btn:
                    doctorBtn.setClickable(false);
                    doctorBtn.setBackgroundResource(R.drawable.doctor_voice_click);
                    bean = (VoiceBean) doctorBtn.getTag();
                    break;
            }
            if(bean != null && mListener != null){
                mListener.onVoiceClick(bean, getPosition());
            }
        }
    }

    private OnItemVoiceClickListener mListener;
    public void setOnItemVoiceClickListener(OnItemVoiceClickListener listener){
        mListener = listener;
    }

    public interface OnItemVoiceClickListener{
        void onVoiceClick(VoiceBean voiceBean, int position);
    }
}
