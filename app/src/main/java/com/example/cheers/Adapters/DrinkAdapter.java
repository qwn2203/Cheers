package com.example.cheers.Adapters;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheers.Activity.DrinkInformationActivity;
import com.example.cheers.Activity.InventarioActivity;
import com.example.cheers.BluetoothConnectionManager;
import com.example.cheers.LoadFavorites;
import com.example.cheers.Objetos.Drink;
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


public class DrinkAdapter extends  RecyclerView.Adapter {
    ArrayList<DrinkIngredient> list = new ArrayList<>();
    static Context context;
    static int pos;
    Drink drink;

    static Properties dispenser = new Properties();
    private static final String DISPENSER_FILENAME = "dispenser.xml";

    public DrinkAdapter(ArrayList<DrinkIngredient> list, Context context) {
        this.list.addAll(list);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.drink_card,parent,false);
        DrinkViewHolder holder = new DrinkViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        pos = list.get(position).getId();
        final DrinkViewHolder alcoholHolder = (DrinkViewHolder)holder;
        alcoholHolder.name.setText(list.get(position).getNameDrink());
        if(LoadFavorites.favorites.getProperty("D"+pos) != null){
            alcoholHolder.heart.setImageResource(R.drawable.favorite);
        } else {
            alcoholHolder.heart.setImageResource(R.drawable.favorite_empty);
        }
        alcoholHolder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = LoadFavorites.favorites.getProperty("D"+pos);
                if(tmp == null){
                    LoadFavorites.favorites.setProperty("D"+pos,Integer.toString(pos));
                    alcoholHolder.heart.setImageResource(R.drawable.favorite);

                } else if(tmp != null){
                    LoadFavorites.favorites.remove("D"+pos);
                    alcoholHolder.heart.setImageResource(R.drawable.favorite_empty);
                }
                LoadFavorites.saveFavorites();
            }
        });
        alcoholHolder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Prueba", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, DrinkInformationActivity.class);
                i.putExtra("drink", list.get(position));
                context.startActivity(i);
            }
        });
        alcoholHolder.prepare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mssg = "Are you sure that you want to prepare the drink: " + list.get(position).getNameDrink() + " ?";
                SpannableString ss = new SpannableString(mssg);
                StyleSpan italic = new StyleSpan(Typeface.BOLD);
                ss.setSpan(italic,49,mssg.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setTitle("Prepare your drink: ")
                        .setMessage(ss)
                        .setPositiveButton("Preprare Drink", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //boolean tmp = DrinkAdapter.checkStock(list.get(position));
                                    HashMap<String, Integer> amount = DrinkAdapter.assignBumpers(list.get(position));
                                    new Drink(0,null,null,null).parseIngredients(amount);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private static HashMap<String, Integer> assignBumpers(DrinkIngredient d){
        /*ArrayList<Integer> tmp = new ArrayList<>();
        ArrayList<String> bumpers = new ArrayList<>();
        HashMap<String, Integer> ing = new HashMap<>();
        ArrayList<Ingredients> ingredients = new ArrayList<>(d.getIngredient());
        ArrayList<Integer> amount = new ArrayList<>(d.getPercentage());

        System.out.println("PROPERTIES: " + dispenser);

        Enumeration<String> enums = (Enumeration<String>) dispenser.propertyNames();
        while (enums.hasMoreElements()) {
            String key = enums.nextElement();
            String value = dispenser.getProperty(key);
            System.out.println(key + " --> " + value);
            bumpers.add(key);
            tmp.add(Integer.parseInt(value));
        }
        System.out.println("EMPIEZA CICLO:" + ingredients.size() + ", " + tmp.size());
        for(int i = 0; i < bumpers.size(); i++){
            System.out.println("B: " + bumpers.get(i) + " --> " + tmp.get(i));
        }
        //Hace falta que se diferencie de los pump y los dispenser al evaluar el id
        System.out.println("HASHMAP" + ing);

        return false;*/
        HashMap<String, Integer> tmp = new HashMap<>();
        tmp.put("d1", 23);
        tmp.put("d2", 30);
        tmp.put("d3", 25);
        tmp.put("p1",11);
        return tmp;
    }

    private static boolean checkStock(DrinkIngredient d){
        loadIngredients();
        ArrayList<Ingredients> ingredients = new ArrayList<>(d.getIngredient());
        ArrayList<Integer> tmp = new ArrayList<>();
        Enumeration<String> enums = (Enumeration<String>) dispenser.propertyNames();
        while (enums.hasMoreElements()) {
            String key = enums.nextElement();
            String value = dispenser.getProperty(key);
            tmp.add(Integer.parseInt(value));
        }

        for(int i = 0; i < tmp.size(); i++){
            for(int j = 0; j < ingredients.size(); j++){
                if(tmp.get(i) == ingredients.get(j).getId()){
                    System.out.println(i + " Disp: " + tmp.get(i) + " --> (j = " + j + ")" + ingredients.get(j).getId());
                    tmp.remove(i);
                    ingredients.remove(j);
                    i = j = 0;
                    continue;
                }
            }
        }
        if(ingredients.isEmpty()){
            System.out.println("Se eliminaron todos los elementos");
            return true;
        }

        return false;
    }

    private static void loadIngredients(){
        try {
            FileInputStream fis = context.openFileInput(DISPENSER_FILENAME);
            dispenser.loadFromXML(fis);
            fis.close();

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            Toast.makeText(context, "No se puede leer los ingredientes.", Toast.LENGTH_LONG).show();
        }
    }

}
