package com.qg.kinectdoctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qg.kinectdoctor.R;
import com.qg.kinectdoctor.model.MedicalRecord;

import java.util.ArrayList;

/**
 * Created by 攀登者 on 2016/10/28.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> implements View.OnClickListener {

    private ArrayList<MedicalRecord> medicalRecordList;
    private Context mContext;

    public MyRecyclerAdapter(Context context, ArrayList<MedicalRecord> objects) {
        this.mContext = context;
        this.medicalRecordList = new ArrayList<>();
        this.medicalRecordList.addAll(objects);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    // define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, MedicalRecord data);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_item, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        // 将创建的View注册点击事件
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        viewHolder.name.setText(medicalRecordList.get(position).getPname());
        viewHolder.ageAndsex.setText(medicalRecordList.get(position).getAge() + "，" + (medicalRecordList.get(position).getSex() == 1 ? "男" : "女"));
        // 将数据保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(medicalRecordList.get(position));
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            // 注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (MedicalRecord) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    //获取数据的数量
    @Override
    public int getItemCount() {
        return medicalRecordList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView ageAndsex;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            ageAndsex = (TextView) itemView.findViewById(R.id.age_sex);
        }
    }
}