package com.example.cheers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class NewIngredientDialog extends AppCompatDialogFragment {
    private EditText nameEditText;
    private Spinner typeSpinner;
    private NewIngredientDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.newdrink_dialog,null);

        builder.setView(view).setTitle("Add new Drink")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String username = nameEditText.getText().toString().trim();
                String type = typeSpinner.getSelectedItem().toString();
                listener.applyTexts(username,type);
            }
        });

        nameEditText = view.findViewById(R.id.editTextDrink);
        typeSpinner = view.findViewById(R.id.spinnerNewDrink);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (NewIngredientDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ " must implement ExampleDialog Listener") ;
        }
    }

    public interface NewIngredientDialogListener{
        void applyTexts(String name, String type);
    }
}
