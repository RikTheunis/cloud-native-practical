package com.ezgroceries.shoppinglist.contract;

import java.util.UUID;

public class CreateNewShoppingListOutputContract {

    private UUID shoppingListId;
    private String name;

    public UUID getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(UUID shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
