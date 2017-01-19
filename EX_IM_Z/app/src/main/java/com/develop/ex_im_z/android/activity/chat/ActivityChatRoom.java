package com.develop.ex_im_z.android.activity.chat;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.develop.ex_im_z.R;
import com.develop.ex_im_z.android.activity.base.BaseActivity;
import com.develop.ex_im_z.android.adapter.AdapterHistoryMessage;
import com.develop.ex_im_z.android.config.BaseConfig;
import com.develop.ex_im_z.android.model.ModelMessageList;
import com.develop.ex_im_z.db.ChatHistoryHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import imsdk.data.IMMyself;
import imsdk.data.localchatmessagehistory.IMChatMessage;
import imsdk.data.localchatmessagehistory.IMMyselfLocalChatMessageHistory;
import imsdk.data.recentcontacts.IMMyselfRecentContacts;

public class ActivityChatRoom extends BaseActivity {
    private EditText et_msg;
    private TextView tv_title_name;
    private Button btn_send_msg;
    private String user_name;
    List<IMChatMessage> dataList;
    private ListView lv_message_list;
    private AdapterHistoryMessage adapter;
    String msg =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreateNoTitle(savedInstanceState);
        Intent intent = getIntent();
        user_name = intent.getStringExtra("user_name");
        initView();
        initListener();
    }
    public void initView(){
        et_msg = (EditText) findViewById(R.id.et_msg);
        btn_send_msg = (Button) findViewById(R.id.btn_send_msg);
        tv_title_name = (TextView) findViewById(R.id.tv_title_name);
        lv_message_list = (ListView) findViewById(R.id.lv_message_list);
        tv_title_name.setText(user_name);
        setNotifyMsg(user_name,dataList,lv_message_list);

        long count = IMMyselfLocalChatMessageHistory.getChatMessageCount(user_name);
        for (int i=0;i<count;i++){
            IMChatMessage im = IMMyselfLocalChatMessageHistory.getChatMessage(user_name,i);
            Log.i("zyp","消息："+im.getText().toString()+",from："+im.getFromCustomUserID()+",是否是接收状态："+im.isReceivedMessage());
        }
    }

    public void initListener(){

        btn_send_msg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 动态配置超时时长
                msg = et_msg.getText().toString();
                IMMyself.sendText(msg, user_name, (msg.length() / 400 + 1) * 5, new IMMyself.OnActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(ActivityChatRoom.this, "发送成功", Toast.LENGTH_SHORT).show();
                        setNotifyMsg(user_name,dataList,lv_message_list);
                        et_msg.setText("");  //清空发送文本输入框内容
                    }

                    @Override
                    public void onFailure(String error) {
                        Toast.makeText(ActivityChatRoom.this, "发送失败 -- " + error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        // 设置监听器
        IMMyself.setOnReceiveTextListener(new IMMyself.OnReceiveTextListener() {
            // 监听来自其他用户的文本讯息
            @Override
            public void onReceiveText(String text, String fromCustomUserID, long serverActionTime) {
                setNotifyMsg(user_name,dataList,lv_message_list);
            }

            // 监听系统消息
            @Override
            public void onReceiveSystemText(String text, long serverActionTime) {
                Toast.makeText(ActivityChatRoom.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setNotifyMsg(String name,List<IMChatMessage> list,ListView listView){
        long count = IMMyselfLocalChatMessageHistory.getChatMessageCount(name);
        list = new ArrayList<>();
        for (int i=0;i<count;i++){
            IMChatMessage im = IMMyselfLocalChatMessageHistory.getChatMessage(name,i);
            list.add(im);
        }
        Collections.reverse(list);
        adapter = new AdapterHistoryMessage(ActivityChatRoom.this,0,list);
        listView.setAdapter(adapter);
        listView.setSelection(list.size());//将ListView定位到最后一行
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_room;
    }

    @Override
    public String getTitleCenter() {
        return null;
    }
}
