package com.xias.plugins.customview.util;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by XIAS on 2018/9/17.
 */

public class ActivityJump {

    public static void jumpToCEdittext() {
        ARouter.getInstance().build(ARouterUtil.C_VIEW_CEDITTEXT).navigation();
    }

    public static void jumpToCProgressBar() {
        ARouter.getInstance().build(ARouterUtil.C_VIEW_CPROGRESSBAR).navigation();
    }
}
