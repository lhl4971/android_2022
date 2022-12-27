package com.example.myapplication.beans;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

public class OrderBean implements Serializable{
    Integer id;
    String  name;
    Integer uId;//client uid
    Integer cId;//cook uid
    String  clientName;
    String  cookName;
    Integer mId;
    Integer status; //0 order ,1 approvel, 2 reject
    Integer cuisineType;
    String collectTime;
    String price;


    public static ContentValues getValues(OrderBean bean){
        ContentValues cv = new ContentValues();
        cv.put("name", bean.name);
        cv.put("uId", bean.uId);
        cv.put("cId", bean.cId);
        cv.put("client_name", bean.clientName);
        cv.put("cook_name", bean.cookName);
        cv.put("mId", bean.mId);
        cv.put("status", bean.status);
        cv.put("cuisine_type", bean.cuisineType);
        cv.put("collect_time", bean.collectTime);
        cv.put("price", bean.price);
        return cv;
    }
    public static OrderBean getOrderBean(Cursor cursor){

        int id = cursor.getInt(cursor.getColumnIndex("id"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        int uId = cursor.getInt(cursor.getColumnIndex("uId"));
        int cId = cursor.getInt(cursor.getColumnIndex("cId"));
        String clientName = cursor.getString(cursor.getColumnIndex("client_name"));
        String cookName = cursor.getString(cursor.getColumnIndex("cook_name"));
        int mId = cursor.getInt(cursor.getColumnIndex("mId"));
        int status = cursor.getInt(cursor.getColumnIndex("status"));
        int cuisineType = cursor.getInt(cursor.getColumnIndex("cuisine_type"));
        String collectTime = cursor.getString(cursor.getColumnIndex("collect_time"));
        String price = cursor.getString(cursor.getColumnIndex("price"));

       OrderBean orderBean = new OrderBean();
       orderBean.setId(id);
       orderBean.setName(name);
       orderBean.setmId(mId);
       orderBean.setuId(uId);
       orderBean.setcId(cId);
       orderBean.setClientName(clientName);
       orderBean.setCookName(cookName);
       orderBean.setStatus(status);
       orderBean.setCuisineType(cuisineType);
       orderBean.setCollectTime(collectTime);
       orderBean.setPrice(price);

        return orderBean;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(Integer cuisineType) {
        this.cuisineType = cuisineType;
    }

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public String getPrice() {
        return price;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCookName() {
        return cookName;
    }

    public void setCookName(String cookName) {
        this.cookName = cookName;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
