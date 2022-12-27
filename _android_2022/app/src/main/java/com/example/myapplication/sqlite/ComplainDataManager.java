package com.example.myapplication.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.beans.ComplainBean;

import java.util.ArrayList;
import java.util.List;

public class ComplainDataManager {
    private static ComplainDataManager complainDataManager;
    private SQLiteDatabase db;


    private ComplainDataManager(Context context) {
        db = SQLiteHelper.getInstance(context).getWritableDatabase();
    }
    public static ComplainDataManager getInstance(Context context) {
        if (complainDataManager == null) {
            complainDataManager = new ComplainDataManager(context);
        }
        return complainDataManager;
    }



    public void  insertComplainBean(ComplainBean bean) {
        if (bean != null) {
            db.insert("complain", null, ComplainBean.getValues(bean));
        }
    }
    public  List<ComplainBean>  getAllComplain() {
        List<ComplainBean> complainBeans = new ArrayList<>();
        String section = "status"+"="+ "'" + 0 + "'";
        Cursor cursor = db .query("complain", null, section, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ComplainBean complainBean = ComplainBean.getComplainBean(cursor);
                complainBeans.add(complainBean);
            }
            cursor.close();
        }
        return complainBeans;
    }
    public  void deleteComplain(ComplainBean bean) {
        db.delete("complain", "id" + "=?" , new String[]{bean.getId()+""});
    }

}
