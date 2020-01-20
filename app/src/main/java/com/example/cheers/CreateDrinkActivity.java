package com.example.cheers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class CreateDrinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_drink);
    }

    public void prepararBebida(View v) {
        Toast.makeText(this, "Preparando bebida personalizada", Toast.LENGTH_LONG).show();
        Intent i = new Intent(CreateDrinkActivity.this, homeActivity.class);
        startActivity(i);
    }
}
