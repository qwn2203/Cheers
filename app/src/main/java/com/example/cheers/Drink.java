package com.example.cheers;

import java.util.HashMap;

public class Drink {
    private String name;
    private String instructions;
    private HashMap<String, Integer> ingredients;

    public Drink() {
        setName("");
        this.setInstructions("");
        this.ingredients = new HashMap<>();
    }

    Drink(String name, String instructions) {
        this.setName(name);
        this.instructions = instructions;
    }

    public Drink(String name, String instructions, HashMap<String, Integer> ingredients){
        this.setName(name);
        this.setInstructions(instructions);
        this.ingredients = new HashMap<>();
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

    public HashMap<String, Integer> getIngredients() {
        return ingredients;
    }

    public void setIngredients(HashMap<String, Integer> ingredients) {
        this.ingredients = ingredients;
    }
}
