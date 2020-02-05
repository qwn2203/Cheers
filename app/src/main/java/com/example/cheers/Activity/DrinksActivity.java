package com.example.cheers.Activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheers.Adapters.DrinkAdapter;
import com.example.cheers.DBHandler;
import com.example.cheers.LoadFavorites;
import com.example.cheers.Objetos.DrinkIngredient;
import com.example.cheers.R;

import java.util.ArrayList;

public class DrinksActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private DrinkAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<DrinkIngredient> drinks;
    private DBHandler handler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);
        setTitle("Drinks");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        handler = new DBHandler(this,null,null,0);
        recycler = findViewById(R.id.drinksRecyclerView);
        drinks = new ArrayList<>();

        System.out.println(LoadFavorites.favorites);
        fillData();
        printData();

        buildRecyclerViewDrinks();
    }

    private void fillData(){
        drinks.addAll(handler.findDrinks());
        System.out.println("TAMAÑO DEL ARREGLO: " +drinks.size());
        for(int i = 0; i < drinks.size(); i++){
            int id = drinks.get(i).getId();
            drinks.get(i).setIngredient(handler.findDrinksWithIngredients(id));
            drinks.get(i).setPercentage(handler.findPercentages(id));
            for(int j = 0; j < drinks.get(i).getIngredient().size(); j++){
                System.out.println("Ingredient percentage: " + drinks.get(i).getPercentage().get(j));
            }
        }

        if(drinks.isEmpty()){
            Toast.makeText(this, "There are no drinks created yet!", Toast.LENGTH_SHORT).show();
        }
    }

    private void printData(){
        System.out.println("TAMAÑO DE ARREGLO : " + drinks.size());
        for(int i = 0; i < drinks.size(); i++){
            int id = drinks.get(i).getId();
            String name = drinks.get(i).getNameDrink();
            String desc = drinks.get(i).getDescriptionDrink();
            System.out.printf("%d: %s, %s ing:\n", id,name,desc);

        }
    }

    public void buildRecyclerViewDrinks(){
        drinks.clear();

        fillData();

        adapter = new DrinkAdapter(drinks,this);
        manager = new LinearLayoutManager(this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);
    }

}
