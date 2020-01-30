package com.example.cheers.Objetos;

import com.example.cheers.BluetoothConnectionManager;
import com.example.cheers.ConnectionThread;

import java.util.HashMap;

public class Drink {
    private int id;
    private String name;
    private String instructions;
    private HashMap<String, Integer> ingredients;
    ConnectionThread thread;
    BluetoothConnectionManager manager;


    public Drink() {
        this.id = 0;
        this.name = "";
        this.instructions = "";
        this.ingredients = new HashMap<String, Integer>();
    }

    public Drink(int id, String name, String instructions, HashMap<String, Integer> ingredients) {
        this.id = id;
        this.name = name;
        this.instructions = instructions;
        this.ingredients = ingredients;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void parseIngredients(HashMap ingredients){
        this.ingredients = ingredients;
        String string = ingredients.toString();
        String s = string.replaceAll("[^a-zA-Z0-9=,]", "");
        byte[] b = s.getBytes();
        manager.send(b);
    }
}