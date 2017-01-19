package com.develop.ex_im_z.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/10/22.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "ex_im_db"; //数据库名称
    private static final int version = 1; //数据库版本
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ChatHistoryHelper.CREATE_TB_MESSAGE_LIST);  //历史聊天记录
        db.execSQL(UserInfoHelper.CREATE_TB_USER_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
//    public void insertSendMsg(String from_name,String to_name,String msg,String time){
//        String sql = "insert into user(from_name,to_name,msg,time) values ('"+from_name+"','"+to_name+"','"+msg+"','"+time+"')";//插入操作的SQL语句
//        dbWrit.execSQL(sql);//执行SQL语句
//    }
//    public void selectSendMsg(){
//
//    }
}
