package com.develop.ex_im_z.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.develop.ex_im_z.android.model.ModelMessageList;

import java.util.ArrayList;
import java.util.List;

import imsdk.data.IMMyself;

/**
 * Created by Administrator on 2016/10/23.
 */
public class ChatHistoryHelper {
    DatabaseHelper chatHistoryHelper;
    public static final String TB_MESSAGE_HISTORY_LIST = "tb_message_history_list";// 历史聊天记录列表
    //create table send_message(id INTEGER PRIMARY KEY AUTOINCREMENT,from_name TEXT , to_name TEXT ,msg TEXT,time TEXT)
    public static final String CREATE_TB_MESSAGE_LIST = "CREATE TABLE if not exists " + TB_MESSAGE_HISTORY_LIST
            + "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "from_name TEXT, " +
            "to_name TEXT," +
            "msg TEXT," +
            "time LONG, " +
            "msg_type INTEGER)";

    public ChatHistoryHelper(Context context) {
        this.chatHistoryHelper = new DatabaseHelper(context);
    }

    public void addMessageHistoryList(ModelMessageList messageList) {

        ContentValues map = messageList.toContentValues();
        //      int result = chatHistoryHelper.getWritableDatabase().update(TB_MESSAGE_HISTORY_LIST, map, null,
        //              null);
        //     if (result <= 0) {
        chatHistoryHelper.getWritableDatabase().insert(TB_MESSAGE_HISTORY_LIST, null, map);
        //      }
        //     return result;
    }

    public List<ModelMessageList> getHistoryMessageList(String to_name,int count) {

        List<ModelMessageList> queyList = new ArrayList<ModelMessageList>();
        Cursor c = chatHistoryHelper.getWritableDatabase().rawQuery("select * from " + TB_MESSAGE_HISTORY_LIST + " where to_name=? and from_name =?  order by time desc  LIMIT  "+count+" ", new String[]{to_name,IMMyself.getCustomUserID()});
        if (c.moveToFirst()) {

            do{
                ModelMessageList modelMessageList = new ModelMessageList();
                modelMessageList.setFrom_name(c.getString(c.getColumnIndex("from_name")));
                modelMessageList.setTo_name(c.getString(c.getColumnIndex("to_name")));
                modelMessageList.setMsg(c.getString(c.getColumnIndex("msg")));
                modelMessageList.setTime(c.getLong(c.getColumnIndex("time")));
                modelMessageList.setMsg_type(c.getInt(c.getColumnIndex("msg_type")));
                queyList.add(modelMessageList);
            }while (c.moveToNext());
        }
        c.close();
        return queyList;
    }
}
