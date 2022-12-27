package com.example.myapplication.beans;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;
import java.util.List;

public class MealBean implements Serializable {
    private Integer id;
    private String name;
    private Integer uId;
    private String cookName;
    private Integer type;
    private Integer cuisineType;
    private Integer status;
    private String price;
    private String description;
    private List<Ingredient> ingredients;



    public static ContentValues getValues(MealBean bean){
        ContentValues cv = new ContentValues();
        cv.put("name", bean.name);
        cv.put("uId", bean.uId);
        cv.put("cook_name", bean.cookName);
        cv.put("type", bean.type);
        cv.put("cuisine_type", bean.cuisineType);
        cv.put("price", bean.price);
        cv.put("status", bean.getStatus());
        cv.put("description", bean.getStatus());

        return cv;
    }

    public static MealBean getMealBean(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex("id"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        int uId = cursor.getInt(cursor.getColumnIndex("uId"));
        String cookName = cursor.getString(cursor.getColumnIndex("cook_name"));
        int type = cursor.getInt(cursor.getColumnIndex("type"));
        int cuisineType = cursor.getInt(cursor.getColumnIndex("cuisine_type"));
        String price = cursor.getString(cursor.getColumnIndex("price"));
        String description = cursor.getString(cursor.getColumnIndex("description"));


        MealBean  mealBean = new MealBean();
        mealBean.setId(id);
        mealBean.setuId(uId);
        mealBean.setCookName(cookName);
        mealBean.setCuisineType(cuisineType);
        mealBean.setName(name);
        mealBean.setType(type);
        mealBean.setPrice(price);
        mealBean.setDescription(description);
        return mealBean;
    }





    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(Integer cuisineType) {
        this.cuisineType = cuisineType;
    }



    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getCookName() {
        return cookName;
    }

    public void setCookName(String cookName) {
        this.cookName = cookName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
