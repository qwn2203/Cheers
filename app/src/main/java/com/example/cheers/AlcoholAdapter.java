package com.example.cheers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlcoholAdapter extends  RecyclerView.Adapter {
    ArrayList<AlcoholData> list;
    Context context;

    public AlcoholAdapter(ArrayList<AlcoholData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.alcohol_card,parent,false);
        AlcoholViewHolder  holder = new AlcoholViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AlcoholViewHolder alcoholHolder = (AlcoholViewHolder)holder;
        alcoholHolder.name.setText(list.get(position).name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
