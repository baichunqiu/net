package com.bcq.oklib.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bcq.oklib.net.utils.NetType;

/**
 * @author: BaiCQ
 * @ClassName: BaseFragment
 * @date: 2018/8/17
 * @Description: Fragment 的基类
 */
public abstract class BaseFragment extends Fragment implements IRefresh, BaseActivity.OnNetChangeListeren {
    protected final String TAG = this.getClass().getSimpleName();
    protected BaseActivity mActivity;
    private View layout;

    @Override
    public final void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
        mActivity.setOnNetChangeListeren(this);
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != layout) {
            ViewGroup vg = (ViewGroup) layout.getParent();
            if (vg != null) vg.removeAllViewsInLayout();
        } else {
            layout = inflater.inflate(setLayoutId(), null);
        }
        return layout;
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    @Override
    public void onRefresh(Object obj) {
        Log.e(TAG, "onRefresh");
    }

    @Override
    public void onNetChange(NetType netType) {
        Log.e(TAG, "onNetChange");
    }

    /**
     * 填充和初始化view
     */
    protected abstract int setLayoutId();

    protected abstract void init(View view);
}
