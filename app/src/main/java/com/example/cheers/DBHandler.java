package com.example.cheers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.cheers.Objetos.Drink;
import com.example.cheers.Objetos.DrinkIngredient;
import com.example.cheers.Objetos.Ingredients;

import java.util.ArrayList;
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

    public List<Ingredients> getIngredients(){
        List<Ingredients> ingredients = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from ingredients;",null );
        if(cursor.moveToFirst()){
            while(cursor.isAfterLast() == false) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int type = cursor.getInt(cursor.getColumnIndex("type"));

                ingredients.add(new Ingredients(id,name,type));
                cursor.moveToNext();
            }
        }
        return ingredients;
    }

    public List<DrinkIngredient> findDrinks(){
        List<DrinkIngredient> drinks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from drinks;",null );
        if(cursor.moveToFirst()){
            while(cursor.isAfterLast() == false) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String desc = cursor.getString(cursor.getColumnIndex("description"));
                Drink tmp = new Drink(id,name,desc,null);

                drinks.add(new DrinkIngredient(tmp));
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

    public List<Ingredients> findDrinksWithIngredients(int idDrink){
        List<Ingredients> ingredients = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ingredients.id Id, ingredients.name Name, ingredients.type Type from drinks, drinks_has_ingredients, ingredients WHERE drinks.id = id_drink AND ingredients.id = id_ingredient and drinks.id = " + idDrink + ";",null );
        if(cursor.moveToFirst()){
            while(cursor.isAfterLast() == false) {
                int id_ingredient = cursor.getInt(cursor.getColumnIndex("id_ingredient"));
                String nameIngredient = cursor.getString(cursor.getColumnIndex("ingredients.name"));
                int typeIngredient = cursor.getInt(cursor.getColumnIndex("ingredients.type"));
                ingredients.add(new Ingredients(id_ingredient, nameIngredient, typeIngredient));
                cursor.moveToNext();
            }
        }
        System.out.println("Tamaño de arreglo de ingredientes --> " + ingredients.size());
        return ingredients;
    }
}