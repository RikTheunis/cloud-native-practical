package com.ezgroceries.shoppinglist.service.shoppinglist.model;

import com.ezgroceries.shoppinglist.service.cocktail.model.Cocktail;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ShoppingList {

    private UUID shoppingListId;
    private String name;
    private String ownerName;
    private Set<Cocktail> cocktails;

    public ShoppingList(UUID shoppingListId, String name, String ownerName) {
        this.shoppingListId = shoppingListId;
        this.name = name;
        this.ownerName = ownerName;
        this.cocktails = new HashSet<>();
    }

    public ShoppingList(UUID shoppingListId, String name, String ownerName, Set<Cocktail> cocktails) {
        this.shoppingListId = shoppingListId;
        this.name = name;
        this.ownerName = ownerName;
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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Set<Cocktail> getCocktails() {
        return cocktails;
    }

    public void setCocktails(Set<Cocktail> cocktails) {
        this.cocktails = cocktails;
    }

    public void addCocktail(Cocktail cocktail) {
        this.cocktails.add(cocktail);
    }
}
