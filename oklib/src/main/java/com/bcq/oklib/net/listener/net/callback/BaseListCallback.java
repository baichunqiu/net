package com.bcq.oklib.net.listener.net.callback;


import com.bcq.oklib.net.view.BaseListView;
import com.bcq.oklib.utils.Logger;
import com.bcq.oklib.utils.ObjUtil;

import java.util.List;

/**
 * @author: BaiCQ
 * @ClassName: BaseListCallback
 * @Description: 有body网络请求的回调
 */
public class BaseListCallback<T, R> implements IListCallback<T, R> {
    private BaseListView baseListView;
    protected Class<R> rClass;

    public BaseListCallback() {
        this(null);
    }

    public BaseListCallback(BaseListView baseListView) {
        this.baseListView = baseListView;
        rClass = (Class<R>) ObjUtil.getTType(BaseListCallback.this)[1];
    }

    @Override
    public List<R> onPreprocess(List<R> rawData) {
        return rawData;
    }

    /**
     * @param tag      Tag
     * @param rs       网络数据
     * @param loadFull 当前页码<分页使用>
     */
    public void onSuccess(T tag, List<R> rs, Boolean loadFull) {
        if (null != baseListView) {
            baseListView.setLoadFull(loadFull);
        }
    }

    /**
     * @param tag    Tag
     * @param errMsg 错误信息
     */
    public void onError(T tag, int status, String errMsg) {
        Logger.e("BaseListCallback", "onError:" + errMsg);
    }

    public void onNoData(T tag) {
    }

    @Override
    public void onAfter(T tag, int status,String msg) {
        if (null != baseListView) {
            baseListView.onRefreshComplete();
            baseListView.onLoadComplete();
        }
    }

    @Override
    public Class<R> setType() {
        return rClass;
    }
}
