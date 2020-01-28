package com.example.cheers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class DrinksActivity extends AppCompatActivity {
    RecyclerView recycler;
    AlcoholAdapter adapter;
    ArrayList<Drink> alcohols;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);
        setTitle("Favoritos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recycler = findViewById(R.id.drinksRecyclerView);
        alcohols = new ArrayList<>();
        fillData();
        adapter = new AlcoholAdapter(alcohols,getApplication(),R.drawable.favorite_empty);
        recycler.setAdapter(adapter);
        recycler.addItemDecoration(new DividerItemDecoration(recycler.getContext(), DividerItemDecoration.VERTICAL));

        recycler.setLayoutManager(new LinearLayoutManager(DrinksActivity.this));
    }

    private void fillData(){
        alcohols.add(new Drink(1, "Bebida 1", "Instrucciones 1", null));
        alcohols.add(new Drink(2, "Bebida 2", "Instrucciones 2", null));
        alcohols.add(new Drink(3, "Bebida 3", "Instrucciones 3", null));

    }
}
