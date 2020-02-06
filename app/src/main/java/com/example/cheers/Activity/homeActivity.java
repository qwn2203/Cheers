package com.example.cheers.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.cardview.widget.CardView;

import com.example.cheers.R;

public class homeActivity extends Activity {
    CardView crearBebida, favoritos, bebidas, inventario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Enlazar objetos con UI
        crearBebida = findViewById(R.id.crear);
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
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        bebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homeActivity.this, DrinksActivity.class);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(i);
            }
        });
        crearBebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homeActivity.this, CreateDrinkActivity.class);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(i);
            }
        });
        inventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homeActivity.this, InventarioActivity.class);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(i);
            }
        });
    }
}
