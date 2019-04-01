package com.qunli.network;

import android.view.View;

import com.bcq.oklib.UI;
import com.bcq.oklib.adapter.OAdapter;
import com.bcq.oklib.base.AbsListActivity;
import com.bcq.oklib.base.AbsListFragment;
import com.bcq.oklib.net.utils.ApiType;
import com.bcq.oklib.utils.Logger;


public class TestFragment extends AbsListFragment<ResponBean> {

    @Override
    public View setContentView() {
        return UI.inflate(R.layout.activity_net_master);
    }

    @Override
    public void initView(View view) {
        getDataFromNet(true);
    }

    public void getDataFromNet(boolean isRefresh) {
        // 作品评论列表
        String url = "https://alaya2.sabinetek.com/response/list";
        super.getNetData(isRefresh, url, null, "什么情况",ApiType.POST);
    }

    @Override
    public OAdapter setAdapter() {
        Logger.e(TAG,"setAdapter");
        return new NetAdapter(mActivity);
    }
}
