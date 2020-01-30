package com.example.cheers.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cheers.NewIngredientDialog;
import com.example.cheers.Objetos.Ingredients;
import com.example.cheers.R;
import com.example.cheers.StockAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
        System.out.println("PASSAAAAA ENTRADA");
        for(int i = 1; i <= 3; i++){
            if(disp.get("dispenser"+i) == 0){
                Toast.makeText(this, "Please, select any alcohol in the Dispenser #"+i, Toast.LENGTH_LONG).show();
                return;
            }
        }
        for(int i=1; i <= 5; i++){
            if(disp.get("pump"+i) == 0){
                Toast.makeText(this, "Please, select any alcohol in the Pump #"+i, Toast.LENGTH_LONG).show();
                return;
            }
        }
        for(int i=1; i <= 3; i++)
            dispenser.setProperty("dispenser"+i,""+disp.get("dispenser"+i));
        for(int i=1; i <= 5; i++)
            dispenser.setProperty("pump"+i,""+disp.get("pump"+i));

        try {
            FileOutputStream fos = openFileOutput(DISPENSER_FILENAME, Context.MODE_PRIVATE);
            dispenser.storeToXML(fos, null);
            fos.close();
        } catch (IOException ioe) {
            Toast.makeText(this, "No se puede guardar los ingredientes.", Toast.LENGTH_LONG).show();
        }
        System.out.println("PASSAAAAA SALIDA");
    }

    public void spinnerListeners(){
        spinnerAlcohol1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                disp.put("dispenser1",i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerAlcohol2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                disp.put("dispenser2",i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerAlcohol3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                disp.put("dispenser3",i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerMixer1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                disp.put("pump1",i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerMixer2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                disp.put("pump2",i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerMixer3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                disp.put("pump3",i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerMixer4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                disp.put("pump4",i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerMixer5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                disp.put("pump5",i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getAlcohol(){
        for(int i = 0; i < MainActivity.ingredients.size(); i++)
            if(MainActivity.ingredients.get(i).getType() == 0){
                alcoholString.add(MainActivity.ingredients.get(i).getName());
                alcohol.add(MainActivity.ingredients.get(i));
            }
        if(alcoholString.size() == 0){
            alcohol.add(new Ingredients(0,null,0));
            alcoholString.add("No alcohol registered");
        } else {
            alcohol.add(new Ingredients(0,null,0));
            alcoholString.add(0,"Select Alcohol");
        }
    }

    public void getMixers(){
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
            mixers.add(new Ingredients(0,null,1));
            mixersString.add(0,"Select Mixer");
        }
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
            spinnerAlcohol1.setSelection(Integer.parseInt(dispenser.getProperty("dispenser1")));
            spinnerAlcohol2.setSelection(Integer.parseInt(dispenser.getProperty("dispenser2")));
            spinnerAlcohol3.setSelection(Integer.parseInt(dispenser.getProperty("dispenser3")));
            spinnerMixer1.setSelection(Integer.parseInt(dispenser.getProperty("pump1")));
            spinnerMixer2.setSelection(Integer.parseInt(dispenser.getProperty("pump2")));
            spinnerMixer3.setSelection(Integer.parseInt(dispenser.getProperty("pump3")));
            spinnerMixer4.setSelection(Integer.parseInt(dispenser.getProperty("pump4")));
            spinnerMixer5.setSelection(Integer.parseInt(dispenser.getProperty("pump5")));
            fis.close();

        } catch (FileNotFoundException fnfe) {
            dispenser.setProperty("pump1","0");
            dispenser.setProperty("pump2","0");
            dispenser.setProperty("pump3","0");
            dispenser.setProperty("pump4","0");
            dispenser.setProperty("pump5","0");
            dispenser.setProperty("dispenser1","0");
            dispenser.setProperty("dispenser2","0");
            dispenser.setProperty("dispenser3","0");


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
