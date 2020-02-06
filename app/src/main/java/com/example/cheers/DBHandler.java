package com.example.cheers;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import androidx.annotation.Nullable;


import com.example.cheers.Objetos.Drink;
import com.example.cheers.Objetos.DrinkIngredient;
import com.example.cheers.Objetos.Ingredients;

import java.util.ArrayList;

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
        CREATE_TABLE = "CREATE TABLE drinks_has_ingredients (id_drink INTEGER, id_ingredient INTEGER, amount INTEGER,FOREIGN KEY (id_drink) REFERENCES drinks(id), FOREIGN KEY (id_ingredient) REFERENCES ingredients(id) ON DELETE CASCADE) ;";
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
        values.put("name",titleCaseConversion(name.trim().toLowerCase()));
        values.put("type",type);
        db.insert("ingredients",null,values);
        db.close();
    }

    public void addDrink(String name, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",titleCaseConversion(name.toLowerCase().trim()));
        values.put("description",description.trim());
        db.insert("drinks",null,values);
        db.close();
    }

    public ArrayList<Ingredients> getIngredients(){
        ArrayList<Ingredients> ingredients = new ArrayList<>();
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
        db.close();
        return ingredients;
    }

    public ArrayList<DrinkIngredient> findDrinks(){
        ArrayList<DrinkIngredient> drinks = new ArrayList<>();
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
        db.close();
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

    public ArrayList<Ingredients> findDrinksWithIngredients(int idDrink){
        ArrayList<Ingredients> ingredients = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ingredients.id Id, ingredients.name Name, ingredients.type Type from drinks, drinks_has_ingredients, ingredients WHERE drinks.id = id_drink AND ingredients.id = id_ingredient and drinks.id = " + idDrink + ";",null );
        if(cursor.moveToFirst()){
            while(cursor.isAfterLast() == false) {
                int id_ingredient = cursor.getInt(cursor.getColumnIndex("Id"));
                String nameIngredient = cursor.getString(cursor.getColumnIndex("Name"));
                int typeIngredient = cursor.getInt(cursor.getColumnIndex("Type"));
                System.out.println(id_ingredient + ": " + nameIngredient + " -> " + typeIngredient);
                ingredients.add(new Ingredients(id_ingredient, nameIngredient, typeIngredient));
                cursor.moveToNext();
            }
        }
        System.out.println("Tamaño de arreglo de ingredientes --> " + ingredients.size());
        db.close();
        return ingredients;
    }

    public int findIdDrinkByName(String name, String description){
        int id = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM drinks WHERE name LIKE '%" + name + "%' AND description LIKE '%" + description + "%' LIMIT 1",null);
        if(cursor.moveToFirst()){
            id = cursor.getInt(cursor.getColumnIndex("id"));
        }
        db.close();
        return id;
    }

    private static String titleCaseConversion(String input){
        StringBuilder titleCase = new StringBuilder(input.length());
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }

    public int addDrinkReturnId(String name, String description){
        String n = titleCaseConversion(name.toLowerCase().trim());
        String d = description.trim();
        this.addDrink(n,d);
        return this.findIdDrinkByName(n,d);
    }

    public ArrayList<Integer> findPercentages(int id){
        ArrayList<Integer> percentages = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ingredients.id Id, ingredients.name Name, ingredients.type Type, amount from drinks, drinks_has_ingredients, ingredients WHERE drinks.id = id_drink AND ingredients.id = id_ingredient and drinks.id = " + id + ";",null );
        if(cursor.moveToFirst()){
            while(cursor.isAfterLast() == false) {
                int percentage = cursor.getInt(cursor.getColumnIndex("amount"));
                percentages.add(percentage);
                cursor.moveToNext();
            }
        }
        System.out.println("Tamaño de arreglo de ingredientes --> " + percentages.size());
        db.close();
        return percentages;
    }

    public Drink findDrinkById(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM drinks WHERE id = " + id + ";",null );
        if(cursor.moveToFirst()){
            int idDrink = cursor.getInt(cursor.getColumnIndex("id"));
            String nameDrink = cursor.getString(cursor.getColumnIndex("name"));
            String amountDrink = cursor.getString(cursor.getColumnIndex("description"));
            System.out.println(idDrink + ": " + nameDrink + " --> " + amountDrink);
            return new Drink(idDrink,nameDrink,amountDrink, null);
        }
        db.close();
        return null;
    }

    public boolean deleteDrink(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("drinks",  "id =" + id, null) > 0;
    }

    public int findAmountById(int idDrink, int idIngredient){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM drinks_has_ingredients WHERE id_drink = " + idDrink + " AND id_ingredient = " + idIngredient + "; ",null);
        if(cursor.moveToFirst()){
            int percentage = cursor.getInt(cursor.getColumnIndex("amount"));
            //System.out.println("("+idDrink + " --> " + idIngredient + "): " + percentage);
            return percentage;
        }
        return -1;
    }

    public Ingredients findIngredientById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ingredients WHERE id = " + id + ";",null );
        if(cursor.moveToFirst()){
            int idIngredient = cursor.getInt(cursor.getColumnIndex("id"));
            String nameIngredient = cursor.getString(cursor.getColumnIndex("name"));
            int typeIngredient = cursor.getInt(cursor.getColumnIndex("type"));
            return new Ingredients(idIngredient,nameIngredient,typeIngredient);
        }
        db.close();
        return null;
    }
}
