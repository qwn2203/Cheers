package com.example.cheers.Objetos;

import java.util.ArrayList;
import java.util.List;

public class DrinkIngredient {
    private Drink drink;
    private List<Ingredients> ingredient;
    private List<Integer> percentage;

    public DrinkIngredient(Drink drink) {
        this.drink = drink;
        this.ingredient = new ArrayList<>();
        this.percentage = new ArrayList<>();
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public List<Ingredients> getIngredient() {
        return ingredient;
    }

    public void setIngredient(List<Ingredients> ingredient) {
        this.ingredient = ingredient;
    }

    public List<Integer> getPercentage() {
        return percentage;
    }

    public void setPercentage(List<Integer> percentage) {
        this.percentage = percentage;
    }
}
