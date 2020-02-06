package com.example.cheers.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.cheers.Adapters.DrinkInformationAdapter;
import com.example.cheers.DBHandler;
import com.example.cheers.Objetos.DrinkIngredient;
import com.example.cheers.Objetos.Ingredients;
import com.example.cheers.R;

import java.util.ArrayList;


public class DrinkInformationActivity extends AppCompatActivity {
    RecyclerView mixerRecyclerView, alcoholRecyclerView;
    DrinkInformationAdapter adapterAlcohol, adapterMixer;

    Button delete;
    ImageView cup;
    TextView name, instructions, percentage;

    DrinkIngredient drink;

    ArrayList<Ingredients> mixer, alcohol;
    ArrayList<Integer> mixerAmount, alcoholAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setNull();
        setContentView(R.layout.activity_drink_information);
        mixerRecyclerView = findViewById(R.id.mixerRecycler);
        alcoholRecyclerView = findViewById(R.id.alcoholRecycler);
        delete = findViewById(R.id.deleteBtn);
        name = findViewById(R.id.nameLabel);
        percentage = findViewById(R.id.percentTextView);
        instructions = findViewById(R.id.instructionsLabel);
        cup = findViewById(R.id.cupImage);

        drink = (DrinkIngredient) getIntent().getSerializableExtra("drink");

        mixer = new ArrayList<>();
        alcohol = new ArrayList<>();
        mixerAmount = new ArrayList<>();
        alcoholAmount = new ArrayList<>();

        loadAlcohol(drink.getIngredient(),drink.getPercentage(),0);
        loadMixer(drink.getIngredient(),drink.getPercentage(),1);

        name.setText(drink.getNameDrink());
        instructions.setText(drink.getDescriptionDrink());

        loadCup();
        buildRecyclerViews();
    }

    public void setNull(){
        mixerRecyclerView = null;
        alcoholRecyclerView = null;
        adapterAlcohol = null;
        adapterMixer = null;
        delete = null;
        cup = null;
        name = null;
        instructions = null;
        percentage = null;
        drink = null;
        mixer = null;
        alcohol = null;
        mixerAmount = null;
        alcoholAmount = null;
    }

    public void loadCup(){
        int total = 0;
        for(int i = 0; i < alcoholAmount.size(); i++)
            total += alcoholAmount.get(i);
        for(int i = 0; i < mixerAmount.size(); i++)
            total += mixerAmount.get(i);

        percentage.setText(total + "%");
        total = ((total/10)*10);
        System.out.println("TOTAL: " + total);
        switch (total){
            case 0: cup.setImageResource(R.drawable.cup0); break;
            case 10: cup.setImageResource(R.drawable.cup10); break;
            case 20: cup.setImageResource(R.drawable.cup20); break;
            case 30: cup.setImageResource(R.drawable.cup30); break;
            case 40: cup.setImageResource(R.drawable.cup40); break;
            case 50: cup.setImageResource(R.drawable.cup50); break;
            case 60: cup.setImageResource(R.drawable.cup60); break;
            case 70: cup.setImageResource(R.drawable.cup70); break;
            case 80: cup.setImageResource(R.drawable.cup80); break;
            case 90: cup.setImageResource(R.drawable.cup90); break;
            default: cup.setImageResource(R.drawable.cup100); break;
        }
    }

    public void loadMixer(ArrayList<Ingredients> ing, ArrayList<Integer> amount, int type){
        for(int i=0; i < ing.size(); i++){
            if(ing.get(i).getType() == type) {
                mixer.add(ing.get(i));
                mixerAmount.add(amount.get(i));
                System.out.println("Mixer: " + ing.get(i).getName() + ": " + amount.get(i));
            }
        }
    }

    public void loadAlcohol(ArrayList<Ingredients> ing, ArrayList<Integer> amount, int type){
        for(int i=0; i < ing.size(); i++){
            if(ing.get(i).getType() == type) {
                alcohol.add(ing.get(i));
                alcoholAmount.add(amount.get(i));
                System.out.println("Alcohol: " + ing.get(i).getName() + ": " + amount.get(i));
            }
        }
    }

    public void buildRecyclerViews(){
        adapterAlcohol = new DrinkInformationAdapter(alcohol,alcoholAmount,this);
        alcoholRecyclerView.setAdapter(adapterAlcohol);
        alcoholRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapterMixer = new DrinkInformationAdapter(mixer,mixerAmount,this);
        mixerRecyclerView.setAdapter(adapterMixer);
        mixerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void delete(View v){
        DBHandler handler = new DBHandler(this,null,null,0);
        boolean val = handler.deleteDrink(drink.getId());
        if(val){
            Toast.makeText(this, "Drink ereased!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(DrinkInformationActivity.this,DrinksActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            Toast.makeText(this, "Drink cannot be ereased!", Toast.LENGTH_SHORT).show();
        }
    }
}
