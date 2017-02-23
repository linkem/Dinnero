package com.example.mat.dinnerov4;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mat on 14.02.2017.
 */

public class MyDatabase extends SQLiteAssetHelper {

    private static final String TAG = "MyDatabase";
    //** TABLES***//
    private static final String TABLE_INGREDIENTS = "ingredients";
    private static final String TABLE_DISHES = "dish";
    private static final String TABLE_DISH_INGR = "dish_ingr";

    //** COLUMNS**//
    private static final String KEY_DISH_ID = "id";
    private static final String KEY_DISH_NAME = "name";
    private static final String KEY_DISH_DESCRIPTION = "description";

    private static final String KEY_ING_ID = "id";
    private static final String KEY_ING_NAME ="name";
    private static final String KEY_ING_DESCRIPTION = "description";

    private static final String KEY_DISH_INGR_DISH_ID = "dish_id";
    private static final String KEY_DISH_INGR_ING_ID = "ingr_id";

    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 1;

    private static MyDatabase mInstance = null;

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade(2);
    }
    public static MyDatabase getInstance(Context context){
        if (mInstance == null) {
            mInstance = new MyDatabase(context.getApplicationContext());
        }
        return mInstance;
    }

    public List<Dish> getDishesFromIngerdientsIDs(List<Integer> selectedIngredientsList, int numberOfMatches){
        ArrayList<Dish> dishesList = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+TABLE_DISHES+" WHERE "+KEY_DISH_ID+" IN("+
                "SELECT "+ KEY_DISH_INGR_DISH_ID +" FROM "+ TABLE_DISH_INGR +" WHERE "+ KEY_DISH_INGR_ING_ID +" IN(";
        /*
        SELECT * FROM DISH
        WHERE ID IN(
        select dish_ingr.dish_id from dish_ingr where dish_ingr.ingr_id IN(2,4,8)
        group by dish_ingr.dish_id
        having count(*) = 3)
        */
        for(int i = 0;i< selectedIngredientsList.size();i++){
            int ingredientID = selectedIngredientsList.get(i);
            Log.d(TAG, "getDishesFromIngredientsIDs: "+selectedIngredientsList.get(i));
            if((i+1) ==selectedIngredientsList.size()){
                selectQuery+= ingredientID+")";
            }
            else {
                selectQuery+= ingredientID+",";
            }
        }
        selectQuery+=" GROUP BY "+ KEY_DISH_INGR_DISH_ID +" HAVING COUNT(*)="+numberOfMatches+")";

        Log.d(TAG, "getDishesFromIngredients: "+ selectQuery);
        Cursor cursor = null;
        SQLiteDatabase db = null;

        try{
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Dish dish = new Dish();
                    dish.setId(cursor.getInt(0));
                    dish.setName(cursor.getString(1));
                    dish.setDescription(cursor.getString(2));

                    byte[] image = cursor.getBlob(4);
                    dish.setImage(BitmapFactory.decodeByteArray(image, 0, image.length));

                    dishesList.add(dish);
                } while (cursor.moveToNext());
            }

        }catch (Exception e){
            Log.d(TAG, "getDishesFromIngredients: "+e.getMessage());
        }
        finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            if (db != null)
                db.close();
        }
        return dishesList;
    }
    public ArrayList<Dish> getDishesFromIngredients(ArrayList<Ingredient> selectedIngredientsList, int numberOfMatches){
        ArrayList<Dish> dishesList = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+TABLE_DISHES+" WHERE "+KEY_DISH_ID+" IN("+
                "SELECT "+ KEY_DISH_INGR_DISH_ID +" FROM "+ TABLE_DISH_INGR +" WHERE "+ KEY_DISH_INGR_ING_ID +" IN(";

        for(int i = 0;i< selectedIngredientsList.size();i++){
            Ingredient ingredient = selectedIngredientsList.get(i);
            Log.d(TAG, "getDishesFromIngredients: "+ingredient.getName());
            if((i+1) ==selectedIngredientsList.size()){
                selectQuery+= ingredient.getId()+")";
            }
            else {
                selectQuery+= ingredient.getId()+",";
            }
        }
        selectQuery+=" GROUP BY "+ KEY_DISH_INGR_DISH_ID +" HAVING COUNT(*)="+numberOfMatches+")";

        Log.d(TAG, "getDishesFromIngredients: "+ selectQuery);
        Cursor cursor = null;
        SQLiteDatabase db = null;
        
        try{
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Dish dish = new Dish();
                    dish.setId(cursor.getInt(0));
                    dish.setName(cursor.getString(1));
                    dishesList.add(dish);
                } while (cursor.moveToNext());
            }
            
        }catch (Exception e){
            Log.d(TAG, "getDishesFromIngredients: "+e.getMessage());
        }
        finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            if (db != null)
                db.close();
        }
        return dishesList;
    }
    public Ingredient getIngredient(int id){
        // zwrata skladnik o podanym id
        return null;
    }
    public List<Ingredient> getAllIngredients(){
        //zwraca wszystkie skladniki
        // SELECT * FROM TABLE_INGREDIENTS
        List<Ingredient> ingredientList = new ArrayList<Ingredient>();
        String selectQuery = "SELECT  * FROM " + TABLE_INGREDIENTS;
        Cursor cursor = null;
        SQLiteDatabase db = null;
        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setId(cursor.getInt(0));
                    ingredient.setName(cursor.getString(1));
                    ingredient.setGlutenFree(cursor.getInt(4));
                    ingredient.setVegetarian(cursor.getInt(5));
                    ingredientList.add(ingredient);
                } while (cursor.moveToNext());
            }
        }catch (Exception e){
            Log.d(TAG, "getAllIngredients: EXCEPTON: "+e.getMessage());
        }
        finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            if (db != null)
                db.close();
        }
        Log.d(TAG, "ilosc ingedientow: "+ ingredientList.size());

        return ingredientList;
    }
    public int getIngredientsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_INGREDIENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();

        // return count
        return cursor.getCount();
    }
    public List<Dish> getDishes(/*String args[]*/){
        //int numberOfIngredients = args.length;
        //select * from dish where id in (3,5)
        List<Dish> dishList = new ArrayList<Dish>();
        String selectQuery = "SELECT  * FROM " + TABLE_DISHES+" WHERE "+ KEY_DISH_ID+" IN"+" (3,5)";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Dish dish = new Dish();
                dish.setId(cursor.getInt(0));
                dish.setName(cursor.getString(1));
                dishList.add(dish);
            } while (cursor.moveToNext());
        }
        return dishList;
    }
    public List<Dish> getAllDishes(){
        List<Dish> dishList = new ArrayList<Dish>();
        String selectQuery = "SELECT  * FROM " + TABLE_DISHES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Dish dish = new Dish();
                dish.setId(cursor.getInt(0));
                dish.setName(cursor.getString(1));
                dishList.add(dish);
            } while (cursor.moveToNext());
        }
        return dishList;
    }

}
