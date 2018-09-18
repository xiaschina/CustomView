package com.xias.plugins.customview;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;

/**
 * Created by XIAS on 2018/9/17.
 */

public class CApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (isInit()) {
            initArouter();
        }
    }

    //多个进程只在主进程中初始化
    private boolean isInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    //初始化路由框架
    private void initArouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }
}
