package com.qg.kinectdoctor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ZH_L on 2016/10/21.
 */public abstract class ItemViewHolder<E> extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ItemViewHolder(View itemView) {
        super(itemView);
        initChilds(itemView);

    }

    /**
     * 给holder绑定数据
     * @param element
     */
    public abstract void bindElement(E element);

    /**
     * 初始化holder中的子view，如果有的话
     * @param itemView
     */
    public abstract void initChilds(View itemView);

    @Override
    public void onClick(View view) {

    }
}
