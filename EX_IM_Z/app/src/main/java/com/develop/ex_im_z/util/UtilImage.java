package com.develop.ex_im_z.util;

import android.app.Activity;
import android.app.Application;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.develop.ex_im_z.MainApplication;

/**
 * Created by Administrator on 2016/10/22.
 */
public class UtilImage{
    public static void commonImageLoad(Activity context, String imageUrl, ImageView view) {
        Glide.with(context).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(view);
    }

    public static void commonRoundImageLoad(Activity context, String imageUrl, ImageView view) {
        Glide.with(context).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideCircleTransform(context))
                .crossFade()
                .into(view);
    }
}
