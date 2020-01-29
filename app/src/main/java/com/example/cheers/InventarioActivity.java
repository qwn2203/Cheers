package com.example.cheers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class InventarioActivity extends AppCompatActivity {
    private Button guardar;
    DBHandler handler;
    private List<Ingredients> ing = new ArrayList<>();
    private List<Drink> drinks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        handler = new DBHandler(this,null,null,1);
        handler.addIngredient("Ingredient1",1);
        handler.addDrink("Drink1","Description 1");
        ing.addAll(handler.findIngredients());
        for(int i=0; i < ing.size(); i++)
            System.out.println(ing.get(i).getId() + " -> Ingredient: " + ing.get(i).getName());
        guardar = findViewById(R.id.guardar);
        drinks.addAll(handler.findDrinks());
        for(int i=0; i < drinks.size(); i++)
            System.out.println(drinks.get(i).getId() + " -> Drink: " + drinks.get(i).getName());

        handler.addIngredientAndDrink(1,1,3);
        handler.findDrinksWithIngredients(4);
    }



    public void guardar(View view){
        Intent i = new Intent(InventarioActivity.this,homeActivity.class);
        startActivity(i);
    }
}
