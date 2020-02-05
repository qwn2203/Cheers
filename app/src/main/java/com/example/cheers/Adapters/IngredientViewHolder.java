package com.example.cheers.Adapters;


import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheers.R;

public class IngredientViewHolder extends RecyclerView.ViewHolder {
    ImageView image;
    TextView name, percentage;
    SeekBar seekBar;

    public IngredientViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.nameInformation);
        image = itemView.findViewById(R.id.typeInformation);
        percentage = itemView.findViewById(R.id.percentageInformation);
        seekBar = itemView.findViewById(R.id.progressBarInformation);


    }


}
