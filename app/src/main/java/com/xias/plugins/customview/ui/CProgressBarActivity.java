package com.xias.plugins.customview.ui;

import android.graphics.Color;
import android.os.Handler;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xias.plugins.base.ui.BaseActivity;
import com.xias.plugins.customview.R;
import com.xias.plugins.customview.util.ARouterUtil;
import com.xias.plugins.customview.view.CProgressBar;

@Route(path = ARouterUtil.C_VIEW_CPROGRESSBAR)
public class CProgressBarActivity extends BaseActivity {

    private CProgressBar cProgressBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cprogress_bar;
    }

    @Override
    protected void initView() {
        titleBar.setText(getResources().getString(R.string.c_progressbar));
        cProgressBar = $.id(R.id.activity_c_progressbar).view(CProgressBar.class);
    }

    @Override
    protected void initData() {
        cProgressBar.setMax(1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cProgressBar.setProgress(400);
            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cProgressBar.setProgress(800);
            }
        }, 3000);
    }
}
