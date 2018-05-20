package com.qg.kinectdoctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ZH_L on 2016/10/21.
 */
public abstract class ItemAdapter<E, VH extends ItemViewHolder> extends RecyclerView.Adapter<VH>{
    protected Context context;
    private List<E> mList;
    private LayoutInflater mInflater;
    private int mLayoutId;


    public ItemAdapter(Context context, List list, int layoutId){
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mList = list;
        mLayoutId = layoutId;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
         return createItemView(parent,viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        E element = mList.get(position);
        holder.bindElement(element);
    }

    /**
     * 用getView 获取到itemView之后，new相应的holder就好了
     * @param parent
     * @param viewType
     * @return
     */
    public abstract VH createItemView(ViewGroup parent, int viewType);

//    public abstract  void bindViewHolder(VH holder);

    public View getView(ViewGroup parent, int viewType){
        return mInflater.inflate(mLayoutId, parent, false);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
