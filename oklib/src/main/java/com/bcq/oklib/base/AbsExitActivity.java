package com.bcq.oklib.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import com.bcq.oklib.Constant;
import com.bcq.oklib.utils.BroadcastManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: BaiCQ
 * @ClassName: AbsExitActivity
 * @date: 2018/8/17
 * @Description: 基类 统一处理app exit功能和返回键点击事件
 *              给子类提供自定义action的buildFilterAction入口和广播处理的onReceive入口
 */
public abstract class AbsExitActivity extends FragmentActivity {
    protected final String TAG = this.getClass().getSimpleName();
    protected String mPackageName;
    // 退出应用的广播接受者
    private BroadcastReceiver basisReceiver;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BroadcastManager.unregisterLocalReceiver(basisReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver();
    }

    private void registerReceiver() {
        basisReceiver = new BasisReceiver();
        mPackageName = this.getPackageName();
        //注册统一广播 处理退出app的功能
        List<String> actionList = new ArrayList<>(2);
        actionList.add(mPackageName + Constant.ACTION_APP_EXIT);
        buildFilterAction(actionList);
        BroadcastManager.registerReceiver(basisReceiver,actionList.toArray(new String[actionList.size()]));
    }

    public class BasisReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ((mPackageName + Constant.ACTION_APP_EXIT).equals(action)) {//退出
                finish();
            } else {
                AbsExitActivity.this.onReceive(context, action,intent);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBackCode();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onBackCode();
    }

    /**
     * 统一处理activity 的销毁关闭和back code 的时间
     */
    public void onBackCode() {
        finish();
    }


    /**
     * 提供一个添加自定义广播action的入口
     * @param actionList
     */
    protected abstract void buildFilterAction(List<String> actionList);

    /**
     * 处理自定义广播action的入口， 参数参考广播的onReceive
     * @param context Context
     * @param action String
     * @param intent 意图
     */
    protected abstract void onReceive(Context context, String action,Intent intent);

}
