package com.qunli.network;

import android.os.Bundle;
import android.view.View;

import com.bcq.oklib.UI;
import com.bcq.oklib.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        UI.startActivity(mActivity,NetWorkActivity.class);
    }
}
