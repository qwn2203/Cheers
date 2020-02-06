package com.example.cheers.Activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cheers.NewIngredientDialog;
import com.example.cheers.Objetos.DrinkIngredient;
import com.example.cheers.Objetos.Ingredients;
import com.example.cheers.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

public class InventarioActivity extends AppCompatActivity implements NewIngredientDialog.NewIngredientDialogListener {
    private ArrayList<String> alcoholString;
    private ArrayList<String> mixersString;
    public static ArrayList<Ingredients> alcohol;
    public static ArrayList<Ingredients> mixers;



    private Spinner spinnerAlcohol1, spinnerAlcohol2,spinnerAlcohol3;
    private Spinner spinnerMixer1,spinnerMixer2,spinnerMixer3,spinnerMixer4,spinnerMixer5;

    Properties dispenser;
    private final String DISPENSER_FILENAME = "dispenser.xml";

    HashMap<String, Integer> disp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);
        setTitle("Stock");
        getSupportActionBar().setHomeButtonEnabled(true);

        alcoholString = new ArrayList<>();
        mixersString = new ArrayList<>();
        alcohol = new ArrayList<>();
        mixers = new ArrayList<>();

        dispenser = new Properties();
        disp = new HashMap<>();

        spinnerAlcohol1 = findViewById(R.id.spinnerAlcohol1);
        spinnerAlcohol2 = findViewById(R.id.spinnerAlcohol2);
        spinnerAlcohol3 = findViewById(R.id.spinnerAlcohol3);

        spinnerMixer1 = findViewById(R.id.spinnerMixer1);
        spinnerMixer2 = findViewById(R.id.spinnerMixer2);
        spinnerMixer3 = findViewById(R.id.spinnerMixer3);
        spinnerMixer4 = findViewById(R.id.spinnerMixer4);
        spinnerMixer5 = findViewById(R.id.spinnerMixer5);


        buildSpinners();
        loadIngredients();
        spinnerListeners();


    }

    public void savePreferences(View v){
        System.out.println("PASSAAAAA ENTRADA" + dispenser);
        System.out.println("PASSAAAAA ENTRADA" + disp);
        for(int i = 1; i <= 3; i++){
            if(disp.get("d"+i) == 0){
                Toast.makeText(this, "Please, select any alcohol in the Dispenser #"+i, Toast.LENGTH_LONG).show();
                return;
            }
        }
        for(int i=1; i <= 5; i++){
            if(disp.get("p"+i) == 0){
                Toast.makeText(this, "Please, select any alcohol in the Pump #"+i, Toast.LENGTH_LONG).show();
                return;
            }
        }
        for(int i=1; i <= 3; i++)
            dispenser.setProperty("d"+i,""+disp.get("d"+i));
        for(int i=1; i <= 5; i++)
            dispenser.setProperty("p"+i,""+disp.get("p"+i));

        try {
            FileOutputStream fos = openFileOutput(DISPENSER_FILENAME, Context.MODE_PRIVATE);
            dispenser.storeToXML(fos, null);
            fos.close();

        } catch (IOException ioe) {
            Toast.makeText(this, "No se puede guardar los ingredientes.", Toast.LENGTH_LONG).show();
        }
        Toast.makeText(this, "Saved Changes", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, homeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void spinnerListeners(){
        spinnerAlcohol1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                disp.put("d1",alcohol.get(i).getId());
                System.out.println("Dispenser 1 --> " + alcohol.get(i).getId() + ": " + alcohol.get(i).getName());
                System.out.println("HASHMAP -->" + disp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerAlcohol2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                disp.put("d2",alcohol.get(i).getId());
                System.out.println("Dispenser 2 --> " + alcohol.get(i).getId() + ": " + alcohol.get(i).getName());
                System.out.println("HASHMAP -->" + disp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerAlcohol3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                disp.put("d3",alcohol.get(i).getId());
                System.out.println("Dispenser 3 --> " + alcohol.get(i).getId() + ": " + alcohol.get(i).getName());
                System.out.println("HASHMAP -->" + disp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerMixer1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                disp.put("p1",mixers.get(i).getId());
                System.out.println("Pump 1 --> " + mixers.get(i).getId() + ": " + mixers.get(i).getName());
                System.out.println("HASHMAP -->" + disp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerMixer2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                disp.put("p2",mixers.get(i).getId());
                System.out.println("Pump 2 --> " + mixers.get(i).getId() + ": " + mixers.get(i).getName());
                System.out.println("HASHMAP -->" + disp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerMixer3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                disp.put("p3",mixers.get(i).getId());
                System.out.println("Pump 3 --> " + mixers.get(i).getId() + ": " + mixers.get(i).getName());
                System.out.println("HASHMAP -->" + disp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerMixer4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                disp.put("p4",mixers.get(i).getId());
                System.out.println("Pump 4 --> " + mixers.get(i).getId() + ": " + mixers.get(i).getName());
                System.out.println("HASHMAP -->" + disp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerMixer5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                disp.put("p5",mixers.get(i).getId());
                System.out.println("Pump 5 --> " + mixers.get(i).getId() + ": " + mixers.get(i).getName());
                System.out.println("HASHMAP -->" + disp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void getAlcohol(){
        alcohol.clear();
        alcoholString.clear();
        for(int i = 0; i < MainActivity.ingredients.size(); i++)
            if(MainActivity.ingredients.get(i).getType() == 0){
                alcoholString.add(MainActivity.ingredients.get(i).getName());
                alcohol.add(MainActivity.ingredients.get(i));
            }
        if(alcoholString.size() == 0){
            alcohol.add(new Ingredients(0,null,0));
            alcoholString.add("No alcohol registered");
        } else {
            alcohol.add(0,new Ingredients(0,"NULL",0));
            alcoholString.add(0,"Select Alcohol");
        }
        System.out.println("ALCOHOL: " + alcohol.size() + " : " + alcoholString.size());
        for(int i = 0; i < alcohol.size(); i++)
            System.out.println(i + ": " + alcohol.get(i).getName());
    }

    public void getMixers(){
        mixers.clear();
        mixersString.clear();
        for(int i=0; i < MainActivity.ingredients.size(); i++) {
            if (MainActivity.ingredients.get(i).getType() == 1) {
                mixers.add(MainActivity.ingredients.get(i));
                mixersString.add(MainActivity.ingredients.get(i).getName());
            }
        }
        if(mixersString.size() == 0){
            mixers.add(new Ingredients(0,null,1));
            mixersString.add("No mixer registered");
        } else {
            mixers.add(0,new Ingredients(0,"NULL",1));
            mixersString.add(0,"Select Mixer");
        }
        System.out.println("MIXER: " + mixers.size() + " : " + mixersString.size());
        for(int i = 0; i < mixers.size(); i++)
            System.out.println(i + ": " + mixers.get(i).getName());
    }

    public void addNewDrink(View v){
        NewIngredientDialog newIngredientDialog = new NewIngredientDialog();
        newIngredientDialog.show(getSupportFragmentManager(),"New Drink");
        loadIngredients();
    }

    public void buildSpinners(){

        MainActivity.loadData();

        //Separate ingredients from alcohol and mixers
        getAlcohol();
        getMixers();

        ArrayAdapter<String> adapterAlcohol = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_item, alcoholString);
        spinnerAlcohol1.setAdapter(adapterAlcohol);
        spinnerAlcohol2.setAdapter(adapterAlcohol);
        spinnerAlcohol3.setAdapter(adapterAlcohol);

        ArrayAdapter<String> adapterMixer = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_item, mixersString);
        spinnerMixer1.setAdapter(adapterMixer);
        spinnerMixer2.setAdapter(adapterMixer);
        spinnerMixer3.setAdapter(adapterMixer);
        spinnerMixer4.setAdapter(adapterMixer);
        spinnerMixer5.setAdapter(adapterMixer);

    }

    private void loadIngredients() {
        try {
            FileInputStream fis = openFileInput(DISPENSER_FILENAME);
            dispenser.loadFromXML(fis);
            System.out.println("ASASs: " + dispenser);
            spinnerAlcohol1.setSelection(findPositionAlcohol(Integer.parseInt(dispenser.getProperty("d1"))));
            spinnerAlcohol2.setSelection(findPositionAlcohol(Integer.parseInt(dispenser.getProperty("d2"))));
            spinnerAlcohol3.setSelection(findPositionAlcohol(Integer.parseInt(dispenser.getProperty("d3"))));
            spinnerMixer1.setSelection(findPositionMixer(Integer.parseInt(dispenser.getProperty("p1"))));
            spinnerMixer2.setSelection(findPositionMixer(Integer.parseInt(dispenser.getProperty("p2"))));
            spinnerMixer3.setSelection(findPositionMixer(Integer.parseInt(dispenser.getProperty("p3"))));
            spinnerMixer4.setSelection(findPositionMixer(Integer.parseInt(dispenser.getProperty("p4"))));
            spinnerMixer5.setSelection(findPositionMixer(Integer.parseInt(dispenser.getProperty("p5"))));
            fis.close();
            System.out.println("BUGGUEO");
        } catch (FileNotFoundException fnfe) {
            dispenser.setProperty("p1","0");
            dispenser.setProperty("p2","0");
            dispenser.setProperty("p3","0");
            dispenser.setProperty("p4","0");
            dispenser.setProperty("p5","0");
            dispenser.setProperty("d1","0");
            dispenser.setProperty("d2","0");
            dispenser.setProperty("d3","0");


            try {
                FileOutputStream fos = openFileOutput(DISPENSER_FILENAME, Context.MODE_PRIVATE);
                dispenser.storeToXML(fos, null);
                fos.close();
            } catch (IOException ioe) {
                Toast.makeText(this, "No se puede guardar los ingredientes.", Toast.LENGTH_LONG).show();
            }
        } catch (IOException ioe) {
            Toast.makeText(this, "No se puede leer los ingredientes.", Toast.LENGTH_LONG).show();
        }
        System.out.println("PROPERTIES LOADED: " + dispenser);
    }

    public int findPositionAlcohol(int id){
        for(int i = 0; i < alcohol.size(); i++){
            if(id == alcohol.get(i).getId()){
                return i;
            }
        }
        return 0;
    }

    public int findPositionMixer(int id){
        for(int i = 0; i < mixers.size(); i++){
            if(id == mixers.get(i).getId()){
                return i;
            }
        }
        return 0;
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
            mixersString.clear();
            alcoholString.clear();

            buildSpinners();

        }

    }


}
