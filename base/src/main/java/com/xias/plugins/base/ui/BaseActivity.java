package com.xias.plugins.base.ui;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.xias.plugins.base.R;
import com.xias.plugins.base.util.Query;
import com.xias.plugins.base.view.BaseTitleBar;

/**
 * Created by XIAS on 2018/9/17.
 */

public abstract class BaseActivity extends Activity {

    public Query $;

    public BaseTitleBar titleBar;

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        FrameLayout frameLayout = findViewById(R.id.base_activity_content);
        View view = LayoutInflater.from(this).inflate(getLayoutId(), null);
        frameLayout.addView(view);
        $ = Query.with(this.findViewById(R.id.base_activity_container));
        ImmersionBar.with(this).statusBarColor(R.color.colorPrimary).fitsSystemWindows(true).init();
        titleBar = $.id(R.id.base_activity_title_bar).view(BaseTitleBar.class);
        initView();
        initData();
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public boolean isAlive() {
        if (this == null)
            return false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return !(this.isDestroyed() || this.isFinishing());
        }
        return !this.isFinishing();
    }

}
