package com.example.cheers.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheers.Objetos.Ingredients;
import com.example.cheers.R;

import java.util.ArrayList;

public class DrinkInformationAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<Ingredients> ingredients = new ArrayList<>();
    ArrayList<Integer> amountIngredients = new ArrayList<>();

    public DrinkInformationAdapter(ArrayList<Ingredients> ingredients, ArrayList<Integer> amount, Context context){
        this.ingredients.addAll(ingredients);
        this.amountIngredients.addAll(amount);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredient_information_card,parent,false);
        DrinkInformationViewHolder holder = new DrinkInformationViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DrinkInformationViewHolder drinkViewHolder = (DrinkInformationViewHolder) holder;
        drinkViewHolder.name.setText(ingredients.get(position).getName());
        drinkViewHolder.imageView.setImageResource((ingredients.get(position).getType() == 0) ? R.drawable.champagne : R.drawable.soda);
        drinkViewHolder.percentage.setText(amountIngredients.get(position) + "%");
        drinkViewHolder.progressBar.setProgress(amountIngredients.get(position));

        System.out.println(ingredients.get(position).getName() + ": " + ingredients.get(position).getType() + " --> " + amountIngredients.get(position));


    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }


}
