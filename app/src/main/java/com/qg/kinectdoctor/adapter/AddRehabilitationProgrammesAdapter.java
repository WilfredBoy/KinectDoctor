package com.qg.kinectdoctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.qg.kinectdoctor.R;
import com.qg.kinectdoctor.model.Action;

import java.util.ArrayList;

/**
 * Created by 攀登者 on 2016/10/30.
 */
public class AddRehabilitationProgrammesAdapter extends ArrayAdapter<Action> {

    private int resourceId;

    public AddRehabilitationProgrammesAdapter(Context context, int resource, ArrayList<Action> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        Action action = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (converView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.stage_name = (TextView) view.findViewById(R.id.stage_name);
            view.setTag(viewHolder);
        } else {
            view = converView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.stage_name.setText(action.getName());
        return view;
    }

    class ViewHolder {
        TextView stage_name;
    }
}