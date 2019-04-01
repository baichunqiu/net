package com.qunli.network;

import android.view.View;

import com.bcq.oklib.UI;
import com.bcq.oklib.base.BaseActivity;
import com.bcq.oklib.net.listener.net.callback.BaseListCallback;
import com.bcq.oklib.net.utils.OKUtil;
import com.bcq.oklib.net.view.LoadDialog;
import com.bcq.oklib.net.view.PullToRefreshListView;
import com.bcq.oklib.utils.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetWorkActivity extends BaseActivity{
    private PullToRefreshListView prlv;
    private NetAdapter adapter;

    @Override
    public int setLayoutId() {
        return R.layout.activity_network;
    }

    @Override
    public void init() {
        prlv = getView(R.id.prlv);
        initData();
    }

    public void initData() {
        adapter = new NetAdapter(mActivity);
        prlv.setAdapter(adapter);
        getNetData(true, true);
        getView(R.id.select_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UI.startActivity(mActivity,NetMasterActivity.class);
            }
        });
    }


    public void getNetData(boolean showDialog, boolean isRefresh) {
        Map params = new HashMap<String, String>(2);
        // 作品评论列表
        String url = "https://alaya2.sabinetek.com/response/list";
        OKUtil.postArr(showDialog ? new LoadDialog(mActivity) : null, url, params, new BaseListCallback<LoadDialog, ResponBean>(prlv) {
            @Override
            public void onSuccess(LoadDialog tag, List<ResponBean> responBeans, Boolean loadFull) {
                super.onSuccess(tag, responBeans, loadFull);
                Logger.e(TAG,"BaseListCallback onSuccess:");
                adapter.setData(responBeans);
            }

            @Override
            public void onError(LoadDialog tag, int status, String errMsg) {
                Logger.d(TAG,"onError errMsg:"+errMsg);
            }

            @Override
            public void onAfter(LoadDialog tag, int status, String msg) {
                prlv.setLoadFull(true);
                Logger.d(TAG,"onAfter msg:"+msg);
            }
        });
    }

}
