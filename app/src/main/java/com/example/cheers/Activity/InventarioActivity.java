package com.example.cheers.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cheers.NewIngredientDialog;
import com.example.cheers.Objetos.Ingredients;
import com.example.cheers.R;
import com.example.cheers.StockAdapter;

import java.util.ArrayList;

public class InventarioActivity extends AppCompatActivity implements NewIngredientDialog.NewIngredientDialogListener {
    private ArrayList<String> alcohol;
    private ArrayList<String> mixers;

    private Spinner spinnerAlcohol1, spinnerAlcohol2,spinnerAlcohol3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);
        setTitle("Stock");

        alcohol = new ArrayList<>();
        mixers = new ArrayList<>();

        spinnerAlcohol1 = findViewById(R.id.spinnerAlcohol1);
        spinnerAlcohol2 = findViewById(R.id.spinnerAlcohol2);
        spinnerAlcohol3 = findViewById(R.id.spinnerAlcohol3);

        buildSpinners();

    }

    public void getAlcohol(){
        for(int i = 0; i < MainActivity.ingredients.size(); i++)
            if(MainActivity.ingredients.get(i).getType() == 0)
                alcohol.add(MainActivity.ingredients.get(i).getName());
    }

    public void getMixers(){
        for(int i=0; i < MainActivity.ingredients.size(); i++)
            if(MainActivity.ingredients.get(i).getType() == 1)
                mixers.add(MainActivity.ingredients.get(i).getName());
    }

    public void addNewDrink(View v){
        NewIngredientDialog newIngredientDialog = new NewIngredientDialog();
        newIngredientDialog.show(getSupportFragmentManager(),"New Drink");
    }

    public void buildSpinners(){

        MainActivity.loadData();

        //Separate ingredients from alcohol and mixers
        getAlcohol();
        getMixers();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_item, alcohol);
        spinnerAlcohol1.setAdapter(adapter);
        spinnerAlcohol2.setAdapter(adapter);
        spinnerAlcohol3.setAdapter(adapter);


    }


    @Override
    public void applyTexts(String name, String type) {

        if(name.length() < 2)
            Toast.makeText(this, "Invalid Entry. Please, insert another name", Toast.LENGTH_SHORT).show();
        else if(type.contains("drink"))
            Toast.makeText(this, "Invalid Entry. Please, select the type of drink", Toast.LENGTH_SHORT).show();
        else{
            MainActivity.handler.addIngredient(name,(type.equals("Alcohol")?0:1));
            mixers.clear();
            alcohol.clear();
            buildSpinners();

        }

    }
}
