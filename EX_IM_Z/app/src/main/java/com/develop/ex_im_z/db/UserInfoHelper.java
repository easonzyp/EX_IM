package com.develop.ex_im_z.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.develop.ex_im_z.android.model.ModelUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyp on 2016/12/21.
 */
public class UserInfoHelper {
    DatabaseHelper userInfoHelper;
    public static final String TB_USER_INFO = "tb_user_info";// 用户信息表
    public static final String CREATE_TB_USER_INFO = "CREATE TABLE if not exists " + TB_USER_INFO
            + "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "userName TEXT, " +
            "password TEXT, " +
            "isLogin INTEGER )";

    public UserInfoHelper(Context context) {
        this.userInfoHelper = new DatabaseHelper(context);
    }

    public void addUserLoginInfo(String userName,String password,int isLogin){
        SQLiteDatabase database = userInfoHelper.getWritableDatabase();
        String sql = "replace into "+this.TB_USER_INFO +"('userName','password','isLogin' ) values ( '" + userName +"','"+ password+ "'," +  isLogin  + ")" ;
        database.execSQL(sql);
    }

    public List<ModelUser> getUserLoginInfo(){
        List<ModelUser> queryList = new ArrayList<>();
        SQLiteDatabase database = userInfoHelper.getReadableDatabase();
        String sql = "select * from "+this.TB_USER_INFO ;
        Cursor c = database.rawQuery(sql,null);
        if (c.moveToFirst()) {

            do{
                ModelUser modelUser = new ModelUser();
                modelUser.setUserName(c.getString(c.getColumnIndex("userName")));
                modelUser.setPassword(c.getString(c.getColumnIndex("password")));
                modelUser.setIsLogin(c.getInt(c.getColumnIndex("isLogin")));
                queryList.add(modelUser);
            }while (c.moveToNext());
        }
        return queryList;
    }

}
