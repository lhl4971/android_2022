package com.example.myapplication.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.beans.ComplainBean;
import com.example.myapplication.beans.Ingredient;
import com.example.myapplication.beans.MealBean;
import com.example.myapplication.beans.UserBean;

import java.util.ArrayList;
import java.util.List;

public class IngredientDataManager {

    private static IngredientDataManager userDataManager;
    private SQLiteDatabase db;


    private IngredientDataManager(Context context) {
        db = SQLiteHelper.getInstance(context).getWritableDatabase();
    }
    public static IngredientDataManager getInstance(Context context) {
        if (userDataManager == null) {
            userDataManager = new IngredientDataManager(context);
        }
        return userDataManager;
    }

    public void  insertIngredient(Ingredient bean) {
        if (bean != null) {
            db.insert("ingredient", null, Ingredient.getValues(bean));
        }
    }
    public void  saveIngredient(Ingredient bean) {
        if (bean != null) {
           db.update("ingredient", Ingredient.getValues(bean),"id"+ "= ? ", new String[]{bean.getId()+""}) ;
        }
    }


    public void deleteIngredientById(int ingredientById){
        db.delete("ingredient", "id" + "=?" , new String[]{ingredientById+""});
    }
    public Ingredient getIngredientById(int ingredientById) {
        Ingredient ingredient = null;
        String section = "id"+"="+ "'" +ingredientById + "'";
        Cursor cursor = db .query("ingredient", null, section, null, null, null, null);
        if(cursor!=null){
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                ingredient = Ingredient.getIngredient(cursor);
            }
            cursor.close();
        }
        return ingredient;
    }
    public  List<Ingredient> getIngredientByMealId(int mealId) {
        List<Ingredient> ingredients = new ArrayList<>();
        String section = "mId"+"="+ "'" +mealId + "'";
        Cursor cursor = db .query("ingredient", null, section, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Ingredient ingredient = Ingredient.getIngredient(cursor);
                ingredients.add(ingredient);
            }
            cursor.close();
        }
        return ingredients;
    }





}
