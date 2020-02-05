package com.example.cheers.Activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheers.DBHandler;
import com.example.cheers.Adapters.IngredientAdapter;
import com.example.cheers.Objetos.Ingredients;
import com.example.cheers.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CreateDrinkActivity extends AppCompatActivity {

    private IngredientAdapter alcoholAdapter,mixerAdapter;

    private EditText nameDrink, descriptionDrink;
    private RecyclerView alcoholRecyclerView, mixerRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    static TextView totalPercentage;
    static ImageView cupImage;
    public static Context context;

    private DBHandler handler;
    private ArrayList<Ingredients> alcohol, mixers, ing;

    public static HashMap<Integer, Integer> percentageIng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_drink);
        setTitle("Create new Drink");

        context = this;

        alcohol = new ArrayList<>();
        mixers = new ArrayList<>();
        ing = new ArrayList<>(MainActivity.ingredients);
        percentageIng = new HashMap<>();

        handler = new DBHandler(this,null,null,1);

        nameDrink = findViewById(R.id.nameDrink);
        descriptionDrink = findViewById(R.id.descriptionDrink);
        alcoholRecyclerView = findViewById(R.id.alcoholRecycler);
        mixerRecyclerView = findViewById(R.id.mixerRecyclerView);
        totalPercentage = findViewById(R.id.totalPercentage);
        cupImage = findViewById(R.id.cupImage);

        MainActivity.loadData();
        loadAlcohol();
        loadMixers();


        alcoholAdapter = new IngredientAdapter(alcohol,this);
        layoutManager = new LinearLayoutManager(this);
        alcoholRecyclerView.setAdapter(alcoholAdapter);
        alcoholRecyclerView.setLayoutManager(layoutManager);

        mixerAdapter = new IngredientAdapter(mixers,this);
        layoutManager = new LinearLayoutManager(this);
        mixerRecyclerView.setAdapter(mixerAdapter);
        mixerRecyclerView.setLayoutManager(layoutManager);


    }

    public void loadAlcohol(){
        for(int i=0; i < ing.size(); i++){
            if(ing.get(i).getType() == 0)
                alcohol.add(ing.get(i));
        }
    }

    public void loadMixers(){
        for(int i=0; i < ing.size(); i++){
            if(ing.get(i).getType() == 1)
                mixers.add(ing.get(i));
        }
    }

    public void loadDrinks(View v){
        int totalSum = 0;
        String n = nameDrink.getText().toString().trim();
        String d = descriptionDrink.getText().toString().trim();
        //Makes the sum through and Iterator of all percentages of the ingredients
        Iterator<Map.Entry<Integer, Integer>> it = percentageIng.entrySet().iterator();

        //Get sum of all ingredients
        totalSum = getSum();

        //If name is empty
        if(n.isEmpty()){
            nameDrink.setError("Please, insert the name of the drink");
            nameDrink.requestFocus();
            return;

            //If description is empty
        } else if(d.isEmpty()){
            descriptionDrink.setError("Please, insert the description of the drink");
            descriptionDrink.requestFocus();
            return;

            //If the ingredients exceed the %100 of the CUP
        } else if( totalSum > 100){
            Toast.makeText(this, "Exceed the amout of percentage in cup", Toast.LENGTH_SHORT).show();
            return;
        } else {
            int id = handler.addDrinkReturnId(n,d);
            it = percentageIng.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry<Integer, Integer> pair = it.next();
                handler.addIngredientAndDrink(id,pair.getKey(),pair.getValue());
            }
            Toast.makeText(this, "Total Sum --> " + totalSum, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Drink Created" + totalSum, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this,homeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);

        }

    }

    public static int getSum(){
        int total = 0;
        Iterator<Map.Entry<Integer, Integer>> it = percentageIng.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<Integer, Integer> pair = it.next();
            total += pair.getValue();
        }
        return total;
    }

    public static void updatePercentage(){
        if(getSum() > 100)
            totalPercentage.setTextColor(context.getResources().getColor(R.color.colorPrimary2));
        else
            totalPercentage.setTextColor(context.getResources().getColor(R.color.black));
        totalPercentage.setText(getSum() + "%");
        int sum = (getSum()/10);
        int resource;
        switch (sum) {
            case 0: resource = R.drawable.cup0; break;
            case 1: resource = R.drawable.cup10; break;
            case 2: resource = R.drawable.cup20; break;
            case 3: resource = R.drawable.cup30; break;
            case 4: resource = R.drawable.cup40; break;
            case 5: resource = R.drawable.cup50; break;
            case 6: resource = R.drawable.cup60; break;
            case 7: resource = R.drawable.cup70; break;
            case 8: resource = R.drawable.cup80; break;
            case 9: resource = R.drawable.cup90; break;
            default: resource = R.drawable.cup100; break;
        }

        cupImage.setImageResource(resource);
    }

}
