package com.example.cheers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class homeActivity extends AppCompatActivity {
    CardView crearBebida, favoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Enlazar objetos con UI
        crearBebida = findViewById(R.id.crear);
        favoritos = findViewById(R.id.favoritos);

        //onClickListeners para mandar a diferentes pantallas
        crearBebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homeActivity.this, CreateDrinkActivity.class);
                startActivity(i);
            }
        });
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homeActivity.this, FavoritesActivity.class);
                startActivity(i);
            }
        });

    }
}
