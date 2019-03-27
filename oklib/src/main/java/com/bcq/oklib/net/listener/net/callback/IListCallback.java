package com.bcq.oklib.net.listener.net.callback;

import java.util.List;

/**
 * @author: BaiCQ
 * @ClassName: IListCallback
 * @Description: 有body网络请求的回调
 */
public interface IListCallback<T, R> extends ICallback<T, List<R>, Boolean> {

    /**
     * 数据预处理
     *
     * @param rawData
     * @return
     */
    List<R> onPreprocess(List<R> rawData);

    @Override
    void onSuccess(T tag, List<R> rs, Boolean loadFull);

    Class<R> setType();
}
