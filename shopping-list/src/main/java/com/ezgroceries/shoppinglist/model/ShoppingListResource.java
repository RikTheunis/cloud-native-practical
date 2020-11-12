package com.ezgroceries.shoppinglist.model;

import java.util.List;
import java.util.UUID;

public class ShoppingListResource {

    private UUID shoppingListId;
    private String name;
    private List<CocktailResource> cocktails;

    public ShoppingListResource(UUID shoppingListId, String name, List<CocktailResource> cocktails) {
        this.shoppingListId = shoppingListId;
        this.name = name;
        this.cocktails = cocktails;
    }

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

    public List<CocktailResource> getCocktails() {
        return cocktails;
    }

    public void setCocktails(List<CocktailResource> cocktails) {
        this.cocktails = cocktails;
    }

    public void addCocktail(CocktailResource cocktail) {
        this.cocktails.add(cocktail);
    }
}
