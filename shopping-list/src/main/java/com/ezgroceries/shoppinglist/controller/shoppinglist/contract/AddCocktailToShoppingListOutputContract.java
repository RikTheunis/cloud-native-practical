package com.ezgroceries.shoppinglist.controller.shoppinglist.contract;

import java.util.UUID;

public class AddCocktailToShoppingListOutputContract {

    private UUID cocktailId;

    public UUID getCocktailId() {
        return cocktailId;
    }

    public void setCocktailId(UUID cocktailId) {
        this.cocktailId = cocktailId;
    }
}
