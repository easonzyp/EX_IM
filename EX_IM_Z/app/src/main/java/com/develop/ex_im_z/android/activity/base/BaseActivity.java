package com.develop.ex_im_z.android.activity.base;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;

import com.develop.ex_im_z.R;
import com.develop.ex_im_z.android.activity.CustomTitle;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public abstract class BaseActivity extends FragmentActivity {
    protected FragmentManager fragmentManager = getSupportFragmentManager();
    protected FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    protected CustomTitle title;

    protected void onCreateNoTitle(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        initCreate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        this.setTheme(R.style.titleTheme);
        // 加载布局
        initCreate();
        initTitle();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 设置布局
     */
    private void initCreate() {
        setContentView(this.getLayoutId());
        //设置页面背景颜色
        ColorDrawable bgDrawable = new ColorDrawable(getResources().getColor(R.color.white));
        this.getWindow().setBackgroundDrawable(bgDrawable);
    }


    // 初始化title
    protected void initTitle() {
        title = this.setCustomTitle();
    }

    protected CustomTitle setCustomTitle() {
        return null;
    }

    ;

    public CustomTitle getCustomTitle() {
        return title;
    }

    /**
     * 获取布局
     *
     * @return
     */
    protected abstract int getLayoutId();
    // 是否在底部tab中
    public boolean isInTab() {
        return false;
    }

    // 获取中间
    public abstract String getTitleCenter();

    // 左部点击，加入栈
    public View.OnClickListener getLeftListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.this.finish();
//                MainApplication.finishActivity(BaseActivity.this);
            }
        };
    }

    // 右边点击，清除栈，进入home
    public View.OnClickListener getRightListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    public View getDefaultView() {
        return null;
    }

    public PullToRefreshListView getPullRefreshView() {
        return null;
    }
    public PullToRefreshGridView getPullRefreshGridView() {
        return null;
    }

    /***
     * 重写启动activity方法
     ***/
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent, Bundle options) {
        super.startActivity(intent, options);
    }
}
