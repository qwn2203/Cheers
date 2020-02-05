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
import java.util.Enumeration;

public class FavoritesActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private DrinkAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DrinkIngredient> drinks;
    private DBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        setTitle("Favoritos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drinks = new ArrayList<>();
        handler = new DBHandler(this,null,null,0);

        recycler = findViewById(R.id.favoritesRecyclerView);

        fillData();
        buildRecyclerView();
    }

    public void fillData(){
        Enumeration<String> enums = (Enumeration<String>) LoadFavorites.favorites.propertyNames();
        while (enums.hasMoreElements()) {
            String key = enums.nextElement();
            String value = LoadFavorites.favorites.getProperty(key);
            drinks.add(new DrinkIngredient(handler.findDrinkById(Integer.parseInt(value))));
        }
        if(drinks.size() == 0){
            Toast.makeText(this, "There are no favorite drinks", Toast.LENGTH_SHORT).show();
        }
    }

    private void buildRecyclerView(){
        drinks.clear();

        fillData();

        adapter = new DrinkAdapter(drinks,this);
        layoutManager = new LinearLayoutManager(this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(layoutManager);
    }
}
