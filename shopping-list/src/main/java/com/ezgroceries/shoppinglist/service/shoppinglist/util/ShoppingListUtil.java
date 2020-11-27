package com.ezgroceries.shoppinglist.service.shoppinglist.util;

import com.ezgroceries.shoppinglist.service.shoppinglist.model.ShoppingListResource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShoppingListUtil {

    public static List<String> getAllIngredients(ShoppingListResource shoppingList) {
        Set<String> ingredients = new HashSet<>();

        shoppingList.getCocktails().forEach(cocktail -> ingredients.addAll(cocktail.getIngredients()));

        return new ArrayList<>(ingredients);
    }
}
