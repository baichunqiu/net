package com.bcq.oklib.base;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;

import com.bcq.oklib.net.utils.NetType;
import com.bcq.oklib.net.utils.OKUtil;
import com.bcq.oklib.utils.BroadcastManager;
import com.bcq.oklib.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: BaiCQ
 * @ClassName: AppHelper
 * @CreateDate: 2019/3/29 16:43
 * @Description: AppHelper
 */
public class AppHelper {
    private final static String TAG = "AppHelper";
    private final static AppHelper instance = new AppHelper();
    // 退出应用的广播接受者
    private BroadcastReceiver bdReceiver;
    private List<Activity> activities = new ArrayList<>(16);
    private List<OnNetChangeListeren> onNetListerens = new ArrayList<>(4);

    private AppHelper() {
        registBroadcast(new String[]{ConnectivityManager.CONNECTIVITY_ACTION});
    }

    private void registBroadcast(String[] actions) {
        bdReceiver = new BasisReceiver();
        BroadcastManager.registerReceiver(bdReceiver, actions);
    }

    public static AppHelper getInstance() {
        return instance;
    }

    public void add(Activity add) {
        activities.add(add);
        Logger.e(TAG, "activitie size = " + activities.size());
    }

    public void remove(Activity remove) {
        activities.remove(remove);
    }

    public void exit() {
        if (null != bdReceiver) {
            BroadcastManager.unregisterLocalReceiver(bdReceiver);
            bdReceiver = null;
        }
        for (Activity act : activities) {
            if (!act.isFinishing()) {
                act.finish();
            }
        }
    }

    public class BasisReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                AppHelper.this.setResult(OKUtil.getNetWorkState());
            }
        }
    }

    private void setResult(NetType netType) {
        for (Activity a : activities) {
            if (a instanceof BaseActivity) {
                ((BaseActivity) a).onNetChange(netType);
            }
        }
        for (OnNetChangeListeren ol : onNetListerens) {
            ol.onNetChange(netType);
        }
        Logger.e(TAG, "activitie size = " + activities.size());
        Logger.e(TAG, "onNetListerens size = " + onNetListerens.size());
    }

    public void addOnNetChangeListeren(OnNetChangeListeren ol) {
        onNetListerens.add(ol);
    }

    public void removeOnNetChangeListeren(OnNetChangeListeren ol) {
        onNetListerens.remove(ol);
    }

    public interface OnNetChangeListeren {
        void onNetChange(NetType netType);
    }

}
