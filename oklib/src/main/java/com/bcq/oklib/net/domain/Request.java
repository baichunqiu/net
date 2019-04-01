package com.bcq.oklib.net.domain;

import com.bcq.oklib.net.Parser;
import com.bcq.oklib.net.listener.net.callback.BaseListCallback;
import com.bcq.oklib.net.listener.net.callback.IListCallback;
import com.bcq.oklib.net.utils.ApiType;
import com.bcq.oklib.net.listener.net.callback.ICallback;
import com.bcq.oklib.net.utils.OKUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author: BaiCQ
 * @ClassName: Request
 * @date: 2018/6/27
 * @Description: Request 再次请求参数封装实体
 */
public class Request<T,B,E> implements Serializable {
    private T tag;
    private String url;
    private Map<String, String> params;
    private Parser parser;
    private ICallback<T,B,E> iCallback;
    private ApiType type;

    public <I> Request(T tag, String url, Map<String, String> params, Parser parser, ICallback<T,B,E> iCallback, ApiType type) {
        this.tag = tag;
        this.url = url;
        this.params = params;
        this.parser = parser;
        this.iCallback = iCallback;
        this.type = type;
    }

    public T getTag() {
        return tag;
    }

    public ApiType getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public Parser getParser() {
        return parser;
    }

    public ICallback<T,B,E> getiCallback() {
        return iCallback;
    }

    private boolean isArr(){
        return null != iCallback && iCallback instanceof BaseListCallback;
    }

    /**
     * 再次发起请求
     */
    public <R> Request requestAgain() {
        switch (type) {
            case GET:
                if (isArr()){
                    return OKUtil.getArr(tag, url, params, parser, (IListCallback<T, R>)iCallback);
                }else {
                    return OKUtil.get(tag, url, params, parser, (ICallback<T, Integer,String>)iCallback);
                }
            case POST:
                if (isArr()){
                    return OKUtil.postArr(tag, url, params, parser, (IListCallback<T, R>)iCallback);
                }else {
                    return OKUtil.post(tag, url, params, parser, (ICallback<T, Integer,String>)iCallback);
                }
        }
        return null;
    }

}
