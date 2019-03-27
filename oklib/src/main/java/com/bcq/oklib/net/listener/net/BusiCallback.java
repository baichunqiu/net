package com.bcq.oklib.net.listener.net;

import com.bcq.oklib.net.domain.NetInfo;
import com.bcq.oklib.net.domain.Request;
import com.bcq.oklib.net.listener.net.callback.ICallback;
import com.bcq.oklib.net.view.LoadDialog;

/**
 * @author: BaiCQ
 * @ClassName: BusiCallback
 * @Description: 业务处理 没有body的回调
 */
public class BusiCallback extends OKCallback<Integer, String> {
    public BusiCallback(Request<LoadDialog, Integer, String> request) {
        super(request);
    }

    @Override
    protected Integer parseResult(NetInfo netInfo, ICallback<LoadDialog, Integer, String> iCallback) {
        return netInfo.getStatus();
    }

    @Override
    protected String parseExtra(NetInfo netInfo) {
        return netInfo.getSysMsg();
    }
}
