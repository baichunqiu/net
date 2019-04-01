package com.bcq.oklib.net.controller;


import com.bcq.oklib.net.listener.net.callback.BaseCallback;
import com.bcq.oklib.net.listener.net.callback.BaseListCallback;
import com.bcq.oklib.net.utils.ApiType;
import com.bcq.oklib.net.utils.OKUtil;
import com.bcq.oklib.net.view.LoadDialog;
import com.bcq.oklib.utils.ObjUtil;

import java.util.List;
import java.util.Map;

/**
 * @author: BaiCQ
 * @createTime: 2017/1/13 11:38
 * @className: NetController
 * @Description: 供没有listview的页面使用的控制器
 */
public abstract class NetController<T>{
    private Class<T> tClass;

    public NetController() {
        this.tClass = ObjUtil.getTType(NetController.class)[0];
    }

    public void postArr(String mUrl, Map<String, String> params, LoadDialog dialog) {
        obtainNetData(mUrl, params, dialog, ApiType.POST);
    }

    public void getArr(final String mUrl, final Map<String, String> params, LoadDialog dialog) {
        obtainNetData(mUrl, params, dialog, ApiType.GET);
    }

    /**
     * 有返回数据集的post请求
     *
     * @param mUrl    mUrl
     * @param params  参数 注意：不包含page
     * @param dialog  进度条
     * @param apiType post/get
     */
    public void obtainNetData(final String mUrl, Map<String, String> params, LoadDialog dialog, ApiType apiType) {
        BaseListCallback<LoadDialog,T> baseListCallback = new BaseListCallback<LoadDialog,T>(){
            List<T> tempData = null;
            @Override
            public void onSuccess(LoadDialog tag, List<T> tList, Boolean loadFull) {
                tempData = tList;
            }
            @Override
            public void onAfter(LoadDialog tag, int status, String msg) {
                _bindData(tempData);
               _onResponceCallBack(mUrl,status,msg);
            }
            @Override
            public Class<T> setType() {
                return tClass;
            }
        };
        if (ApiType.GET == apiType) {
            OKUtil.getArr(dialog, mUrl, params, baseListCallback);
        } else {
            OKUtil.postArr(dialog, mUrl, params, baseListCallback);
        }
    }

    /**
     * 没有结果集，只有状态的post请求
     * @param mUrl   url
     * @param params 参数
     * @param dialog 进度条
     */
    public void post(final String mUrl, Map<String, String> params, LoadDialog dialog) {
        OKUtil.post(dialog,  mUrl, params, new BaseCallback<LoadDialog>(){
            @Override
            public void onAfter(LoadDialog tag, int status, String sysMsg) {
                super.onAfter(tag, status, sysMsg);
                _onResponceCallBack(mUrl,status,sysMsg);
            }
        });
    }

    /**
     * 接口响应回调
     *
     * @param url       接口连接
     * @param stateCode 状态 1：成功 其他：失败
     * @param msg       服务器返回msg
     */
    public abstract void _onResponceCallBack(String url, int stateCode, String msg);

    /**
     * 绑定数据
     *
     * @param mNetData
     */
    public abstract void _bindData(List<T> mNetData);

}