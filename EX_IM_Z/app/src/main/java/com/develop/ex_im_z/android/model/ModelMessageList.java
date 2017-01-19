package com.develop.ex_im_z.android.model;

import android.content.ContentValues;

import imsdk.data.IMMyself;

/**
 * Created by Administrator on 2016/10/23.
 */
public class ModelMessageList {
    private String from_name;
    private String to_name;
    private String msg;
    private long time;
    private int msg_type;
    public ModelMessageList(){}
    public ModelMessageList(String from_name,String to_name,String msg,long time,int msg_type){
        this.from_name = from_name;
        this.to_name = to_name;
        this.msg = msg;
        this.time = time;
        this.msg_type = msg_type;
    }
    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public String getTo_name() {
        return to_name;
    }

    public void setTo_name(String to_name) {
        this.to_name = to_name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(int msg_type) {
        this.msg_type = msg_type;
    }
    public ContentValues toContentValues() {

        ContentValues map = new ContentValues();
        map.put("from_name", IMMyself.getCustomUserID());
        map.put("to_name", getTo_name());
        map.put("msg", getMsg());
        map.put("time", getTime());
        map.put("msg_type", getMsg_type());
        return map;
    }

}
