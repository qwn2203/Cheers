package com.example.cheers;


import android.content.Context;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class LoadFavorites {
    private static final String FAVORITES_FILENAME = "favorites.xml";
    public static Properties favorites;
    static Context context;
    static ArrayList<String> favorite;

    public LoadFavorites(Context context){
        this.context = context;
        favorites = new Properties();
        favorite = new ArrayList<>();
    }

    public static void loadFavorites(){
        try{
            FileInputStream fis = context.openFileInput(FAVORITES_FILENAME);
            favorites.loadFromXML(fis);
            favorite.addAll((Collection<String>)(Collection<?>)favorites.values());
            fis.close();

        } catch (FileNotFoundException fnfe){
            saveFavorites();
        } catch (IOException ioe){
            Toast.makeText(context, "No se puede leer las bebidas favoritas", Toast.LENGTH_LONG).show();
        }
    }

    public static void saveFavorites(){
        try{
            FileOutputStream fos = context.openFileOutput(FAVORITES_FILENAME, Context.MODE_PRIVATE);
            favorites.storeToXML(fos,null);
            fos.close();
        } catch(IOException ioe){
            Toast.makeText(context, "No se puede guardar las bebidas favoritas", Toast.LENGTH_LONG).show();
        }
    }
}
