package com.example.cheers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {
    RecyclerView recycler;
    AlcoholAdapter adapter;
    ArrayList<AlcoholData> alcohols;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        setTitle("Favoritos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recycler = (RecyclerView)findViewById(R.id.recycler);
        alcohols = new ArrayList<>();
        fillData();
        adapter = new AlcoholAdapter(alcohols,getApplication());
        recycler.setAdapter(adapter);
        recycler.addItemDecoration(new DividerItemDecoration(recycler.getContext(), DividerItemDecoration.VERTICAL));

        recycler.setLayoutManager(new LinearLayoutManager(FavoritesActivity.this));
    }

    private void fillData(){
        alcohols.add(new AlcoholData("Bebida 1"));
        alcohols.add(new AlcoholData("Bebida 2"));
        alcohols.add(new AlcoholData("Bebida 3"));
    }
}
