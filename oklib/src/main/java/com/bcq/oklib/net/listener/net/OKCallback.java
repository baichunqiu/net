package com.bcq.oklib.net.listener.net;

import android.text.TextUtils;

import com.bcq.oklib.OKHelper;
import com.bcq.oklib.net.Parser;
import com.bcq.oklib.net.domain.NetInfo;
import com.bcq.oklib.net.domain.Request;
import com.bcq.oklib.net.listener.net.callback.ICallback;
import com.bcq.oklib.net.utils.OKUtil;
import com.bcq.oklib.net.view.LoadDialog;
import com.bcq.oklib.utils.Logger;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author: BaiCQ
 * @ClassName: BusiCallback
 * @Description: 业务处理 没有body的回调
 */
public abstract class OKCallback<B,E> extends Callback<B> {
    protected final String TAG = this.getClass().getSimpleName();
    public final static int CODE_OK = 0;
    public final static String _unKnow_error = "未知错误!";
    private ICallback<LoadDialog, B,E> iCallback;
    private LoadDialog tTag;
    private int status;
    private E extra;
    private String sysMsg = "";
    private Parser parser;
    private Request<LoadDialog, B,E> request;

    public OKCallback(Request<LoadDialog, B,E> request) {
        this.request = request;
        this.iCallback = request.getiCallback();
        this.tTag = request.getTag();
        this.parser = null == request.getParser() ? OKHelper.getParser() : request.getParser();
    }

    @Override
    public B parseNetworkResponse(Response response, int id) throws Exception {
        // 请求提示语
        OKUtil.setToken(response.header(OKUtil.TOKEN_KEY, ""));
        String res = response.body().string();
        NetInfo netInfo = null;
        if (!TextUtils.isEmpty(res)) {
            Logger.e(TAG, "url = " + request.getUrl());
            Logger.e(TAG, "res = " + res);
            JsonObject resulObject = new JsonParser().parse(res).getAsJsonObject();
            if (null != parser) {
                netInfo = parser.parse(resulObject);
                if (null != netInfo) {
                    sysMsg = netInfo.getSysMsg();
                    status = netInfo.getStatus();
                    extra = parseExtra(netInfo);
                    Logger.e(TAG, "status = " + status);
                    if (CODE_OK != status && null != OKHelper.getProcessor()) {
                        //请求失败 特殊 error code 处理器统一处理
                        OKHelper.getProcessor().process(status, request);
                    }
                }
            }
        }
        return parseResult(netInfo,iCallback);
    }

    protected abstract B parseResult(NetInfo netInfo,ICallback<LoadDialog, B,E> iCallback);
    protected abstract E parseExtra(NetInfo netInfo);

    @Override
    public void onResponse(B b, int id) {
        boolean success = false;
        if (b instanceof Integer){
            success = CODE_OK == (Integer) b;
        }else if (b instanceof List){
            success = null != b && !((List)b).isEmpty();
        }
        if (success){
            iCallback.onSuccess(tTag,b,extra);
        } else {
            iCallback.onError(tTag, status, TextUtils.isEmpty(sysMsg) ? _unKnow_error : sysMsg);
        }
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        sysMsg = e.getLocalizedMessage();//错误信息级别比接口返回信息稿
        iCallback.onError(tTag, status, sysMsg);
    }

    @Override
    public void onAfter(int id) {
        if (null != tTag) tTag.dismiss();
        iCallback.onAfter(tTag, status, sysMsg);
    }
}
