package com.develop.ex_im_z.android.model;

/**
 * Created by zyp on 2016/12/21.
 */
public class ModelUser {
    private String userName;
    private String password;
    private String desc;
    private int isLogin;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(int isLogin) {
        this.isLogin = isLogin;
    }
}
