package com.example.cheers.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheers.Activity.CreateDrinkActivity;
import com.example.cheers.Objetos.Ingredients;
import com.example.cheers.R;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter {
    ArrayList<Ingredients> ingredients = new ArrayList<>();
    Context context;

    public IngredientAdapter(ArrayList<Ingredients> ingredients, Context context){
        this.ingredients.addAll(ingredients);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bebida_porcentaje_card,parent,false);
        IngredientViewHolder holder = new IngredientViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final IngredientViewHolder ingredientViewHolder = (IngredientViewHolder)holder;
        ingredientViewHolder.image.setImageResource((ingredients.get(position).getType() == 0)?R.drawable.champagne:R.drawable.soda);
        ingredientViewHolder.name.setText(ingredients.get(position).getName());
        ingredientViewHolder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Integer id = ingredients.get(position).getId();
                ingredientViewHolder.percentage.setText(progress + "%");
                if(progress == 0){
                    CreateDrinkActivity.percentageIng.remove(id);
                } else {
                    CreateDrinkActivity.percentageIng.put(id,progress);
                    CreateDrinkActivity.updatePercentage();
                }
                if(CreateDrinkActivity.getSum() <= 100){
                    CreateDrinkActivity.updatePercentage();
                } else if(CreateDrinkActivity.getSum() > 100) {
                    Toast.makeText(context, "You've exceeded the 100% percent of your cup.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
}
