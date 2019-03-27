package com.bcq.oklib.net.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.bcq.oklib.net.listener.refresh.OnLoadListener;
import com.bcq.oklib.net.listener.refresh.OnRefreshListener;


/**
 * @author: BaiCQ
 * @className: BaseListView
 * @Description: 所有刷新listview的基类
 */
public class BaseListView extends ListView {

    public BaseListView(Context context) {
        super(context);
    }
    public BaseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    /**
     * 刷新结束
     */
    public void onRefreshComplete() {}

    /**
     * 刷新监听
     * @param onRefreshListener
     */
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {}

    /*
     * load 结束
     */
    public void onLoadComplete() {}

    /**
     * load 监听
     * @param onRefreshListener
     */
    public void setOnLoadListener(OnLoadListener onRefreshListener) {}

    /**
     * 设置loadFull
     * @param isLoadFull
     */
    public void setLoadFull(boolean isLoadFull) {}
}
