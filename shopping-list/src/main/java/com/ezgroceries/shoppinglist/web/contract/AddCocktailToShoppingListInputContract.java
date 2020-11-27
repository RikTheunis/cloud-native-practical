package com.ezgroceries.shoppinglist.web.contract;

import java.util.UUID;

public class AddCocktailToShoppingListInputContract {

    private UUID cocktailId;

    public UUID getCocktailId() {
        return cocktailId;
    }

    public void setCocktailId(UUID search) {
        this.cocktailId = search;
    }
}
