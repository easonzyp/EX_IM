package com.develop.ex_im_z;

import android.app.Application;

import imsdk.data.IMSDK;

/**
 * Created by Administrator on 2016/10/20.
 */
public class MainApplication extends Application {
    // demo
    private static final String imsdk_sAppKey = "20eca8ab58a1519026974717";
    private static final String pyx_sAppKey = "55f2769040e80e87784a04a6";
    private static final String sAppKey = imsdk_sAppKey;

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化IMSDK
        IMSDK.init(getApplicationContext(), sAppKey);
    }
}
