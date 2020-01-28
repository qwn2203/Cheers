package com.example.cheers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    public static final String DB_NAME = "bebidas.db";
    public static final int DB_VERSION = 1;


    public DBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE ingredients (id INTEGER PRIMARY KEY, name TEXT, type INTEGER)";
        db.execSQL(CREATE_TABLE);
        CREATE_TABLE = "CREATE TABLE drinks (id INTEGER PRIMARY KEY, name TEXT, description TEXT);";
        db.execSQL(CREATE_TABLE);
        CREATE_TABLE = "CREATE TABLE drinks_has_ingredients (id_drink INTEGER, id_ingredient INTEGER, amount TEXT,FOREIGN KEY (id_drink) REFERENCES drinks(id), FOREIGN KEY (id_ingredient) REFERENCES ingredients(id));";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String DROP_TABLES = "DROP TABLE IF EXISTS ingredients;";
        db.execSQL(DROP_TABLES);
        DROP_TABLES = "DROP TABLE IF EXISTS drinks";
        db.execSQL(DROP_TABLES);
        DROP_TABLES = "DROP TABLE IF EXISTS drinks_has_ingredients";
        db.execSQL(DROP_TABLES);
        onCreate(db);
    }

    public void addIngredient(String name, int type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("type",type);
        db.insert("ingredients",null,values);
        db.close();
    }

    public void addDrink(String name, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("description",description);
        db.insert("drinks",null,values);
        db.close();
    }

    public List<Ingredients> findIngredients(){
        List<Ingredients> ingredients = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from ingredients;",null );
        if(cursor.moveToFirst()){
            while(cursor.isAfterLast() == false) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int type = cursor.getInt(cursor.getColumnIndex("id"));

                ingredients.add(new Ingredients(id,name,type));
                cursor.moveToNext();
            }
        }
        return ingredients;
    }

    public List<Drink> findDrinks(){
        List<Drink> drinks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from drinks;",null );
        if(cursor.moveToFirst()){
            while(cursor.isAfterLast() == false) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String desc = cursor.getString(cursor.getColumnIndex("description"));
                Drink tmp = new Drink(id,name,desc,null);

                drinks.add(tmp);
                cursor.moveToNext();
            }
        }
        return drinks;
    }

    public void addIngredientAndDrink(int idDrink, int idIngredient, int amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_drink",idDrink);
        values.put("id_ingredient",idIngredient);
        values.put("amount",amount);
        db.insert("drinks_has_ingredients",null,values);
        db.close();
    }

    public void findDrinksWithIngredients(int idDrink){
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id_drink, drinks.name Drink, id_ingredient ,ingredients.name Ingredient, ingredients.type Type, amount Amount FROM drinks_has_ingredients, ingredients, drinks WHERE id_drink = drinks.id and id_ingredient = ingredients.id AND id_drink = " + idDrink +";",null );
        if(cursor.moveToFirst()){
            while(cursor.isAfterLast() == false) {
                int id = cursor.getInt(cursor.getColumnIndex("id_drink"));
                String drink = cursor.getString(cursor.getColumnIndex("Drink"));
                int idIngredient = cursor.getInt(cursor.getColumnIndex("id_ingredient"));
                String ingredient = cursor.getString(cursor.getColumnIndex("Ingredient"));
                int amount = cursor.getInt(cursor.getColumnIndex("Amount"));
                System.out.printf("%d : (%d = %s | %d = %s) --> %d\n",count,id,drink,idIngredient,ingredient,amount);
                count++;
                cursor.moveToNext();
            }
        }
    }
}
