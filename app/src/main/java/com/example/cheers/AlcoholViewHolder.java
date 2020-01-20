package com.example.cheers;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlcoholViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    public AlcoholViewHolder(@NonNull View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.name);
    }
}

