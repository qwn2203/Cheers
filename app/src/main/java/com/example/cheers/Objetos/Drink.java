package com.example.cheers.Objetos;

import com.example.cheers.BluetoothConnectionManager;

import java.io.Serializable;
import java.util.HashMap;

public class Drink implements Serializable {
    private int id;
    private String name;
    private String instructions;
    private HashMap<String, Integer> ingredients;
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
        String br = "\n";
        String f = s.concat(br);
        byte[] b = f.getBytes();
        manager = BluetoothConnectionManager.getInstance("98:D3:31:20:4D:8E");
        manager.send(b);
        System.out.println(b);
    }
}