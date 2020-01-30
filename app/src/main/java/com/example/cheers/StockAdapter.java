package com.example.cheers;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheers.Activity.CreateDrinkActivity;
import com.example.cheers.Objetos.Ingredients;

import java.util.ArrayList;
import java.util.List;

public class StockAdapter extends RecyclerView.Adapter {
    List<Ingredients> ingredients = new ArrayList<>();
    Context context;

    public StockAdapter(List<Ingredients> ingredients, Context context){
        this.ingredients.addAll(ingredients);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.stock_card,parent,false);
        StockViewHolder holder = new StockViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StockViewHolder stockViewHolder = (StockViewHolder)holder;
        stockViewHolder.image.setImageResource((ingredients.get(position).getType() == 0)?R.drawable.champagne:R.drawable.soda);
        stockViewHolder.name.setText(ingredients.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
}
