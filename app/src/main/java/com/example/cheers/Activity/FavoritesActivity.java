package com.example.cheers.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.cheers.AlcoholAdapter;
import com.example.cheers.Objetos.Drink;
import com.example.cheers.R;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {
    RecyclerView recycler;
    AlcoholAdapter adapter;
    ArrayList<Drink> alcohols;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        setTitle("Favoritos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recycler = findViewById(R.id.favoritesRecyclerView);
        alcohols = new ArrayList<>();
        fillData();
        adapter = new AlcoholAdapter(alcohols,getApplication(),R.drawable.favorite);
        recycler.setAdapter(adapter);
        recycler.addItemDecoration(new DividerItemDecoration(recycler.getContext(), DividerItemDecoration.VERTICAL));

        recycler.setLayoutManager(new LinearLayoutManager(FavoritesActivity.this));
    }

    private void fillData(){
        alcohols.add(new Drink(1, "Bebida 1", "Instrucciones 1", null));
        alcohols.add(new Drink(2, "Bebida 2", "Instrucciones 2", null));
        alcohols.add(new Drink(3, "Bebida 3", "Instrucciones 3", null));
    }
}
