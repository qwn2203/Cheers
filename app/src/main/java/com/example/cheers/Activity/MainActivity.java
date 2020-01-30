package com.example.cheers.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cheers.DBHandler;
import com.example.cheers.Objetos.DrinkIngredient;
import com.example.cheers.Objetos.Ingredients;
import com.example.cheers.R;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    public Button btnScanner;
    public static List<DrinkIngredient> drinks;
    public static List<Ingredients> ingredients;
    public static DBHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScanner = findViewById(R.id.btnScanner);

        drinks = new ArrayList<>();
        ingredients = new ArrayList<>();
        handler = new DBHandler(this,null,null,1);
        loadData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //if(result != null){
            Intent intent = new Intent(MainActivity.this, homeActivity.class);
            startActivity(intent);
            finish();
        //}
    }

    public void lector(View v){
        new IntentIntegrator(MainActivity.this).initiateScan();
    }

    public static void loadData(){
        //reset all the data
        ingredients.clear();

        //fill data in ArrayList
        ingredients.addAll(handler.getIngredients());
    }
}
