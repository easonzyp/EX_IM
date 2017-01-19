package com.develop.ex_im_z.android.activity.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.develop.ex_im_z.MainApplication;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by ningtn on 2016/8/31 0031.
 */
public abstract class BaseFragment extends Fragment {
    protected MainApplication app;// app
    protected LayoutInflater inflater;

    protected ListView listView;
    // 用于缓存fragment的view，防止oncreateView的时候重复加载
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MainApplication) getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        if (view == null) {
            view = inflater.inflate(getLayoutId(), null);

            try {
                initIntentData();
            } catch (Exception e) {
//                finishByErr("init intentData excetion");
                e.printStackTrace();
            }
            initView();
            initListener();
            initData();
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;

        }
        return view;
    }

    /**
     * 初始化view
     */
    public abstract void initView();

    /**
     * 初始化intentdata
     */
    public abstract void initIntentData();

    /**
     * 初始化监听
     */
    public abstract void initListener();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 根据id查找组件
     */
    protected View findViewById(int id) {
        return view.findViewById(id);
    }

    public abstract int getLayoutId();


    public View getDefaultView() {
        return null;
    }
    public PullToRefreshListView getPullRefreshView() {
        return null;
    }
    public PullToRefreshGridView getPullRefreshGridView() {
        return null;
    }
}
