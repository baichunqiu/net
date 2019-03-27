package com.bcq.oklib.net.listener.net.callback;

/**
 * @author: BaiCQ
 * @ClassName: ICallback
 * @Description: 网络请求回调接口
 *  @param <T> tag 泛型
 *  @param <B> result 泛型
 *  @param <E> extra信息 泛型
 */
public interface ICallback<T, B, E> {

    /**
     * @param tag    Tag
     * @param b
     * @param extra 额外信息
     */
    void onSuccess(T tag, B b, E extra);

    /**
     * @param tag    Tag
     * @param statusCode 状态
     * @param errMsg 错误信息
     */
    void onError(T tag, int statusCode, String errMsg);

    /**
     * @param tag 系统返回信息
     */
    void onAfter(T tag, int status,String msg);
}
