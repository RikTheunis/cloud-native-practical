package com.ezgroceries.shoppinglist.external.contract;

import java.util.List;

public class CocktailDBSearchResponse {
    private List<DrinkResource> drinks;

    public List<DrinkResource> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<DrinkResource> drinks) {
        this.drinks = drinks;
    }


}
