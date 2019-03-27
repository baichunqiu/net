package com.bcq.oklib.net.listener.net.callback;

/**
 * @author: BaiCQ
 * @ClassName: ICallback
 * @Description: 没有body请求回调
 */
public class BaseCallback<T> implements ICallback<T, Integer, String> {

    /**
     * @param tag    Tag
     * @param status
     */
    @Override
    public void onSuccess(T tag, Integer status, String sysMsg) {
    }

    /**
     * @param tag    Tag
     * @param errMsg 错误信息
     */
    @Override
    public void onError(T tag, int status, String errMsg) {
    }

    @Override
    public void onAfter(T tag, int status,String sysMsg) {
    }
}
