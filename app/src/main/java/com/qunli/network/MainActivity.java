package com.qunli.network;

import android.view.View;
import android.widget.TextView;

import com.bcq.oklib.UI;
import com.bcq.oklib.base.BaseActivity;

public class MainActivity extends BaseActivity {
    private TextView tv_content;
    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        tv_content = getView(R.id.tv_content);
        tv_content.setText(TAG);
        tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UI.startActivity(mActivity,NetWorkActivity.class);
                UI.startActivity(mActivity,TestFragmentActivity.class);
            }
        });
    }
}
