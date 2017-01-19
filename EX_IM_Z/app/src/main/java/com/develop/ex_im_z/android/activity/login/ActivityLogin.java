package com.develop.ex_im_z.android.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.develop.ex_im_z.R;
import com.develop.ex_im_z.android.activity.base.BaseActivity;
import com.develop.ex_im_z.android.activity.home.ActivityHome;
import com.develop.ex_im_z.db.UserInfoHelper;

import imsdk.data.IMMyself;

public class ActivityLogin extends BaseActivity {
    UserInfoHelper db = null ;
    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private TextView tv_register;  //注册按钮
    private RelativeLayout iv_back;

    private String user_name;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreateNoTitle(savedInstanceState);
        db = new UserInfoHelper(this);
        initView();
        initListener();
        /*IMMyself.setOnAutoLoginListener(new IMMyself.OnAutoLoginListener() {

            @Override
            public void onAutoLoginBegan() {
                //开始自动登录
            }

            @Override
            public void onAutoLoginSuccess() {
                //自动登录成功
                startActivity(new Intent(ActivityLogin.this, ActivityHome.class));
            }

            @Override
            public void onAutoLoginFailure(boolean loginConflict) {
                //自动登录失败，loginConflict表示是否登录冲突

            }
        });*/
    }
    public void initView(){
        tv_register = (TextView) findViewById(R.id.tv_register);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        iv_back = (RelativeLayout) findViewById(R.id.iv_back);
    }
    public void initListener(){
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //登陆
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name = et_username.getText().toString();
                password = et_password.getText().toString();
                IMMyself.setCustomUserID(user_name);
                IMMyself.setPassword(password);
                IMMyself.login(false, 5, new IMMyself.OnActionListener() {
                    @Override
                    public void onSuccess() {
                        startActivity(new Intent(ActivityLogin.this, ActivityHome.class));
                        db.addUserLoginInfo(user_name,password,1);
                        finish();
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        showToast(errorMsg);
                    }
                });
            }
        });
        //跳转注册界面
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ActivityLogin.this,ActivityRegister.class),0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            user_name = data.getStringExtra("username");
            password = data.getStringExtra("password");
            et_username.setText(user_name);
            et_password.setText(password);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public String getTitleCenter() {
        return null;
    }
    public void showToast(String msg){
        Toast.makeText(ActivityLogin.this,msg,Toast.LENGTH_SHORT).show();
    }

}
