package com.ezgroceries.shoppinglist.model;

import java.util.List;
import java.util.UUID;

public class CocktailResource {

    private UUID cocktailId;
    private String name;
    private String glass;
    private String instructions;
    private String imageUrl;
    private List<String> ingredients;

    public CocktailResource(UUID id, String name, String glass, String instructions, String imageUrl, List<String> ingredients) {
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

    public List<String> getIngredients() {
        return ingredients;
    }
}
