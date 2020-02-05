package com.example.cheers.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheers.R;

public class DrinkInformationViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView name, percentage;
    public ProgressBar progressBar;


    public DrinkInformationViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.typeInformation);
        name = itemView.findViewById(R.id.nameInformation);
        progressBar = itemView.findViewById(R.id.progressBarInformation);
        percentage = itemView.findViewById(R.id.percentageInformation);
    }
}
