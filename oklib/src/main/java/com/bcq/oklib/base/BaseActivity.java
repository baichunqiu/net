package com.bcq.oklib.base;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.bcq.oklib.net.utils.NetType;
import com.bcq.oklib.net.utils.OKUtil;
import com.bcq.oklib.utils.Logger;

import java.util.List;

/**
 * @author: BaiCQ
 * @ClassName: BaseActivity
 * @date: 2018/8/17
 * @Description: 基类，AbsExitActivity的空实现
 */
public class BaseActivity extends AbsExitActivity implements IRefresh{
    protected BaseActivity mActivity;
    private OnNetChangeListeren onNetChangeListeren;

    protected boolean setFullScreen(){
        return false;//默认沉浸式
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        //全屏
        if (setFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        }
        //自动调整，避免软件盘遮挡控件
        getWindow().setSoftInputMode
                (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    /**
     * 自定义添加action的方法
     * @param actionList 存储广播action集合
     */
    @Override
    protected void buildFilterAction(List<String> actionList) {
        actionList.add(ConnectivityManager.CONNECTIVITY_ACTION);
    }

    @Override
    protected void onReceive(Context context, String action,Intent intent) {
        Logger.e(TAG, "onReceive: action = "+action);
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
            NetType netType = OKUtil.getNetWorkState();
            onNetChange(netType);
            if (null != onNetChangeListeren){
                onNetChangeListeren.onNetChange(netType);
            }
        }
    }

    @Override
    public void onRefresh(Object obj) {
        Logger.e(TAG, "onRefresh");
    }

    @Override
    public void onNetChange(NetType netType) {
        Logger.e(TAG, "onNetChange:netType = "+netType);
    }

    public void setOnNetChangeListeren(OnNetChangeListeren onNetChangeListeren) {
        this.onNetChangeListeren = onNetChangeListeren;
    }

    public interface OnNetChangeListeren{
        void onNetChange(NetType netType);
    }
}