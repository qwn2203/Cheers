package com.example.cheers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InventarioActivity extends AppCompatActivity {
    Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        guardar = findViewById(R.id.guardar);

    }

    public void guardar(View view){
        Intent i = new Intent(InventarioActivity.this,homeActivity.class);
        startActivity(i);
    }
}
