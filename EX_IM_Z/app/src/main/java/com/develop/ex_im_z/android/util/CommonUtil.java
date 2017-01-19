package com.develop.ex_im_z.android.util;

import android.app.Activity;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zyp on 2016/12/29.
 */
public class CommonUtil {

    public static void showToast(Activity activity , String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
}
