package com.example.cheers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class homeActivity extends AppCompatActivity {
    CardView crearBebida, favoritos, bebidas, inventario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Enlazar objetos con UI
        favoritos = findViewById(R.id.favoritos);
        bebidas = findViewById(R.id.bebidas);
        crearBebida = findViewById(R.id.crear);
        inventario = findViewById(R.id.inventario);
        //onClickListeners para mandar a diferentes pantallas
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homeActivity.this, FavoritesActivity.class);
                startActivity(i);
            }
        });
        bebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homeActivity.this,DrinksActivity.class);
                startActivity(i);
            }
        });
        crearBebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homeActivity.this, CreateDrinkActivity.class);
                startActivity(i);
            }
        });

        inventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homeActivity.this,InventarioActivity.class);
                startActivity(i);
            }
        });

    }
}