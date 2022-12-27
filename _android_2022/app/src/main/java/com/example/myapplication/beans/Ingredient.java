package com.example.myapplication.beans;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ingredient implements Serializable{
    Integer id;
    Integer mId;
    String name;

    public static ContentValues getValues(Ingredient bean){
        ContentValues cv = new ContentValues();
        cv.put("mId", bean.mId);
        cv.put("name", bean.name);
        return cv;
    }
    public static Ingredient getIngredient(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex("id"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        int mId = cursor.getInt(cursor.getColumnIndex("mId"));
        Ingredient  ingredient = new Ingredient();
        ingredient.setId(id);
        ingredient.setmId(mId);
        ingredient.setName(name);

        return ingredient;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
