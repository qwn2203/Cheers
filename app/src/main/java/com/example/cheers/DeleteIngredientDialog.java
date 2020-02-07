package com.example.cheers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

public class DeleteIngredientDialog extends AppCompatDialogFragment {

    private Spinner typeSpinner;
    private DeleteIngredientDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.deletedrink_dialog, null);
        DBHandler handler = new DBHandler(getContext(),null,null,0);
        List<String> spinnerArray =  handler.getIngredientsName();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = view.findViewById(R.id.deleteSpinner);
        sItems.setAdapter(adapter);

        builder.setView(view).setTitle("Delete Ingredient")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        typeSpinner = view.findViewById(R.id.spinnerNewDrink);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (DeleteIngredientDialog.DeleteIngredientDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ " must implement ExampleDialog Listener") ;
        }
    }

    public interface DeleteIngredientDialogListener{
        void applyTexts(String name, String type);
    }
}
