package com.bcq.oklib.net.view;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bcq.oklib.R;
import com.bcq.oklib.UI;
import com.bcq.oklib.net.view.progress.SpriteFactory;
import com.bcq.oklib.net.view.progress.Style;
import com.bcq.oklib.net.view.progress.sprite.Sprite;

/**
 * @author: BaiCQ
 * @ClassName: LoadDialog
 * @Description: 标准等待进度条
 */
public class LoadDialog{
    private Dialog dialog;
    private View rootView;
    private TextView textView;
    private String dialogMsg;
    private ProgressBar progressBar;
    private int styleIndex = 2;

    public LoadDialog(Activity activity) {
        this(activity, R.string.net_loading);
    }
    public LoadDialog(Activity activity,int strId) {
        this(activity, UI.getResources().getString(strId));
    }
    public LoadDialog(Activity activity, String dialogMsg) {
        this.dialogMsg = dialogMsg;
        dialog = new Dialog(activity, R.style.CustomProgressDialog);
        rootView = LayoutInflater.from(activity).inflate(R.layout.net_layout_load_dialog, null);
        progressBar = (ProgressBar) rootView.findViewById(R.id.prgressBar);
        refreshStyle();
        textView = (TextView) rootView.findViewById(R.id.tv_load_msg);

        textView.setText(dialogMsg);
        // 允许点返回键取消
        dialog.setCancelable(true);
        // 触碰其他地方不消失
        dialog.setCanceledOnTouchOutside(false);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.addContentView(rootView, params);
        if (null != dialog){
            dialog.show();
        }
    }

    /**
     * 刷新style
     */
    private void refreshStyle() {
        styleIndex = styleIndex % 15;
        Style style = Style.values()[styleIndex];
        Sprite drawable = SpriteFactory.create(style);
        progressBar.setIndeterminateDrawable(drawable);
    }

    public void dismiss() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getMsg(){
        return dialogMsg;
    }
}