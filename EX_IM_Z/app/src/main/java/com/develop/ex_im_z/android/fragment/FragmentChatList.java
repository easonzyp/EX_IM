package com.develop.ex_im_z.android.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.develop.ex_im_z.R;
import com.develop.ex_im_z.android.activity.base.BaseFragment;
import com.develop.ex_im_z.android.activity.chat.ActivityChatRoom;
import com.develop.ex_im_z.android.adapter.AdapterChatList;

import java.util.ArrayList;
import java.util.List;

import imsdk.data.IMMyself;
import imsdk.data.IMSDK;
import imsdk.data.localchatmessagehistory.IMChatMessage;
import imsdk.data.localchatmessagehistory.IMMyselfLocalChatMessageHistory;
import imsdk.data.recentcontacts.IMMyselfRecentContacts;

/**
 * Created by Administrator on 2016/10/21.
 */
public class FragmentChatList extends BaseFragment {
    private ListView lv_chat_list;
    private ArrayList<String> list;
    private AdapterChatList adapterChatList;
    private Activity mActivity;
    @Override
    public void initView() {
        lv_chat_list = (ListView) findViewById(R.id.lv_chat_list);
        list = IMMyselfRecentContacts.getUsersList();
        adapterChatList = new AdapterChatList(getActivity(),0,list);
        lv_chat_list.setAdapter(adapterChatList);
    }

    @Override
    public void initIntentData() {
//        for (int i)
//        IMMyselfLocalChatMessageHistory.getChatMessage("lyl",i)
    }

    @Override
    public void initListener() {
        // 监听最近联系人列表变化
        IMMyselfRecentContacts.setOnDataChangedListener(new IMSDK.OnDataChangedListener() {
            @Override
            public void onDataChanged() {
                // 重新获取最近联系人列表
                list = IMMyselfRecentContacts.getUsersList();

                // 刷新ListView
                lv_chat_list.setAdapter(new AdapterChatList(mActivity,0, list));
            }
        });
        lv_chat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String user_name = IMMyselfRecentContacts.getUser(position);
                Intent intent = new Intent(getActivity(), ActivityChatRoom.class);
                intent.putExtra("user_name",user_name);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mActivity=activity;
    }
    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_chat_list;
    }
}
