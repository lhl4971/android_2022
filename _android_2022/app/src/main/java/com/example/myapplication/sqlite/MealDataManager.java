package com.example.myapplication.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.beans.MealBean;
import com.example.myapplication.beans.OrderBean;

import java.util.ArrayList;
import java.util.List;

public class MealDataManager {

    private static MealDataManager userDataManager;
    private SQLiteDatabase db;


    private MealDataManager(Context context) {
        db = SQLiteHelper.getInstance(context).getWritableDatabase();
    }
    public static MealDataManager getInstance(Context context) {
        if (userDataManager == null) {
            userDataManager = new MealDataManager(context);
        }
        return userDataManager;
    }

    public void  updateMeal(MealBean bean) {
        if (bean != null) {
            Log.i("TAG", "updateMeal1: " + bean.getId());
             long updateId = db.update("meal", MealBean.getValues(bean),"id"+ "= ? ", new String[]{bean.getId()+""}) ;
            Log.i("TAG", "updateMeal: " + updateId);
        }
    }
    public long insertDraftMeal() {
        ContentValues cv = new ContentValues();
        cv.put("status", 0);
        long mealId = db.insert("meal", null, cv);
        Log.i("TAG", "insertDraftMeal: " + mealId);
        return mealId;
    }

    public List<MealBean> getMealInfos(int userId) {
        List<MealBean> mealBeans = new ArrayList<>();
        String section = "status"+"="+ "'1'";
        if (userId != 0){
            section = "uId"+"="+ "'" +userId + "'" +  "and  "  + "status"+"="+ "'1'";
        }
        Cursor cursor = db .query("meal", null, section, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                MealBean mealBean = MealBean.getMealBean(cursor);
                mealBeans.add(mealBean);
            }
            cursor.close();
        }
        return mealBeans;
    }

    public List<MealBean> getMealList(String name,int type,int cuisineType) {
        List<MealBean> mealBeans = new ArrayList<>();
//        String  section = "name"+" like "+ "'%" +name + "%'"+  "and  "  + "type"+"="+ "'" +type+ "'" +"and  "  + "cuisine_type"+"="+ "'" +cuisineType+ "'" +  "and  "  + "status"+"="+ "'1'";
        Cursor cursor = db .query("meal", null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                MealBean mealBean = MealBean.getMealBean(cursor);
                mealBeans.add(mealBean);
            }
            cursor.close();
        }
        return mealBeans;
    }

    public MealBean getMealInfosById(int id) {
        MealBean mealBean = null;
        String section = "status"+"="+ "'1'";
        if (id != 0){
            section = "id"+"="+ "'" +id + "'" +  "and  "  + "status"+"="+ "'1'";
        }
        Cursor cursor = db .query("meal", null, section, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                mealBean = MealBean.getMealBean(cursor);
            }
            cursor.close();
        }
        return mealBean;
    }

}
