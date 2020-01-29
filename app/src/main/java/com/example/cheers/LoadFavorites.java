package com.example.cheers;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class LoadFavorites {
    private static final String FAVORITES_FILENAME = "favorites.xml";
    public static Properties favorites;
    Context context;
    List<String> favorite;

    public LoadFavorites(Context context){
        this.context = context;
        favorites = new Properties();
        favorite = new ArrayList<>();
    }

    private void loadFavorites(){
        try{
            FileInputStream fis = context.openFileInput(FAVORITES_FILENAME);
            favorites.loadFromXML(fis);
            favorite.addAll((Collection<String>)(Collection<?>)favorites.values());
            fis.close();

        } catch (FileNotFoundException fnfe){
            try{
                favorites.setProperty("1","Bebida 1");
                favorites.setProperty("2","Bebida 2");

                FileOutputStream fos = context.openFileOutput(FAVORITES_FILENAME, Context.MODE_PRIVATE);
                favorites.storeToXML(fos,null);
                fos.close();
            } catch(IOException ioe){
                Toast.makeText(context, "No se puede guardar las bebidas favoritas", Toast.LENGTH_LONG).show();
            }

        } catch (IOException ioe){
            Toast.makeText(context, "No se puede leer las bebidas favoritas", Toast.LENGTH_LONG).show();
        }
    }

    private void addFavorite(){
        //favorites.setProperty();
    }
}
