package com.example.myapplication.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "hotel.db";
    private static final int DATABASE_VERSION = 2;

    private static SQLiteHelper SQLiteHelper;

    public static SQLiteHelper getInstance(Context context) {
        if (SQLiteHelper == null) {
            SQLiteHelper = new SQLiteHelper(context);
        }
        return SQLiteHelper;
    }
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String user = "create table user ("
                + "id integer primary key autoincrement, "
                + "first_name VARCHAR, "
                + "last_name VARCHAR, "
                + "address VARCHAR, "
                + "cheque VARCHAR, "
                + "role integer, "
                + "status integer, "
                + "email VARCHAR, "
                + "descri VARCHAR, "
                + "password VARCHAR)";




        String meal = "create table meal ("
                + "id integer primary key autoincrement, "
                + "name VARCHAR, "
                + "uId integer, " //cook id
                + "cook_name VARCHAR, " //cook name
                + "type integer, " // 0 main dish, 1 soup, 2 desert,
                + "cuisine_type  integer, " //0 Italian, 1 Chinese, 2 Greek
                + "status  integer, " //0 draft, 1 formal
                + "description  VARCHAR, "
                + "price VARCHAR)";




        String ingredient = "create table ingredient ("
                + "id integer primary key autoincrement, "
                + "mId integer, "
                + "name VARCHAR)";


        String order = "create table client_order ("
                + "id integer primary key autoincrement, "
                + "name  VARCHAR, "
                + "uId integer, "
                + "cId integer, "
                + "client_name VARCHAR, "
                + "cook_name VARCHAR, "
                + "mId integer, " //meal id
                + "status integer, " // 0 order ,1 approvel, 2 reject
                + "cuisine_type  VARCHAR, " //0 Italian, 1 Chinese, 2 Greek
                + "collect_time  VARCHAR, "
                + "price VARCHAR)";


        String complain = "create  table complain ("
                + "id integer primary key autoincrement, "
                + "fromId integer, "
                + "toId integer, "
                + "from_name VARCHAR, "
                + "to_name VARCHAR, "
                + "oId integer, " //order id
                + "status integer, " //0 normal 1 delete
                + "grade  integer, "
                + "comment VARCHAR)";


        db.execSQL(user);
        db.execSQL(meal);
        db.execSQL(ingredient);
        db.execSQL(order);
        db.execSQL(complain);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
