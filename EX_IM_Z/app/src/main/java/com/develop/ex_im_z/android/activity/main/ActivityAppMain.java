package com.develop.ex_im_z.android.activity.main;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.Toast;

import com.develop.ex_im_z.R;
import com.develop.ex_im_z.android.activity.base.BaseActivity;
import com.develop.ex_im_z.android.activity.home.ActivityHome;
import com.develop.ex_im_z.android.activity.login.ActivityLogin;
import com.develop.ex_im_z.android.model.ModelUser;
import com.develop.ex_im_z.db.UserInfoHelper;

import java.util.List;

import imsdk.data.IMMyself;

public class ActivityAppMain extends BaseActivity {
    UserInfoHelper db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreateNoTitle(savedInstanceState);
        db = new UserInfoHelper(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<ModelUser> listInfo = db.getUserLoginInfo();
                if (listInfo!=null&&listInfo.size()>0) {
                    if (listInfo.get(0).getIsLogin() == 1) {
                        //登陆的状态
                        IMMyself.setCustomUserID(listInfo.get(0).getUserName());
                        IMMyself.setPassword(listInfo.get(0).getPassword());
                        IMMyself.login(false, 5, new IMMyself.OnActionListener() {
                            @Override
                            public void onSuccess() {
                                startActivity(new Intent(ActivityAppMain.this, ActivityHome.class));
                                finish();
                            }

                            @Override
                            public void onFailure(String errorMsg) {
                                showToast(errorMsg);
                            }
                        });
                    } else {
                        Intent intent = new Intent(ActivityAppMain.this,
                                ActivityLogin.class);
                        startActivity(intent);
                        finish();
                    }
                }else {
                    Intent intent = new Intent(ActivityAppMain.this,
                            ActivityLogin.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1500);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public String getTitleCenter() {
        return null;
    }

    public void showToast(String msg){
        Toast.makeText(ActivityAppMain.this,msg,Toast.LENGTH_SHORT).show();
    }
}
