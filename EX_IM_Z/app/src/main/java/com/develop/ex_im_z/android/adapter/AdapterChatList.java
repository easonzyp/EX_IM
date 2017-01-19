package com.develop.ex_im_z.android.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.develop.ex_im_z.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import imsdk.data.localchatmessagehistory.IMChatMessage;
import imsdk.data.localchatmessagehistory.IMMyselfLocalChatMessageHistory;
import imsdk.data.recentcontacts.IMMyselfRecentContacts;

/**
 * Created by Administrator on 2016/10/22.
 */
public class AdapterChatList extends ArrayAdapter {
    private Context context;
    private List<String> list;
    public AdapterChatList(Context context, int resource, List<String> list) {
        super(context, resource, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (holder == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_chat_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        IMChatMessage chatMsg = IMMyselfLocalChatMessageHistory.getChatMessage(getItem(position), 0);
        long count = IMMyselfRecentContacts.getUnreadChatMessageCount(chatMsg.getFromCustomUserID());
        SimpleDateFormat sdfShow = new SimpleDateFormat("HH:mm:ss");
        Date curDate = new Date(chatMsg.getClientSendTime());//获取时间
        String strDataShow = sdfShow.format(curDate);
        holder.tv_user_name.setText(getItem(position));
        holder.tv_msg_last.setText(chatMsg.getText().toString());
        holder.tv_time.setText(strDataShow);
        if(count>0){
            holder.tv_message_count.setText(count+"");
            holder.tv_message_count.setVisibility(View.VISIBLE);
        }else {
            holder.tv_message_count.setVisibility(View.GONE);
        }


        return convertView;
    }

    public class ViewHolder {
        private TextView tv_user_name;
        private TextView tv_msg_last;
        private TextView tv_time;
        private TextView tv_message_count;
        public ViewHolder(View view) {
            tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
            tv_msg_last = (TextView) view.findViewById(R.id.tv_msg_last);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_message_count = (TextView) view.findViewById(R.id.tv_message_count);

        }
    }

    @Override
    public String getItem(int position) {
        return (String) super.getItem(position);
    }
}
