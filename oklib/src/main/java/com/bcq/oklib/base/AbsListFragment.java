package com.bcq.oklib.base;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bcq.oklib.R;
import com.bcq.oklib.UI;
import com.bcq.oklib.net.Parser;
import com.bcq.oklib.net.utils.ApiType;
import com.bcq.oklib.net.view.LoadDialog;
import com.bcq.oklib.utils.ObjUtil;

import java.util.List;
import java.util.Map;

/**
 * @author: BaiCQ
 * @createTime: 2017/1/13 11:38
 * @className: AbsListFragment
 * @Description:
 */
public abstract class AbsListFragment<T> extends BaseFragment implements UIController.IOperator<T> {
    private Class<T> tClass;
    private UIController<T> mController;
    private View contentView;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_abs_list;
    }

    public final void init() {
        resetLayoutView();
        tClass = (Class<T>) ObjUtil.getTType(getClass())[0];
        mController = new UIController<T>(getLayout(), tClass, this);
        mController.init();
        initView(contentView);
    }

    private void resetLayoutView() {
        FrameLayout ll_content = getView(R.id.ll_content);
        contentView = setContentView();
        ll_content.addView(contentView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        //覆盖当前视图的v_show_data
        View show_data = UI.getView(contentView, R.id.v_show_data);
        ViewGroup extraParent = null != show_data ? (ViewGroup) show_data.getParent() : ll_content;
        extraParent.addView(UI.inflate(R.layout.no_data), FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
    }

    public void getNetData(boolean isRefresh, String mUrl, Map<String, String> params, String mDialogMsg) {
        getNetData(isRefresh, mUrl, params, null, mDialogMsg, ApiType.GET);
    }

    public void getNetData(boolean isRefresh, String mUrl, Map<String, String> params, String mDialogMsg, ApiType apiType) {
        getNetData(isRefresh, mUrl, params, null, mDialogMsg, apiType);
    }

    /**
     * @param isRefresh  是否刷新
     * @param mUrl       mUrl
     * @param params     参数 注意：不包含page
     * @param parser     解析器
     * @param mDialogMsg 进度条显示msg
     * @param apiType    Post/get
     */
    public void getNetData(boolean isRefresh, String mUrl, Map<String, String> params, Parser parser, String mDialogMsg, ApiType apiType) {
        if (null != mController)
            mController.securityObtainData(isRefresh, mUrl, params, parser, TextUtils.isEmpty(mDialogMsg) ? null : new LoadDialog(mActivity, mDialogMsg), apiType);
    }

    public void onRefreshData(List<T> netData, boolean isRefresh) {
        if (null != mController) mController._onRefreshData(netData, isRefresh);
    }

    /**
     * 接口解析数据后子线程预处理数据
     *
     * @param netData
     */
    @Override
    public List<T> onPreprocess(List<T> netData) {
        return netData;
    }

    /**
     * 适配器设置数据前 处理数据 有可能类型转换
     *
     * @param netData
     */
    @Override
    public List onPreRefreshData(List<T> netData, boolean isRefresh) {
        return netData;
    }

    @Override
    public List<T> onPreSetData(List<T> netData) {
        return netData;
    }

    @Override
    public void onLoadError(int status, String errMsg) {}


    public abstract View setContentView();

    public abstract void initView(View view);
}
