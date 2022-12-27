package com.example.myapplication.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.beans.UserBean;

import java.util.ArrayList;
import java.util.List;

public class UserDataManager {

    private static UserDataManager userDataManager;
    private SQLiteDatabase db;


    private UserDataManager(Context context) {
        db = SQLiteHelper.getInstance(context).getWritableDatabase();
    }
    public static UserDataManager getInstance(Context context) {
        if (userDataManager == null) {
            userDataManager = new UserDataManager(context);
        }
        return userDataManager;
    }

    public void  insertUser(UserBean bean) {
        if (bean != null) {
            db.insert("user", null, UserBean.getValues(bean));
        }
    }

    public UserBean getUserInfoByAccount(String userEmail) {
        UserBean user = null;
        String section = "email"+"="+ "'" +userEmail + "'";
        Cursor cursor = db .query("user", null, section, null, null, null, null);
        if(cursor!=null){
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                user = UserBean.getUser(cursor);
            }
            cursor.close();
        }
        return user;
    }
    public UserBean getUserInfoById(int userId) {
        UserBean user = null;
        String section = "id"+"="+ "'" +userId + "'";
        Cursor cursor = db .query("user", null, section, null, null, null, null);
        if(cursor!=null){
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                user = UserBean.getUser(cursor);
            }
            cursor.close();
        }
        return user;
    }

    public List<UserBean> getAllUser(int role) {
        List<UserBean> users = new ArrayList<>();
        String section = "role"+"="+ "'" + role + "'";
        Cursor cursor = db .query("user", null, section, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                UserBean user = UserBean.getUser(cursor);
                users.add(user);
            }
            cursor.close();
        }
        return users;
    }
    public void updateCookerStatus(UserBean userBean) {
        if (userBean != null) {
            ContentValues cv = new ContentValues();
            cv.put("status", userBean.getStatus());
            db.update("user", cv,"id"+ "= ? ", new String[]{userBean.getId()+""}) ;
        }
    }







}
