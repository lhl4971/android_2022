package com.example.myapplication.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.beans.OrderBean;

import java.util.ArrayList;
import java.util.List;

public class OrderDataManager {

    private static OrderDataManager userDataManager;
    private SQLiteDatabase db;


    private OrderDataManager(Context context) {
        db = SQLiteHelper.getInstance(context).getWritableDatabase();
    }
    public static OrderDataManager getInstance(Context context) {
        if (userDataManager == null) {
            userDataManager = new OrderDataManager(context);
        }
        return userDataManager;
    }

    public void  insertOrder(OrderBean bean) {
        if (bean != null) {
            db.insert("client_order", null, OrderBean.getValues(bean));
        }
    }
    public void  updateOrder(OrderBean bean) {
        if (bean != null) {
            db.update("client_order", OrderBean.getValues(bean),"id"+ "= ? ", new String[]{bean.getId()+""}) ;
        }
    }

    public List<OrderBean> getCookOrder(int userId) {
        List<OrderBean> orderBeans = new ArrayList<>();
//        String section = "cId"+"="+ "'" + userId + "'";
        String section = null;
        Cursor cursor = db .query("client_order", null, section, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                OrderBean orderBean = OrderBean.getOrderBean(cursor);
                orderBeans.add(orderBean);
            }
            cursor.close();
        }
        return orderBeans;
    }






}
