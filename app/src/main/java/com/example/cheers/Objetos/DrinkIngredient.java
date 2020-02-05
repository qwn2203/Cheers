package com.example.cheers.Objetos;


import java.io.Serializable;
import java.util.ArrayList;

public class DrinkIngredient implements Serializable {
    private Drink drink;
    private ArrayList<Ingredients> ingredient;
    private ArrayList<Integer> percentage;

    public DrinkIngredient(Drink d) {
        this.drink = d;
        this.ingredient = new ArrayList<>();
        this.percentage = new ArrayList<>();
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public ArrayList<Ingredients> getIngredient() {
        return ingredient;
    }

    public void setIngredient(ArrayList<Ingredients> ingredient) {
        this.ingredient = ingredient;
    }

    public ArrayList<Integer> getPercentage() {
        return percentage;
    }

    public void setPercentage(ArrayList<Integer> percentage) {
        this.percentage = percentage;
    }

    public int getId(){
        return this.drink.getId();
    }

    public String getNameDrink(){
        return this.drink.getName();
    }

    public String getDescriptionDrink(){
        return this.drink.getInstructions();
    }

}
