package com.bcq.oklib.base;

import com.bcq.oklib.net.utils.NetType;

/**
 * @author: BaiCQ
 * @ClassName: IRefresh
 * @Description: 刷新UI的接口
 */
public interface IRefresh {

    /**
     * 刷新UI回调接口
     *
     * @param obj
     */
    void onRefresh(Object obj);

    /**
     * 网络变化回调接口
     * @param netType
     */
    void onNetChange(NetType netType);

}
