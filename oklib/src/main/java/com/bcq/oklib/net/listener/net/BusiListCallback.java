package com.bcq.oklib.net.listener.net;

import android.util.Log;

import com.bcq.oklib.net.domain.NetInfo;
import com.bcq.oklib.net.domain.Request;
import com.bcq.oklib.net.listener.net.callback.ICallback;
import com.bcq.oklib.net.listener.net.callback.IListCallback;
import com.bcq.oklib.net.utils.GsonUtil;
import com.bcq.oklib.net.utils.OKUtil;
import com.bcq.oklib.net.view.LoadDialog;
import com.bcq.oklib.utils.Logger;

import java.util.List;

/**
 * @author: BaiCQ
 * @ClassName: BusiListCallback
 * @Description: 业务处理回调
 */
public class BusiListCallback<R> extends OKCallback<List<R>, Boolean> {
    public BusiListCallback(Request<LoadDialog, List<R>, Boolean> request) {
        super(request);
    }

    @Override
    protected List<R> parseResult(NetInfo netInfo, ICallback<LoadDialog, List<R>, Boolean> iCallback) {
        if (CODE_OK != netInfo.getStatus() || !(iCallback instanceof IListCallback))
            return null;
        IListCallback<LoadDialog, R> iListCallback = (IListCallback<LoadDialog, R>) iCallback;
        //body 原始数据解析
        List<R> resultData = GsonUtil.json2List(netInfo.getBody(), iListCallback.setType());
        //预处理数据
        resultData = iListCallback.onPreprocess(resultData);
        Logger.e(TAG,"parseResult : "+(null == resultData ? 0 : resultData.size()));
        return resultData;
    }

    @Override
    protected Boolean parseExtra(NetInfo netInfo) {
        return CODE_OK == netInfo.getStatus() /*&& netInfo.getPageIndex() >= netInfo.getPageTotal()*/;
    }
}
