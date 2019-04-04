package com.bcq.oklib.base;

import android.text.TextUtils;
import android.view.View;

import com.bcq.oklib.R;
import com.bcq.oklib.UI;
import com.bcq.oklib.adapter.OAdapter;
import com.bcq.oklib.net.Parser;
import com.bcq.oklib.net.domain.Request;
import com.bcq.oklib.net.listener.net.callback.BaseListCallback;
import com.bcq.oklib.net.listener.refresh.OnLoadListener;
import com.bcq.oklib.net.listener.refresh.OnRefreshListener;
import com.bcq.oklib.net.utils.ApiType;
import com.bcq.oklib.net.utils.OKUtil;
import com.bcq.oklib.net.view.BaseListView;
import com.bcq.oklib.net.view.LoadDialog;
import com.bcq.oklib.utils.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: BaiCQ
 * @createTime: 2017/1/13 11:38
 * @className: UIController
 * @Description: 供列表显示页面使用的控制器
 */
public class UIController<T>{
    private final String TAG = "UIController";
    private Class<T> tClass;
    private IOperator operator;
    public final static String PAGE_SIZE = "per_page";
    public final static String PAGE_INDEX = "page";

    public int pageSize = 15;//每页显示的记录数据 默认15条
    private int index = 1;//当前页的索引
    private Request<LoadDialog,List<T>,Boolean> request;
    //适配器使用功能集合 泛型不能使用 T 接口返回类型有可能和适配器使用的不一致
    private List adapterList = new ArrayList<>();
    private OAdapter mAdapter;
    private BaseListView baseListView;
    private View showData;
    private View noData;
    private View layout;

    public enum ShowType{
        Data,
        None
    }

    public UIController(View parent,Class<T> tClass, IOperator<T> operator) {
        this.layout = parent;
        this.tClass = tClass;
        this.operator = operator;
        init();
    }

    //init 手动调用
    private void init() {
        showData = UI.getView(layout,R.id.v_show_data);
        noData = UI.getView(layout,R.id.v_no_data);
        baseListView = UI.getView(layout,R.id.lv_base);
        if (null == showData) showData = baseListView;
        mAdapter = operator.setAdapter();
        baseListView.setAdapter(mAdapter);
        baseListView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestAgain(true);
            }
        });
        baseListView.setOnLoadListener(new OnLoadListener() {
            @Override
            public void onLoad() {
                requestAgain(false);
            }
        });
    }

    private void requestAgain(boolean refresh){
        if (null != request) {
            resetPageIndex(request.getParams(), refresh);
            request = (Request<LoadDialog, List<T>, Boolean>) request.requestAgain();
        }
    }

    private void resetPageIndex( Map<String, String> params,boolean refresh){
        String index = params.remove(PAGE_INDEX);
        int dex = 0;
        if (!refresh && !TextUtils.isEmpty(index)){
            dex = Integer.valueOf(index) + 1;
        }
        params.put(PAGE_INDEX,dex + "");
    }

    /**
     * 确保ObtainData执行在init执行完毕后的安全获取数据接口
     * @param isRefresh 是否刷新
     * @param mUrl      mUrl
     * @param params    参数 注意：不包含page
     * @param dialog    进度条
     * @param apiType   api类型 post/get
     */
//    public void securityObtainData(final boolean isRefresh, final String mUrl, final Map<String, String> params, final Parser parser, final LoadDialog dialog, final ApiType apiType) {
//       Logger.e(TAG,"securityObtainData");
//        if (isInitComplete) {//不需要切换
//            obtainNetData(isRefresh, mUrl, params, parser, dialog, apiType);
//        } else {//init 未执行完毕 但是执行obtainData方法 故需要手动调整执行序列。
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    synchronized (sequenceLocal) {
//                        while (!isInitComplete) {//未init完毕 等待
//                            try {
//                                Logger.e("UIController","wait ... init");
//                                sequenceLocal.wait();
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                    obtainNetData(isRefresh, mUrl, params, parser, dialog, apiType);
//                }
//            }).start();
//        }
//    }
    public void obtainNetData(final boolean isRefresh, String mUrl, Map<String, String> params, Parser parser, LoadDialog dialog, ApiType apiType) {
       Logger.e(TAG,"obtainNetData");
        if (isRefresh) index = 1;
        if (null == params) params = new HashMap<>(2);
        params.put(PAGE_INDEX, index + "");
        if (!params.containsKey(PAGE_SIZE)) {//参数没设置pageSize 使用默认
            params.put(PAGE_SIZE, pageSize + "");
        }
        BaseListCallback<LoadDialog, T> baseListCallback = new BaseListCallback<LoadDialog, T>(baseListView) {
            @Override
            public List<T> onPreprocess(List<T> rawData) {
                return operator.onPreprocess(rawData);
            }

            @Override
            public void onSuccess(LoadDialog tag, List<T> tList, Boolean loadFull) {
                super.onSuccess(tag, tList, loadFull);
                _onRefreshData(tList, isRefresh);
                if (!loadFull){
                    index ++;
                }
            }

            @Override
            public void onError(LoadDialog tag, int status, String errMsg) {
                super.onError(tag, status, errMsg);
                operator.onLoadError(status,errMsg);
                _onRefreshData(null, isRefresh);
            }

            @Override
            public Class<T> setType() {
                return tClass;
            }
        };
        if (apiType == ApiType.GET) {
            request = OKUtil.getArr(dialog, mUrl, params, parser, baseListCallback);
        } else {
            request = OKUtil.postArr(dialog, mUrl, params, parser, baseListCallback);
        }
    }

    /**
     * 设置适配器数据回调
     *
     * @param netData   接口放回数据
     * @param isRefresh 是否刷新
     */
    public final void _onRefreshData(List<T> netData, boolean isRefresh) {
        Logger.e(TAG,"_onRefreshData");
        //设置适配器前  数据处理
        List preData = operator.onPreRefreshData(netData,isRefresh);
        if (isRefresh) {
            adapterList.clear();
        }
        if (null != preData) {
            adapterList.addAll(preData);
        }
        if (!adapterList.isEmpty()) {
            showViewType(ShowType.Data);
            Logger.e(TAG,"onPreSetData");
            mAdapter.setData(operator.onPreSetData(adapterList));
        } else {
            showViewType(ShowType.None);
        }
    }

    public final void showViewType(ShowType showType) {
        showData.setVisibility(View.GONE);
        noData.setVisibility(View.GONE);
        if (ShowType.Data == showType) {
            showData.setVisibility(View.VISIBLE);
        } else {
            noData.setVisibility(View.VISIBLE);
        }
    }

    public interface IOperator<T> {
        /**
         * 解析数据时,数据预处理
         */
        List<T> onPreprocess(List<T> rawData);

        /**
         * 加载失败回调
         */
        void onLoadError(int status, String errMsg);

        /**
         * adapter设置数据前,数据处理。
         * 此处没使用泛型，特殊情况需要可能修改类型
         */
        List onPreRefreshData(List<T> netData, boolean isRefresh);

        /**
         * 设置Data to Adapter前 一般分页排序功能
         */
        List<T> onPreSetData(List<T> netData);

        OAdapter<T> setAdapter();
    }
}