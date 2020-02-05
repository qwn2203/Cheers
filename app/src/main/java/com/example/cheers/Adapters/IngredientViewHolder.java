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
        name = itemView.findViewById(R.id.nameTextView);
        image = itemView.findViewById(R.id.typeImageView);
        percentage = itemView.findViewById(R.id.percentageInfo);
        seekBar = itemView.findViewById(R.id.progressBar);


    }


}
