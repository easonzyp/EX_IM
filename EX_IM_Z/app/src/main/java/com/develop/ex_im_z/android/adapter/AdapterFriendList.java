package com.develop.ex_im_z.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.develop.ex_im_z.R;

import java.util.List;

/**
 * Created by Administrator on 2016/10/23.
 */
public class AdapterFriendList extends ArrayAdapter{
    private Context context;
    private List<String> list;
    public AdapterFriendList(Context context, int resource, List<String> list) {
        super(context, resource, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (holder == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_friend_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //IMChatMessage chatMsg = IMMyselfLocalChatMessageHistory.getChatMessage(getItem(position), 0);
        holder.tv_user_name.setText(getItem(position));
        //holder.tv_friend_info.setText(chatMsg.getText().toString());

        return convertView;
    }

    public class ViewHolder {
        private TextView tv_user_name;
        private TextView tv_friend_info;
        public ViewHolder(View view) {
            tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
            tv_friend_info = (TextView) view.findViewById(R.id.tv_friend_info);

        }
    }

    @Override
    public String getItem(int position) {
        return (String) super.getItem(position);
    }
}
