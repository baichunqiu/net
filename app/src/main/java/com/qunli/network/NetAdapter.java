package com.qunli.network;

import android.content.Context;

import com.bcq.oklib.adapter.SampleAdapter;
import com.bcq.oklib.adapter.ViewHolder;



public class NetAdapter extends SampleAdapter<ResponBean> {
    public NetAdapter(Context context) {
        super(context, R.layout.layout_net);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ResponBean item, int position) {
        viewHolder.setText(R.id.tv_id, item.get_id());
        viewHolder.setText(R.id.tv_content, item.getContent());
        viewHolder.setText(R.id.tv_create, item.getCreate());
    }
}
