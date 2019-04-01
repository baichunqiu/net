package com.bcq.oklib.net.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.ProgressBar;

import com.bcq.oklib.OKHelper;
import com.bcq.oklib.UI;
import com.bcq.oklib.net.Parser;
import com.bcq.oklib.net.domain.Request;
import com.bcq.oklib.net.listener.net.BusiCallback;
import com.bcq.oklib.net.listener.net.BusiListCallback;
import com.bcq.oklib.net.listener.net.callback.ICallback;
import com.bcq.oklib.net.listener.net.callback.IListCallback;
import com.bcq.oklib.net.view.LoadDialog;
import com.bcq.oklib.utils.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * @author: BaiCQ
 * @ClassName: OkUtil
 * @Description: OkHttp网络请求工具类
 */
public class OKUtil {
    public final static String TAG = "OKUtil";
    public final static String TOKEN_KEY = "authorization";
    private static String LAST_TOKEN = "";
    private static String BEARER = "Bearer";

    public static boolean isLogin(){
        return !TextUtils.isEmpty(LAST_TOKEN);
    }

    public static void releaseToken(){
        LAST_TOKEN = "";
    }

    public static void setToken(String tokenTemp){
        if (TextUtils.isEmpty(tokenTemp))return;
        if (!tokenTemp.startsWith(BEARER)){
            tokenTemp = BEARER +" " + tokenTemp;
        }
        LAST_TOKEN = tokenTemp;
    }

    private static void logParams(String msg, String url, Map<String, String> params) {
        Logger.e(TAG, msg + " : start---------------------------------");
        Logger.e(TAG, msg + " : url = " + url);
        Logger.e(TAG, msg + " : last_token = " + LAST_TOKEN);
        int size = params.size();
        if (size > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                Logger.e(TAG, msg + " : " + entry.getKey() + " = " + entry.getValue());
            }
        }
        Logger.e(TAG, msg + " : end---------------------------------");
    }

    private static <T> GetBuilder initGetBuider(T tag, String url, Map<String, String> params) {
        if (null == params) params = new HashMap<>(2);
        logParams("GET", url, params);
        return OkHttpUtils.get()
                .url(url)
                .tag(tag)
                .params(params)
                .addHeader(TOKEN_KEY,LAST_TOKEN);

    }

    private static <T> PostFormBuilder initPostBuilder(T tag, String url, Map<String, String> params) {
        if (null == params) params = new HashMap<>(2);
        logParams("POST", url, params);
        return OkHttpUtils.post()
                .url(url)
                .tag(tag)
                .params(params)
                .addHeader(TOKEN_KEY,LAST_TOKEN);
    }

    public static <T, R> Request postArr(T tag, String url, Map<String, String> params, IListCallback<T, R> IListCallback) {
        return postArr(tag, url, params, null, IListCallback);
    }

    /**
     * @param tag           请求的tag
     * @param url           url
     * @param params        Map<String,String>
     * @param parser        解析器
     * @param IListCallback 有body的网络回调
     * @param <T>           body实体的泛型
     * @param <R>           tag的泛型
     */
    public static <T, R> Request postArr(T tag, String url, Map<String, String> params, Parser parser, IListCallback<T, R> IListCallback) {
        Request request = new Request(tag, url, params, parser, IListCallback, ApiType.POST);
        if (check(tag, url)) {
            initPostBuilder(tag, url, params)
                    .build()
                    .execute(new BusiListCallback(request));
        }
        return request;
    }

    public static <T, R> Request getArr(T tag, String url, Map<String, String> params,IListCallback<T, R> IListCallback) {
        return getArr(tag, url, params, null, IListCallback);
    }

    /**
     * @param tag           请求的tag
     * @param url           url
     * @param params        Map<String,String>
     * @param parser        解析器
     * @param IListCallback 有body的网络回调
     * @param <T>           body实体的泛型
     * @param <R>           tag的泛型
     */
    public static <T, R> Request getArr(T tag, String url, Map<String, String> params,Parser parser, IListCallback<T, R> IListCallback) {
        Request request = new Request(tag, url, params, parser, IListCallback, ApiType.GET);
       if (check(tag,url)) {
            initGetBuider(tag, url, params)
                    .build()
                    .execute(new BusiListCallback(request));
        }
        return request;
    }

    public static <T> void post(T tag, String url, Map<String, String> params, ICallback<T,Integer,String> iCallback) {
        post(tag, url, params, null, iCallback);
    }

    /**
     * @param tag       请求的tag
     * @param url       url
     * @param params    Map<String,String>
     * @param parser    解析器
     * @param iCallback 无body的网络回调
     * @param <T>       tag的泛型
     */
    public static <T> Request post(T tag, String url, Map<String, String> params, Parser parser, ICallback<T,Integer,String> iCallback) {
        Request request = new Request(tag, url, params, parser, iCallback, ApiType.POST);
        if (check(tag,url)) {
            initPostBuilder(tag, url, params)
                    .build()
                    .execute(new BusiCallback(request));
        }
        return request;
    }

    public static <T> void get(T tag, String url, Map<String, String> params, ICallback<T,Integer,String> iCallback) {
        get(tag, url, params, null, iCallback);
    }

    /**
     * @param tag       请求的tag
     * @param url       url
     * @param params    Map<String,String>
     * @param parser    解析器
     * @param iCallback 无body的网络回调
     * @param <T>       tag的泛型
     */
    public static <T> Request get(T tag, String url, Map<String, String> params, Parser parser, ICallback<T,Integer,String> iCallback) {
        Request request = new Request(tag, url, params, parser, iCallback, ApiType.GET);
        if (check(tag,url)) {
            initGetBuider(tag, url, params)
                    .build()
                    .execute(new BusiCallback(new Request(tag, url, params, parser, iCallback, ApiType.GET)));
        }
        return request;
    }

    /**
     * 下载文件
     * @param tag
     * @param url
     * @param params
     * @param fileName 文件名称 存放在downLoad 目录下
     * @param <T>
     */
    public static <T> void downLoad(T tag, String url, Map<String, String> params, String fileName, final ProgressBar mProgressBar) {
        initGetBuider(tag, url, params)
                .build()
                .execute(new FileCallBack(OKHelper.getBasePath(), fileName) {
                    @Override
                    public void onError(Call call, Exception e, int id) {}

                    @Override
                    public void onResponse(File response, int id) {}

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        mProgressBar.setProgress((int) (100 * progress));
                    }
                });
    }

    /**
     * 检查网 并根据tag的类型 取消加载动画
     * @param tag 请求的TAG
     * @return
     */
    public static <T> boolean check(T tag,String url) {
        if (TextUtils.isEmpty(url)){
            Logger.e(TAG,"you net request url is null ，please check it！");
            return false;
        }
        boolean connected = isNetworkConnected();
        if (!connected && null != tag && tag instanceof LoadDialog) {
            ((LoadDialog) tag).dismiss();
        }
        return connected;
    }

    /**
     * 是否连接上网络
     * @return 连接上true，未连接上false
     * @date: 2017-1-16 下午17:52
     */
    public static boolean isNetworkConnected() {
        return NetType.NONE != getNetWorkState();
    }

    /**
     * 获取网络状态
     * @return
     */
    public static NetType getNetWorkState() {
        //得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) UI.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        //如果网络连接，判断该网络类型
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NetType.WIFI;//wifi
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NetType.MOBILE;//mobile
            }
        }//网络异常
        return NetType.NONE;
    }
}
