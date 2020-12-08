package com.ezgroceries.shoppinglist.controller.shoppinglist.contract;

import java.util.UUID;

public class AddCocktailToShoppingListRequest {

    private UUID cocktailId;

    public UUID getCocktailId() {
        return cocktailId;
    }

    public void setCocktailId(UUID search) {
        this.cocktailId = search;
    }
}
