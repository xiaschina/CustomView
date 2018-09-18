package com.xias.plugins.customview.ui;

import android.view.View;
import android.widget.Button;

import com.xias.plugins.base.ui.BaseActivity;
import com.xias.plugins.customview.R;
import com.xias.plugins.customview.util.ActivityJump;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        titleBar.isShowImage(false);
        titleBar.setText(getResources().getString(R.string.app_name));
        $.id(R.id.c_edittext_password).view(Button.class)
                .setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.c_edittext_password:
                ActivityJump.jumpToCEdittext();
                break;
        }
    }
}
