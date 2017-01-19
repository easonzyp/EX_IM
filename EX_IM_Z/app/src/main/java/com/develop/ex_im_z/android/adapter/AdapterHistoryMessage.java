package com.develop.ex_im_z.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.develop.ex_im_z.R;
import com.develop.ex_im_z.android.config.BaseConfig;
import com.develop.ex_im_z.android.model.ModelMessageList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import imsdk.data.localchatmessagehistory.IMChatMessage;

/**
 * Created by Administrator on 2016/10/23.
 */
public class AdapterHistoryMessage extends ArrayAdapter {
    private Context context;
    private List<IMChatMessage> list;
    private String [] user_name;
    public AdapterHistoryMessage(Context context, int resource, String[] user_name) {
        super(context, resource, user_name);
        this.context = context;
        this.user_name = user_name;
    }
    public AdapterHistoryMessage(Context context, int resource, List<IMChatMessage> list) {
        super(context, resource, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (holder == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_history_message_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        IMChatMessage modelList = getItem(position);
        SimpleDateFormat sdfShow = new SimpleDateFormat("HH:mm:ss");
        Date curDate = new Date(modelList.getClientSendTime());//获取时间
        String strDataShow = sdfShow.format(curDate);

        if(modelList.isReceivedMessage()){
            holder.rl_right.setVisibility(View.GONE);
            //holder.tv_avatar_left
            holder.tv_time_left.setText(strDataShow);
            holder.tv_content_left.setText(modelList.getText().toString());
        }else{
            holder.rl_left.setVisibility(View.GONE);
            //holder.tv_avatar_left
            holder.tv_time_right.setText(strDataShow);
            holder.tv_content_right.setText(modelList.getText().toString());
        }
        return convertView;
    }

    public class ViewHolder {
        private RelativeLayout rl_left;
        private RelativeLayout rl_right;
        private TextView tv_avatar_left;
        private TextView tv_avatar_right;
        private TextView tv_time_left;
        private TextView tv_time_right;
        private TextView tv_content_left;
        private TextView tv_content_right;
        public ViewHolder(View view) {
            rl_left = (RelativeLayout) view.findViewById(R.id.rl_left);
            rl_right = (RelativeLayout) view.findViewById(R.id.rl_right);

            tv_avatar_left = (TextView) view.findViewById(R.id.tv_avatar_left);
            tv_avatar_right = (TextView) view.findViewById(R.id.tv_avatar_right);

            tv_time_left = (TextView) view.findViewById(R.id.tv_time_left);
            tv_time_right = (TextView) view.findViewById(R.id.tv_time_right);
            tv_content_left = (TextView) view.findViewById(R.id.tv_content_left);
            tv_content_right = (TextView) view.findViewById(R.id.tv_content_right);


        }
    }

    @Override
    public IMChatMessage getItem(int position) {
        return (IMChatMessage) super.getItem(position);
    }
}
