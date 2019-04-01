package com.qunli.network;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.bcq.oklib.base.BaseActivity;

public class TestFragmentActivity extends BaseActivity {

    @Override
    public int setLayoutId() {
        return R.layout.activity_fragment_lay;
    }

    @Override
    public void init() {
        TestFragment fragment = new TestFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.ll_root, fragment);
        transaction.commitAllowingStateLoss();
    }
}
