package com.qg.kinectdoctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ZH_L on 2016/10/30.
 */

public class PatientProAdapter extends ItemAdapter{
    private static final String TAG = PatientProAdapter.class.getSimpleName();

    public PatientProAdapter(Context context, List list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    public ItemViewHolder createItemView(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
