package com.develop.ex_im_z.android.activity.friend;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.develop.ex_im_z.R;
import com.develop.ex_im_z.android.activity.base.BaseActivity;

import imsdk.data.IMMyself;
import imsdk.data.relations.IMMyselfRelations;

public class ActivityAddFriend extends BaseActivity {
    private EditText et_add_username;
    private EditText et_add_msg;
    private Button btn_send;
    private String add_username;
    private String add_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreateNoTitle(savedInstanceState);
        initView();
        initListener();
    }
    public void initView(){
        et_add_username = (EditText) findViewById(R.id.et_add_username);
        et_add_msg = (EditText) findViewById(R.id.et_add_msg);
        btn_send = (Button) findViewById(R.id.btn_send);

    }
    public void initListener(){
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_username = et_add_username.getText().toString();
                add_msg = et_add_msg.getText().toString();
                IMMyselfRelations.sendFriendRequest(add_msg, add_username, 10, new IMMyself.OnActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(ActivityAddFriend.this,"添加好友请求发送成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(String error) {
                        // 发送失败
                        Toast.makeText(ActivityAddFriend.this,"发送失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_friend;
    }

    @Override
    public String getTitleCenter() {
        return null;
    }
}
