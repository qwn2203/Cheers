package com.example.cheers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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

    public static class AlcoholViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;

        public AlcoholViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.heart);
        }
    }
}
