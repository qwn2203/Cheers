package com.example.cheers.Objetos;

import java.util.HashMap;

public class Drink {
    private int id;
    private String name;
    private String instructions;


    public Drink() {
        this.id = 0;
        this.name = "";
        this.instructions = "";

    }

    public Drink(int id, String name, String instructions, HashMap<String, Integer> ingredients) {
        this.id = id;
        this.name = name;
        this.instructions = instructions;

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

}