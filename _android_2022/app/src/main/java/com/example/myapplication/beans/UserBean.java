package com.example.myapplication.beans;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

public class UserBean implements Serializable {

    private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private String cheque;
    private String email;
    private String password;
    private String  descri;
    private Integer role; //0 admin 1 client 2 cook
    private Integer status; //0 disable 1 normal

    public UserBean(String firstName, String lastName, String address, String cheque, String email,
                    String password, String descri, Integer role, Integer status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.cheque = cheque;
        this.email = email;
        this.password = password;
        this.descri = descri;
        this.role = role;
        this.status = status;
    }

    public static ContentValues getValues(UserBean bean){
        ContentValues cv = new ContentValues();
        cv.put("first_name", bean.firstName);
        cv.put("last_name", bean.lastName);
        cv.put("address", bean.address);
        cv.put("cheque", bean.cheque);
        cv.put("email", bean.email);
        cv.put("password", bean.password);
        cv.put("descri", bean.descri);
        cv.put("address", bean.address);
        cv.put("role", bean.role);
        cv.put("status", bean.status);
        return cv;
    }

    public static UserBean getUser(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex("id"));
        String firstName = cursor.getString(cursor.getColumnIndex("first_name"));
        String lastName = cursor.getString(cursor.getColumnIndex("last_name"));
        String address = cursor.getString(cursor.getColumnIndex("address"));
        String cheque = cursor.getString(cursor.getColumnIndex("cheque"));
        String email = cursor.getString(cursor.getColumnIndex("email"));
        String password = cursor.getString(cursor.getColumnIndex("password"));
        String descri = cursor.getString(cursor.getColumnIndex("descri"));
        int role = cursor.getInt(cursor.getColumnIndex("role"));
        int status = cursor.getInt(cursor.getColumnIndex("status"));
        UserBean  user = new UserBean(firstName,lastName,address,cheque,email,password,descri,role,status);
        user.setId(id);
        return user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCheque() {
        return cheque;
    }

    public void setCheque(String cheque) {
        this.cheque = cheque;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
