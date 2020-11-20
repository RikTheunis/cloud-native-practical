package com.ezgroceries.shoppinglist.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ShoppingListResource {

    private UUID shoppingListId;
    private String name;
    private Set<CocktailResource> cocktails;

    public ShoppingListResource(UUID shoppingListId, String name) {
        this.shoppingListId = shoppingListId;
        this.name = name;
        this.cocktails = new HashSet<>();
    }

    public ShoppingListResource(UUID shoppingListId, String name, Set<CocktailResource> cocktails) {
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

    public Set<CocktailResource> getCocktails() {
        return cocktails;
    }

    public void setCocktails(Set<CocktailResource> cocktails) {
        this.cocktails = cocktails;
    }

    public void addCocktail(CocktailResource cocktail) {
        this.cocktails.add(cocktail);
    }
}
