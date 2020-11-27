package com.ezgroceries.shoppinglist.web.contract;

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
