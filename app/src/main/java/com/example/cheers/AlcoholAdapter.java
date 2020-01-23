package com.example.cheers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class AlcoholAdapter extends  RecyclerView.Adapter {
    ArrayList<Drink> list;
    Context context;
    int imageSrc;

    public AlcoholAdapter(ArrayList<Drink> list, Context context, int imageSrc) {
        this.list = list;
        this.context = context;
        this.imageSrc = imageSrc;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.alcohol_card,parent,false);
        CreateDrinkActivity.AlcoholViewHolder holder = new CreateDrinkActivity.AlcoholViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CreateDrinkActivity.AlcoholViewHolder alcoholHolder = (CreateDrinkActivity.AlcoholViewHolder)holder;
        alcoholHolder.name.setText(list.get(position).getName());
        alcoholHolder.imageView.setImageResource(imageSrc);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
