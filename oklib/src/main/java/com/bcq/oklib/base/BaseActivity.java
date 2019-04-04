package com.bcq.oklib.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bcq.oklib.UI;
import com.bcq.oklib.net.utils.NetType;

/**
 * @author: BaiCQ
 * @ClassName: BaseActivity
 * @date: 2018/8/17
 * @Description: 基类，AbsExitActivity的空实现
 */
public abstract class BaseActivity extends FragmentActivity implements IBase {
    protected final String TAG = this.getClass().getSimpleName();
    protected BaseActivity mActivity;
    private View layout;

    protected boolean setFullScreen(){
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppHelper.getInstance().remove(mActivity);
    }

    @Override @Deprecated
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        if (setFullScreen()) {//全屏
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        }
        AppHelper.getInstance().add(mActivity);
        layout = UI.inflate(setLayoutId());
        setContentView(layout);
        init();
    }

    protected View getLayout(){
        return layout;
    }

    protected <T extends View> T getView(@IdRes int id){
        return layout.findViewById(id);
    }

    @Override
    public void onRefresh(Object obj) {
    }

    @Override
    public void onNetChange(NetType netType) {
    }
}