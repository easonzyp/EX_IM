package com.develop.ex_im_z.android.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.develop.ex_im_z.R;
import com.develop.ex_im_z.android.activity.base.BaseActivity;

import imsdk.data.IMMyself;

public class ActivityRegister extends BaseActivity {
    private EditText et_username;  //用户名
    private EditText et_password;  //密码
    private EditText et_password_agin;  //再次输入密码
    private Button btn_register;  //注册按钮

    private String user_name;
    private String password;
    private String password_agin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreateNoTitle(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    public void initView(){
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password_agin = (EditText) findViewById(R.id.et_password_agin);
        btn_register = (Button) findViewById(R.id.btn_register);
    }
    public void initData(){

    }
    public void initListener(){
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name = et_username.getText().toString();
                password = et_password.getText().toString();
                password_agin = et_password_agin.getText().toString();
                if(user_name==""||user_name==null||"".equals(user_name)){
                    showToast("用户名不能为空");
                    return ;
                }
                if(password==""||password==null||"".equals(password)){
                    showToast("密码不能为空");
                    return ;
                }
                if(password_agin==""||password_agin==null||"".equals(password_agin)){
                    showToast("密码不能为空");
                    return ;
                }
                if(!password_agin.equals(password)){
                    showToast("两次密码不一致");
                    return ;
                }
                IMMyself.setCustomUserID(user_name);
                IMMyself.setPassword(password);
                // 设置超时时长为5秒
                IMMyself.register(5, new IMMyself.OnActionListener() {
                    @Override
                    public void onSuccess() {
                        showToast("注册成功");
                        Intent intent = new Intent();
                        intent.putExtra("username", user_name);
                        intent.putExtra("password", password);
                        setResult(RESULT_OK,intent);
                        finish();
                    }

                    @Override
                    public void onFailure(String error) {
                        if (error.equals("Timeout")) {
                            error = "注册超时";
                        }
                        showToast(error);
                    }
                });
            }
        });
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public String getTitleCenter() {
        return null;
    }

    public void showToast(String msg){
        Toast.makeText(ActivityRegister.this,msg,Toast.LENGTH_SHORT).show();
    }
}
