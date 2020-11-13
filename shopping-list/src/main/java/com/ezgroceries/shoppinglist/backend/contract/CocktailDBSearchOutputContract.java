package com.ezgroceries.shoppinglist.backend.contract;

import java.util.List;

public class CocktailDBSearchOutputContract {
    private List<DrinkResource> drinks;

    public List<DrinkResource> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<DrinkResource> drinks) {
        this.drinks = drinks;
    }


}
