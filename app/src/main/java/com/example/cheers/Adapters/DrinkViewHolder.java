package com.example.cheers.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheers.R;

public class DrinkViewHolder extends RecyclerView.ViewHolder {
    public ImageView image, heart, prepare, info;
    public TextView name;


    public DrinkViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.bebida);
        name = itemView.findViewById(R.id.nameInformation);
        heart = itemView.findViewById(R.id.heart);
        prepare = itemView.findViewById(R.id.prepareImage);
        info = itemView.findViewById(R.id.infoImage);
    }
}
