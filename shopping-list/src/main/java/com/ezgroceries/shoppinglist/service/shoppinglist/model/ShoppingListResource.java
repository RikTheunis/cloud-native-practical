package com.ezgroceries.shoppinglist.service.shoppinglist.model;

import com.ezgroceries.shoppinglist.service.cocktail.model.CocktailResource;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ShoppingListResource {

    private UUID shoppingListId;
    private String name;
    private String ownerName;
    private Set<CocktailResource> cocktails;

    public ShoppingListResource(UUID shoppingListId, String name, String ownerName) {
        this.shoppingListId = shoppingListId;
        this.name = name;
        this.ownerName = ownerName;
        this.cocktails = new HashSet<>();
    }

    public ShoppingListResource(UUID shoppingListId, String name, String ownerName, Set<CocktailResource> cocktails) {
        this.shoppingListId = shoppingListId;
        this.name = name;
        this.ownerName = name;
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
