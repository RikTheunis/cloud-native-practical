package com.ezgroceries.shoppinglist.service.cocktail.model;

import java.util.Set;
import java.util.UUID;

public class CocktailResource {

    private UUID cocktailId;
    private String name;
    private String glass;
    private String instructions;
    private String imageUrl;
    private Set<String> ingredients;

    public CocktailResource(UUID id, String name, String glass, String instructions, String imageUrl, Set<String> ingredients) {
        this.cocktailId = id;
        this.name = name;
        this.glass = glass;
        this.instructions = instructions;
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
    }

    public UUID getCocktailId() {
        return cocktailId;
    }

    public String getName() {
        return name;
    }

    public String getGlass() {
        return glass;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }
}
