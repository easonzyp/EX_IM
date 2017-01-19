package com.develop.ex_im_z.android.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.develop.ex_im_z.R;
import com.develop.ex_im_z.android.activity.base.BaseFragment;
import com.develop.ex_im_z.android.activity.chat.ActivityChatRoom;
import com.develop.ex_im_z.android.activity.friend.ActivityAddFriend;
import com.develop.ex_im_z.android.activity.home.ActivityHome;
import com.develop.ex_im_z.android.adapter.AdapterFriendList;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import imsdk.data.relations.IMMyselfRelations;

/**
 * Created by Administrator on 2016/10/21.
 */
public class FragmentFriendList extends BaseFragment {
    private RelativeLayout rl_add_friend;
    private ListView lv_friend_list;
    private AdapterFriendList adapter;
    ArrayList<String> customUserIDsList;
    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        rl_add_friend = (RelativeLayout) findViewById(R.id.rl_add_friend);
        lv_friend_list = (ListView) findViewById(R.id.lv_friend_list);
    }

    @Override
    public void initIntentData() {

    }
    @Subscribe
    public void onEventMainThread(ActivityHome.FirstEvent event) {
        if(event.getMsg().equals("refresh")){
            customUserIDsList = IMMyselfRelations.getFriendsList();
            adapter = new AdapterFriendList(getActivity(),0,customUserIDsList);
            lv_friend_list.setAdapter(adapter);
        }
    }
    @Override
    public void initListener() {
        rl_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ActivityAddFriend.class));
            }
        });
        lv_friend_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String customUserID = (String) IMMyselfRelations.getFriendsList().get(position);
                Intent intent = new Intent(getActivity(),ActivityChatRoom.class);
                intent.putExtra("user_name",customUserID);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        customUserIDsList = IMMyselfRelations.getFriendsList();
        adapter = new AdapterFriendList(getActivity(),0,customUserIDsList);
        lv_friend_list.setAdapter(adapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_friend_list;
    }
}
