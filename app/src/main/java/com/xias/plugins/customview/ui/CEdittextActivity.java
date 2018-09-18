package com.xias.plugins.customview.ui;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xias.plugins.base.ui.BaseActivity;
import com.xias.plugins.customview.R;
import com.xias.plugins.customview.util.ARouterUtil;
import com.xias.plugins.customview.view.CEditText;

@Route(path = ARouterUtil.C_VIEW_CEDITTEXT)
public class CEdittextActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cedittext;
    }

    @Override
    protected void initView() {
        titleBar.setText(getResources().getString(R.string.c_password));
        $.id(R.id.c_edit_text_view_text).view(CEditText.class)
                .setOnFinishListener(new CEditText.OnFinishListener() {
            @Override
            public void onFinish(String msg) {
                showToast(msg);
            }
        });

        $.id(R.id.c_edit_text_view_circle).view(CEditText.class)
                .setOnFinishListener(new CEditText.OnFinishListener() {
                    @Override
                    public void onFinish(String msg) {
                        showToast(msg);
                    }
                });

        $.id(R.id.c_edit_text_view).view(CEditText.class)
                .setOnFinishListener(new CEditText.OnFinishListener() {
                    @Override
                    public void onFinish(String msg) {
                        showToast(msg);
                    }
                });
    }

    @Override
    protected void initData() {

    }
}
